package org.example.damo.model;

import java.util.List;

public class BaseResponseWithAdditionalDateModel extends BaseResponeModel{
    private Object data;

    public BaseResponseWithAdditionalDateModel(String status , String message ,  Object data) {
        super(status , message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
