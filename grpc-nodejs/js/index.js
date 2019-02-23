'use strict';

var messages = require('./gen/HelloService_pb');
var services = require('./gen/HelloService_grpc_pb');

var grpc = require('grpc');

function main() {
    var client = new services.HelloServiceClient(
        'localhost:6565',
        grpc.credentials.createInsecure());

    var request = new messages.HelloRequest();
    request.setFirstname('Alex');
    request.setLastname('Good');
    client.hello(request, function (err, response) {
        console.log('Greeting:', response.getGreeting());
    });
}

main();