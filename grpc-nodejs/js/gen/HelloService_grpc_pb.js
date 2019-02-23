// GENERATED CODE -- DO NOT EDIT!

'use strict';
var grpc = require('grpc');
var HelloService_pb = require('./HelloService_pb.js');

function serialize_com_zalizniak_grpc_nodejs_HelloRequest(arg) {
  if (!(arg instanceof HelloService_pb.HelloRequest)) {
    throw new Error('Expected argument of type com.zalizniak.grpc.nodejs.HelloRequest');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_com_zalizniak_grpc_nodejs_HelloRequest(buffer_arg) {
  return HelloService_pb.HelloRequest.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_com_zalizniak_grpc_nodejs_HelloResponse(arg) {
  if (!(arg instanceof HelloService_pb.HelloResponse)) {
    throw new Error('Expected argument of type com.zalizniak.grpc.nodejs.HelloResponse');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_com_zalizniak_grpc_nodejs_HelloResponse(buffer_arg) {
  return HelloService_pb.HelloResponse.deserializeBinary(new Uint8Array(buffer_arg));
}


var HelloServiceService = exports.HelloServiceService = {
  hello: {
    path: '/com.zalizniak.grpc.nodejs.HelloService/hello',
    requestStream: false,
    responseStream: false,
    requestType: HelloService_pb.HelloRequest,
    responseType: HelloService_pb.HelloResponse,
    requestSerialize: serialize_com_zalizniak_grpc_nodejs_HelloRequest,
    requestDeserialize: deserialize_com_zalizniak_grpc_nodejs_HelloRequest,
    responseSerialize: serialize_com_zalizniak_grpc_nodejs_HelloResponse,
    responseDeserialize: deserialize_com_zalizniak_grpc_nodejs_HelloResponse,
  },
};

exports.HelloServiceClient = grpc.makeGenericClientConstructor(HelloServiceService);
