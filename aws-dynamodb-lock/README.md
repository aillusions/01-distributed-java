

#
# Build and install locally this dependency: 
#
    <dependency>
        <groupId>software.amazon.awssdk</groupId>
        <artifactId>dynamodb</artifactId>
        <version>2.3.5</version>
    </dependency>

git clone git@github.com:awslabs/dynamodb-lock-client.git

cd dynamodb-lock-client

mvn install



#
# Create table
#



aws dynamodb create-table \
    --table-name lockTable \
    --attribute-definitions \
        AttributeName=key,AttributeType=S \
    --key-schema AttributeName=key,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
    --profile alex_zalizniak_com_dev --endpoint-url http://localhost:8000 
    
    
#
# Read more:
#   https://github.com/awslabs/dynamodb-lock-client
#   https://aws.amazon.com/blogs/database/building-distributed-locks-with-the-dynamodb-lock-client/
#    
    