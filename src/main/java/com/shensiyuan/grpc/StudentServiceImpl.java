package com.shensiyuan.grpc;

import com.shensiyuan.proto.*;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {
    @Override
    public void getRealnameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("Received client msg:" + request.getUsername());

        responseObserver.onNext(MyResponse.newBuilder().setRealname("张三").build());
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("Received student age:" + request.getAge());

        responseObserver.onNext(StudentResponse.newBuilder().setName("张三").setAge(20).setCity("北京").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("李四").setAge(29).setCity("上海").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("王五").setAge(59).setCity("广州").build());

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StudentRequest> getStudentWrapperByAge(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                System.out.println("Received student age:" + value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                StudentResponse build1 = StudentResponse.newBuilder().setName("张三").setAge(20).setCity("北京").build();
                StudentResponse build2 = StudentResponse.newBuilder().setName("李四").setAge(30).setCity("上海").build();
                StudentResponse build3 = StudentResponse.newBuilder().setName("王五").setAge(40).setCity("广州").build();

                responseObserver.onNext(StudentResponseList.newBuilder().addStudentResponse(build1)
                        .addStudentResponse(build2).addStudentResponse(build3).build());

                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<StreamRequest> biTalk(StreamObserver<StreamResponse> responseObserver) {
        return new StreamObserver<StreamRequest>() {
            @Override
            public void onNext(StreamRequest value) {
                System.out.println(value.getRequestInfo());

                responseObserver.onNext(StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
