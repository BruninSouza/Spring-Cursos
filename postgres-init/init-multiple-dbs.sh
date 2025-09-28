#!/bin/bash
set -e

create_database() {
    local database=$1
    echo "  Criando a base de dados '$database'..."
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
        SELECT 'CREATE DATABASE $database' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = '$database')\gexec
EOSQL
}

if [ -n "$POSTGRES_MULTIPLE_DBS" ]; then
    echo "Iniciando a criação de múltiplas bases de dados..."
    for db in $(echo $POSTGRES_MULTIPLE_DBS | tr ',' ' '); do
        create_database $db
    done
    echo "Criação de múltiplas bases de dados concluída."
fi