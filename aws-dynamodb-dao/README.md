


aws dynamodb describe-endpoints --profile alex_zalizniak_com_dev
aws dynamodb describe-limits --profile alex_zalizniak_com_dev


aws dynamodb describe-table --table-name myDynamoDBTable --profile alex_zalizniak_com_dev

aws cloudformation create-stack --stack-name myteststack --template-body file://DynamoDB_Table.template --profile alex_zalizniak_com_dev