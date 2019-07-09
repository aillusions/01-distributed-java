

export VAULT_ADDR='http://127.0.0.1:8200'
vault login
    myroot
    
vault secrets enable database


vault write database/config/my-mysql-database \
    plugin_name=mysql-database-plugin \
    connection_url="{{username}}:{{password}}@tcp(dj-mysql-srv:3306)/" \
    allowed_roles="my-role" \
    username="root" \
    password="qwerty"
    
    
    
vault write database/roles/my-role \
    db_name=my-mysql-database \
    creation_statements="CREATE USER '{{name}}'@'%' IDENTIFIED BY '{{password}}';GRANT SELECT ON *.* TO '{{name}}'@'%';" \
    default_ttl="1h" \
    max_ttl="24h"
    
    
vault read database/creds/my-role

    Key                Value
    ---                -----
    lease_id           database/creds/my-role/oaZ5JvdeNf7jzL6vAtNy9p1o
    lease_duration     1h
    lease_renewable    true
    password           A1a-yWvZLvijrfvXgwQu
    username           v-token-my-role-1NgVdWXaQYKCXgPn
