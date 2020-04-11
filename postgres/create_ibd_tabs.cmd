@echo off
set Path=%Path%;c:\pgsql\bin\
psql --username=root --password --dbname="ishop" --command="\encoding UTF8" --file=create_idb_tabs.sql
@rem psql --username=root --password --dbname=my_db --file=%cd%\create-structure.sql
@rem psql --username=root --password --dbname=my_db -c "\encoding UTF8" --file=%cd%\test-fill.sql
@pause