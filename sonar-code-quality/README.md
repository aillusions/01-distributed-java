

cd sonar-code-quality
mvn sonar:sonar -Dsonar.projectName=dj-sonar-code-quality -Dsonar.host.url=http://localhost:9000
mvn sonar:sonar -Dsonar.projectName=dj-sonar-code-quality -Dsonar.host.url=http://localhost:9000 -Dmaven.test.skip=true

http://localhost:9000/dashboard?id=com.zalizniak%3Asonar-code-quality