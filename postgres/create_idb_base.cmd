@echo off
set Path=%Path%;c:\pgsql\bin\
psql --username=root --password --dbname=postgres --command="\encoding UTF8" --file=%cd%\create_idb_base.sql
@rem psql --username=root --password --dbname=my_db --file=%cd%\create-structure.sql
@rem psql --username=root --password --dbname=my_db -c "\encoding UTF8" --file=%cd%\test-fill.sql
@pause