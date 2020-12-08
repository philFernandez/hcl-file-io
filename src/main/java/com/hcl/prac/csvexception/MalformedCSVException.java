package com.hcl.prac.csvexception;

public class MalformedCSVException extends Exception {

    private static final long serialVersionUID = 1L;

    public MalformedCSVException(String msg, Throwable err) {
        super(msg, err);
    }

    public MalformedCSVException(String msg) {
        super(msg);
    }

}
