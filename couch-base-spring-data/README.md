https://docs.spring.io/spring-data/couchbase/docs/current/reference/html/
https://mvnrepository.com/artifact/org.springframework.data/spring-data-couchbase

https://www.baeldung.com/spring-data-couchbase

https://github.com/carlosCharz/springdatacouchbase
https://github.com/eugenp/tutorials/tree/master/persistence-modules/spring-data-couchbase-2

https://docs.couchbase.com/java-sdk/current/compatibility-versions-features.html

https://github.com/spring-projects/spring-data-couchbase



CREATE PRIMARY INDEX `#primary` ON `users`
CREATE INDEX `userRepositorySecondaryIndex` ON `users`(`_class`) WHERE (`_class` = "com.zalizniak.couchbasespringdata.User")