
cd grpc-nodejs\js

npm init

# npm install protobufjs --save
# npm install --save grpc
# npm install -g grpcli
# node node_modules/protobufjs/cli/bin/pbjs -t static-module -w commonjs -o model.js F:\work\01-distributed-java\grpc-nodejs\src\main\proto\HelloService.proto
# grpcli  -f ../src/main/proto/HelloService.proto --ip=127.0.0.1 --port=50050 -i

=====

npm install -g grpc-tools

grpc_tools_node_protoc --js_out=import_style=commonjs,binary:./gen --grpc_out=./gen --proto_path=../src/main/proto HelloService.proto

run GrpcNodejsApplication

node index.js