package org.example.damo.model;




public class BaseResponeModel {
    private String status; //success or fail
    private String message; //successfully or anything

    public BaseResponeModel(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
