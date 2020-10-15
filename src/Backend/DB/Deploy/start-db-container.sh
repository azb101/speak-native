# to run this file: sh start-db-container.sh

# this file is for manual intervention, main work is done by docker-compose
docker stop sn-db
docker rm sn-db
docker image rm sn-db-image

docker build -t sn-db-image .

docker run \
        -e 'ACCEPT_EULA=Y' \
        -e 'SA_PASSWORD=Password1!' \
        -e 'MSSQL_PID=DEVELOPER' \
        -e 'SQLCOLLATION=Cyrillic_General_CI_AS' \
        -e 'TZ=Europe/Warsaw'\
        --name sn-db \
        -p 1433:1433 \
        -d sn-db-image