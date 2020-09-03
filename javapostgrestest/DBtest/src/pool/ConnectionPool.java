package pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {
	private static final Logger logger = LogManager.getLogger();
	private String jdbcUrl;
	private String jdbcUser;
	private String jdbcPassword;
	private int maxSize;
	private int validationTimeout;

	private Queue<ConnectionWrapper> freeConnections = new ConcurrentLinkedQueue<>();
	private Set<ConnectionWrapper> usedConnections = new ConcurrentSkipListSet<>();

	public Connection getConnection() throws ConnectionPoolException {
		ConnectionWrapper connection = null;
		while(connection == null) {
			connection = freeConnections.poll();
			if(connection != null) {
				boolean isConnectionValid;
				try {
					isConnectionValid = connection.isValid(validationTimeout);
				} catch(SQLException e) {
					isConnectionValid = false;
				}
				if(!isConnectionValid) {
					close(connection);
					connection = null;
				}
			} else if(usedConnections.size() < maxSize) {
				connection = establishConnection();
			//	logger.warn("A new connection was created");
			} else {
			//	logger.error("The Connection pool max size exceeded");
				throw new ConnectionPoolException();
			}
		}
		usedConnections.add(connection);
	//	logger.info(String.format("Take a connection from the pool, used connections - %04d, free connections - %04d", usedConnections.size(), freeConnections.size()));
		return connection;
	}

	public synchronized void init(String jdbcDriver, String jdbcUrl, String jdbcUser, String jdbcPassword, int minSize, int maxSize, int validationTimeout) throws ConnectionPoolException {
		try {
			Class.forName(jdbcDriver);
			this.jdbcUrl = jdbcUrl;
			this.jdbcUser = jdbcUser;
			this.jdbcPassword = jdbcPassword;
			this.maxSize = maxSize;
			this.validationTimeout = validationTimeout;
			for(int i = 0; i < minSize; i++) {
				freeConnections.add(establishConnection());
			}
		//	logger.info(String.format("the Connection pool init is successful, free connections - %04d", freeConnections.size()));
		} catch(ClassNotFoundException e) {
			throw new ConnectionPoolException(e);
		}
	}

	public void destroy() {
		synchronized (usedConnections) {
			synchronized (freeConnections) {
				usedConnections.addAll(freeConnections);
				freeConnections.clear();
				for(ConnectionWrapper connection : usedConnections) {
					close(connection);
				}
				usedConnections.clear();
				closer.shutdown(); // TODO: проверить, отработал ли executor
		//		logger.info("All the connections in the pool have been closed");
			}
		}
	}

	void freeConnection(ConnectionWrapper connection) throws SQLException {
		try {
			usedConnections.remove(connection);
			connection.clearWarnings();
			freeConnections.add(connection);
	//		logger.info(String.format("Return the connection to the pool, used connections - %04d, free connections - %04d", usedConnections.size(), freeConnections.size()));
		} catch(SQLException e) {
			close(connection);
			throw e;
		}
	}

	private ConnectionWrapper establishConnection() throws ConnectionPoolException {
		try {
			return new ConnectionWrapper(DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword));
		} catch(SQLException e) {
			throw new ConnectionPoolException(e);
		}
	}

	private static ExecutorService closer = Executors.newSingleThreadExecutor();

	private void close(ConnectionWrapper connection) {
		closer.execute(() -> {
			synchronized (connection) {
				try { connection.getConnection().close(); } catch(SQLException e) {
					logger.info("the Connection has been closed");
					//System.out.println("FAILED TO CLOSE THE CONNECTION " + connection);
				}
			}
		});
	}

	private ConnectionPool() {}

	private static ConnectionPool instance = new ConnectionPool();

	public static ConnectionPool getInstance() {
		return instance;
	}
}
