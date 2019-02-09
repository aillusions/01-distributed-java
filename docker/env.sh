#!/bin/bash

docker-compose down
#docker-compose up dj-nginx dj-postgres dj-pgadmin dj-redis dj-redis-commander dj-zoo dj-kafka dj-zoo-nav-api dj-zoo-nav-web dj-kafka-manager dj-amq
docker-compose up dj-cassandra
