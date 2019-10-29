package com.shensiyuan.netty.handler3;

public class PersonProtocol {
    private int length;
    private byte[] content;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }
}
