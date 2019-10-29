package com.shensiyuan.grpc;

import com.shensiyuan.proto.*;
import com.shensiyuan.protobuf.DataInfo;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class GrpcClient {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8899)
                .usePlaintext().build();

        StudentServiceGrpc.StudentServiceBlockingStub blockingStub =
                StudentServiceGrpc.newBlockingStub(managedChannel);
        StudentServiceGrpc.StudentServiceStub stub =
                StudentServiceGrpc.newStub(managedChannel);


        MyResponse myResponse = blockingStub.getRealnameByUsername(MyRequest.newBuilder()
                .setUsername("ZhangSan").build());

        System.out.println(myResponse.getRealname());

        System.out.println("--------------------------------------");

//        Iterator<StudentResponse> iter = blockingStub.getStudentsByAge(StudentRequest.newBuilder()
//                .setAge(20).build());
//
//        while (iter.hasNext()) {
//            StudentResponse studentResponse = iter.next();
//            System.out.println(studentResponse.getName() + ", " + studentResponse.getAge()
//                    + ", " + studentResponse.getCity());
//        }
//        System.out.println("--------------------------------------");
//        StreamObserver<StudentResponseList> streamObserver = new StreamObserver<StudentResponseList>() {
//            @Override
//            public void onNext(StudentResponseList value) {
//                value.getStudentResponseList().forEach(StudentResponse -> {
//                    System.out.println(StudentResponse.getName());
//                    System.out.println(StudentResponse.getAge());
//                    System.out.println(StudentResponse.getCity());
//                    System.out.println("*************************");
//                });
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                System.out.println(t.getMessage());
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("completed");
//            }
//        };
//
//        StreamObserver<StudentRequest> studentRequestStreamObserver = stub.getStudentWrapperByAge(streamObserver);
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(40).build());
//        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(50).build());
//        studentRequestStreamObserver.onCompleted();
//
//        System.out.println("--------------------------------------");
//
//        StreamObserver<StreamRequest> streamRequestStreamObserver = stub.biTalk(new StreamObserver<StreamResponse>() {
//            @Override
//            public void onNext(StreamResponse value) {
//                System.out.println(value.getResponseInfo());
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                System.out.println(t.getMessage());
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("completed");
//            }
//        });
//
//        for (int i=0; i<10; i++) {
//            streamRequestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e){
//                e.printStackTrace();
//            }
//        }

        managedChannel.shutdown().awaitTermination(50, TimeUnit.SECONDS);
    }
}
