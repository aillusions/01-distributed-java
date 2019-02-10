

Build and install locally this dependency: 

    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>dynamodb</artifactId>
        <version>2.3.5</version>
    </dependency>

git clone git@github.com:awslabs/dynamodb-lock-client.git

cd dynamodb-lock-client

mvn install





aws dynamodb create-table \
    --table-name lockTable \
    --attribute-definitions \
        AttributeName=key,AttributeType=S \
    --key-schema AttributeName=key,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
    --profile alex_zalizniak_com_dev --endpoint-url http://localhost:8000 