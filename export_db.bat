@echo off
set PGPASSWORD=postgres
set PGUSER=postgres
set PGDATABASE=api_project3
set BACKUP_PATH=C:\Users\DERELya\IdeaProjects\project3\dump.sql

set PGDUMP_PATH="C:\Program Files\PostgreSQL\17\bin\pg_dump.exe"

REM �믮������ �ᯮ��
%PGDUMP_PATH% -U %PGUSER% -d %PGDATABASE% -F p -f "%BACKUP_PATH%"
if %errorlevel% equ 0 (
    echo ��ᯮ�� ���� ������ �ᯥ譮 �������. ����: %BACKUP_PATH%
) else (
    echo �訡�� �� �ᯮ�� ���� ������.
)
pause