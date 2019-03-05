

https://start.spring.io/


Kafka Manager:
    http://localhost:9000
    
PG Admin
    http://localhost:82
    
    pgadmin4@pgadmin.org / admin
    
    Endpoint: dj-postgres-srv 5432
    DB: dj_pg_db
    User: dj_pg_db_user / qwerty    
    
Adminer: 
    http://localhost:81   
    
    Mysql:
        Host: dj-mysql-srv 3306
        User:  root / qwerty 
        DB: dj-db

Redis commander: 
    http://localhost:8081      
    
Active MQ:     
    http://localhost:8161/admin/queues.jsp
    admin / admin  
    
    
SpringBoot admin:
    http://localhost:1111    
    

springboot-admin 

    Home 
        http://127.0.0.1:8094/

    Actuator     
        http://localhost:8094/actuator/health    
        http://localhost:8094/actuator/loggers
        http://localhost:8094/actuator/conditions
        http://localhost:8094/actuator/auditevents
        http://localhost:8094/actuator/flyway
        http://localhost:8094/actuator/httptrace
        http://localhost:8094/actuator/beans
        http://localhost:8094/actuator
        http://localhost:8094/actuator/metrics
        http://localhost:8094/actuator/metrics/jvm.memory.used
        http://localhost:8094/actuator/metrics/jvm.memory.used?tag=area:nonheap&tag=id:Metaspace
        http://localhost:8094/actuator/scheduledtasks
        http://localhost:8094/actuator/env
        http://localhost:8094/actuator/configprops
        
        
        
TODO: 
    - AWS 
        - SNS
        - SQS
        - Dynamo Db
        
    - ETH blockchain
        
    - Eureka    
    - Spark             
    - Splunk
    - Elastic Search    
    - web socket
    - sonar
    - hbase
    - consistency - availability - partitioning tolerance tradeof 
    - mongodb
    - apache storm
    - java lambda on aws
    
    
    
    
mvn org.xolstice.maven.plugins:protobuf-maven-plugin:0.6.1:compile
mvn org.xolstice.maven.plugins:protobuf-maven-plugin:0.6.1:compile-custom