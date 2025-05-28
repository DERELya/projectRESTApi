@echo off
set PGPASSWORD=postgres
set PGUSER=postgres
set PGDATABASE=api_project3
set BACKUP_PATH=C:\Users\DERELya\IdeaProjects\project3\dump.sql

set PGDUMP_PATH="C:\Program Files\PostgreSQL\17\bin\pg_dump.exe"

REM Выполнение экспорта
%PGDUMP_PATH% -U %PGUSER% -d %PGDATABASE% -F p -f "%BACKUP_PATH%"
if %errorlevel% equ 0 (
    echo Экспорт базы данных успешно завершён. Файл: %BACKUP_PATH%
) else (
    echo Ошибка при экспорте базы данных.
)
pause