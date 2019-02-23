git clone git@github.com:lyft/protoc-gen-validate.git

set GOROOT=f:\env\Go
set GOPATH=F:\work\01-distributed-java\protoc-gen-validate
set Path=%Path%;f:\env\GnuWin32\make\bin;f:\env\Go\bin
cd  protoc-gen-validate

go get -u github.com/lyft/protoc-gen-star
go get -u github.com/lyft/protoc-gen-validate
go get -d github.com/lyft/protoc-gen-validate

make build


cd protoc-gen-validate/java

set JAVA_HOME=f:\env\Java\jdk1.8.0_191
mvn install