#!/bin/sh -e

psql --variable=ON_ERROR_STOP=1 --username "postgres" <<-EOSQL
    CREATE ROLE dj_pg_db_user WITH LOGIN PASSWORD 'qwerty';
    CREATE DATABASE "dj_pg_db" OWNER = dj_pg_db_user;
    GRANT ALL PRIVILEGES ON DATABASE "dj_pg_db" TO dj_pg_db_user;
EOSQL