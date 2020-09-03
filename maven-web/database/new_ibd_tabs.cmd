@echo off
set Path=%Path%;c:\pgsql\bin\
set PGPASSWORD=root
psql --username=root --password --dbname=postgres --command="\encoding UTF8" --file=%cd%\create_idb_base.sql
psql --username=root --password --dbname="ishop" --command="\encoding UTF8" --file=create_idb_tabs.sql
psql --username=root --password --dbname="ishop" --command="\encoding UTF8" --file=fill_idb_tabs.sql
@pause