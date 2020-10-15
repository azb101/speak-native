#!/bin/bash
set -e

sleep 30s

#run the setup script to create the DB and the schema in the DB
echo "Start running migrations..."


for migration in `ls /usr/db/Migrations/*.sql | sort -V`; 
    do 
        echo "Applying migration: $migration"
        /opt/mssql-tools/bin/sqlcmd -S localhost -U "SA" -P "${MSSQL_SA_PASSWORD}" -i $migration 
    done

echo "Migrations have been applied"
