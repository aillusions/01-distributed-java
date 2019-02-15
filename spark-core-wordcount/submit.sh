

mvn clean package


ls -la target/spark-core-wordcount-1.0-SNAPSHOT-jar-with-dependencies.jar
ls -la src/main/resources/spark_example.txt


docker exec -it dj-spark-master-srv mkdir /spark/driver/
docker cp target/spark-core-wordcount-1.0-SNAPSHOT-jar-with-dependencies.jar dj-spark-master-srv:/spark/driver/spark-core-wordcount-1.0-SNAPSHOT-jar-with-dependencies.jar
docker cp src/main/resources/spark_example.txt dj-spark-master-srv:/spark/driver/spark_example.txt
docker exec -it dj-spark-master-srv ls -la /spark/driver/

docker exec -it dj-spark-master-srv /spark/bin/spark-submit \
            --deploy-mode client \
            --class com.zalizniak.sparkcorewordcount.WordCount \
            --master local \
            /spark/driver/spark-core-wordcount-1.0-SNAPSHOT-jar-with-dependencies.jar /spark/driver/spark_example.txt
