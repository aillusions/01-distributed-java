

export VAULT_ADDR='http://127.0.0.1:8200'

vault operator init

vault login
    myroot
    
vault token create

vault secrets enable transit

vault write -force transit/keys/my-key
    
vault kv put secret/my-application example.username=demouser example.password=demopassword

Run HashicorpVaultApplication with -Dspring.cloud.vault.token=myroot
or uncomment token: myroot in bootstrap.yml