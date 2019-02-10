

#####################################################
#
# Managed instance
#
#####################################################

1. Register on AWS 

2. Create user with DynamoDB (and optionally CloudFormation) policy and Get Access keys (aws_access_key_id / aws_secret_access_key)

2. Install AWS CLI

3. Configure two files:

    ~/.aws/config
        
        
        [alex_zalizniak_com_dev]
        region = eu-west-1
        output = json
        
        [profile alex_zalizniak_com_dev]
        region = eu-west-1
        output = json
        
    ~/.aws/credentials

        [alex_zalizniak_com_dev]
        aws_access_key_id = AKIAIYZ....
        aws_secret_access_key = mY1T7R6SUV.....



# aws dynamodb describe-endpoints --profile alex_zalizniak_com_dev
# aws dynamodb describe-limits --profile alex_zalizniak_com_dev

aws cloudformation create-stack --stack-name myDynamoDBTableStack --template-body file://DynamoDB_Table.template --profile alex_zalizniak_com_dev

aws dynamodb describe-table --table-name myDynamoDBTable --profile alex_zalizniak_com_dev

# aws cloudformation delete-stack  --stack-name myDynamoDBTableStack --profile alex_zalizniak_com_dev



######################################################
#
#    Local instance
#
######################################################


aws dynamodb list-tables  --profile alex_zalizniak_com_dev --endpoint-url http://localhost:8000 


aws dynamodb create-table \
    --table-name Music \
    --attribute-definitions \
        AttributeName=Artist,AttributeType=S \
        AttributeName=SongTitle,AttributeType=S \
    --key-schema AttributeName=Artist,KeyType=HASH AttributeName=SongTitle,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
    --profile alex_zalizniak_com_dev --endpoint-url http://localhost:8000 
    
    
    
    
aws dynamodb put-item \
--table-name Music  \
--item \
    '{"Artist": {"S": "No One You Know"}, "SongTitle": {"S": "Call Me Today"}, "AlbumTitle": {"S": "Somewhat Famous"}}' \
--return-consumed-capacity TOTAL \
--profile alex_zalizniak_com_dev --endpoint-url http://localhost:8000 



aws dynamodb put-item \
    --table-name Music \
    --item '{ \
        "Artist": {"S": "Acme Band"}, \
        "SongTitle": {"S": "Happy Day"}, \
        "AlbumTitle": {"S": "Songs About Life"} }' \
    --return-consumed-capacity TOTAL \
    --profile alex_zalizniak_com_dev --endpoint-url http://localhost:8000 