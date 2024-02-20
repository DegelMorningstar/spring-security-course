package com.curso.api.springsecuritycourse.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ApiError implements Serializable {
    private String backMsg;
    private String msgE;
    private String url;
    private String method;
    private LocalDateTime timestamp;

    public String getBackMsg() {
        return backMsg;
    }

    public void setBackMsg(String backMsg) {
        this.backMsg = backMsg;
    }

    public String getMsgE() {
        return msgE;
    }

    public void setMsgE(String msgE) {
        this.msgE = msgE;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
