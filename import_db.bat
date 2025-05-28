@echo off
set PGPASSWORD=postgres
set PGUSER=postgres
set PGDATABASE=api_project3
set BACKUP_PATH=C:\Users\DERELya\IdeaProjects\project3\dump.sql

set PGRESTORE_PATH_PATH="C:\Program Files\PostgreSQL\17\bin\pg_restore.exe"

REM ���⪠ ���� ������
echo ���⪠ ���� ������...
psql -U %PGUSER% -d %PGDATABASE% -c "DROP SCHEMA public CASCADE; CREATE SCHEMA public;"

set PSQL_PATH="C:\Program Files\PostgreSQL\17\bin\psql.exe"

REM �믮������ ������
%PSQL_PATH% -U %PGUSER% -d %PGDATABASE% -f "%BACKUP_PATH%"
if %errorlevel% equ 0 (
    echo ������ ���� ������ �ᯥ譮 �������. ���筨�: %BACKUP_PATH%
) else (
    echo �訡�� �� ������ ���� ������.
)
pause

