#!/bin/bash

docker-compose down
docker-compose pull
docker-compose up dj-datastax-dse dj-datastax-studio

#docker-compose up dj-nginx dj-postgres dj-pgadmin dj-redis dj-redis-commander dj-zoo dj-kafka dj-zoo-nav-api dj-zoo-nav-web dj-kafka-manager dj-amq
#docker-compose up dj-cassandra
#docker-compose up dj-dynamodb
#docker-compose up dj-spark-master dj-spark-worker-1
#docker-compose up dj-zoo-exhibitor dj-zoo-nav-web dj-zoo-nav-api