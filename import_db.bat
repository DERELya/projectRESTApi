@echo off
set PGPASSWORD=postgres
set PGUSER=postgres
set PGDATABASE=api_project3
set BACKUP_PATH=C:\Users\DERELya\IdeaProjects\project3\dump.sql

set PGRESTORE_PATH_PATH="C:\Program Files\PostgreSQL\17\bin\pg_restore.exe"

REM Очистка базы данных
echo Очистка базы данных...
psql -U %PGUSER% -d %PGDATABASE% -c "DROP SCHEMA public CASCADE; CREATE SCHEMA public;"

set PSQL_PATH="C:\Program Files\PostgreSQL\17\bin\psql.exe"

REM Выполнение импорта
%PSQL_PATH% -U %PGUSER% -d %PGDATABASE% -f "%BACKUP_PATH%"
if %errorlevel% equ 0 (
    echo Импорт базы данных успешно завершён. Источник: %BACKUP_PATH%
) else (
    echo Ошибка при импорте базы данных.
)
pause

