FROM justin2004/mssql_server_tiny

ENV dbName="[SpeakNativeDB]"
ENV backupFile="test100.bpk"
ENV backupDestPath="/var/bpk/$backupFile"
ENV backupSourcePath="/${backupFile}"

COPY ${backupSourcePath} ${backupDestPath}

CMD /opt/mssql-tools/bin/sqlcmd -U "SA" -P "${MSSQL_SA_PASSWORD}" -Q "RESTORE DATABASE ${dbName} FROM DISK='${backupDestPath}' ; " & /opt/mssql/bin/sqlservr