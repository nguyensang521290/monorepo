#!/bin/sh

# Script to create multiple databases in PostgreSQL container.
# Referenced by docker-compose volume mount to /docker-entrypoint-initdb.d/

set -e
set -u

create_database() {
    local database=$1
    echo "Creating database '$database'..."
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
        CREATE DATABASE "$database";
        GRANT ALL PRIVILEGES ON DATABASE "$database" TO "$POSTGRES_USER";
EOSQL
    echo "Database '$database' created."
}

if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
    echo "Multiple databases requested: $POSTGRES_MULTIPLE_DATABASES"
    for db in $(echo "$POSTGRES_MULTIPLE_DATABASES" | tr ',' ' '); do
        create_database "$db"
    done
    echo "All databases created."
fi
