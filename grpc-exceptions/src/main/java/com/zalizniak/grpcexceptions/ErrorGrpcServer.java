package com.zalizniak.grpcexceptions;


/**
 * Created by rayt on 5/16/16.
 */
public class ErrorGrpcServer {
   /* static public void main(String[] args) throws IOException, InterruptedException {
        CustomInterceptor customInterceptor = new CustomInterceptor(Arrays.asList(
                IllegalArgumentException.class
        ));
        Server server = ServerBuilder.forPort(8080)
                .addService(ServerInterceptors.intercept(new ErrorServiceImpl(), customInterceptor))
                .build();

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started!");
        server.awaitTermination();
    }*/
}