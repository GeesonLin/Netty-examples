package com.shensiyuan.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GrpcServer {
    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(8899).addService(new StudentServiceImpl()).build().start();

        System.out.println("Server started");

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                GrpcServer.this.stop();
                System.err.println("*** server shut down");
            }
        });

        System.out.println("执行到这里");
    }

    private void stop() {
        if (null != this.server) {
            server.shutdown();
        }
    }

    private  void awaitTermination() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server = new GrpcServer();
        server.start();
        server.awaitTermination();
    }
}
