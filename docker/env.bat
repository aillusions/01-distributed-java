docker-compose down
#docker-compose build
@REM docker-compose up dj-nginx dj-postgres dj-pgadmin dj-redis dj-redis-commander dj-zoo dj-kafka dj-zoo-nav-api dj-zoo-nav-web dj-kafka-manager dj-amq
@REM docker-compose up dj-datastax-dse dj-datastax-studio dj-h2o-3-ai
@REM docker-compose up dj-dynamodb
#docker-compose up dj-zoo dj-zoo-nav-web dj-zoo-nav-api
docker-compose up dj-zoo-exhibitor dj-zoo-nav-web dj-zoo-nav-api

