FROM justin2004/mssql_server_tiny

# Create work directory
RUN mkdir -p /usr/db
WORKDIR /usr/db

# Copy all scripts into working directory
COPY ./Scripts /usr/db/Scripts
COPY ./Migrations /usr/db/Migrations

RUN chmod +x /usr/db/Scripts/run_migrations.sh

# Grant permissions for the import-data script to be executable
# RUN chmod +x /usr/work/import-data.sh
EXPOSE 1433
CMD /bin/bash ./Scripts/entrypoint.sh