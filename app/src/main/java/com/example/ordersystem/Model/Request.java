package com.example.ordersystem.Model;

import java.sql.Time;
import java.sql.Timestamp;

public class Request {
    private int requestId;
    private int requestNumber;
    private String requestdateTime;
    private String requestStatus;
    private String requestCater;
    private String requestName;
    private String requestTotal;

    public Request(int requestId, int requestNumber, String requestdateTime, String requestStatus, String requestCater, String requestName, String requestTotal) {
        this.requestId = requestId;
        this.requestNumber = requestNumber;
        this.requestdateTime = requestdateTime;
        this.requestStatus = requestStatus;
        this.requestCater = requestCater;
        this.requestName = requestName;
        this.requestTotal = requestTotal;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getrequestNumber() {return requestNumber; }

    public String getRequestdateTime() {
        return requestdateTime;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public String getRequestCater() {
        return requestCater;
    }

    public String getRequestName() {
        return requestName;
    }

    public String getRequestTotal() {
        return requestTotal;
    }

}
