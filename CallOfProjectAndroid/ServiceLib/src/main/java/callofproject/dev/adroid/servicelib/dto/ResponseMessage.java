package callofproject.dev.adroid.servicelib.dto;

import com.google.gson.annotations.SerializedName;

public class ResponseMessage<T> {
    @SerializedName("message")
    private String message;
    @SerializedName("status_code")
    private int statusCode;
    @SerializedName("object")
    private T object;

    public ResponseMessage(String message, int statusCode, T object) {
        this.message = message;
        this.statusCode = statusCode;
        this.object = object;
    }

    public String getMessage() {
        return message;
    }


    public T getObject() {
        return object;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setObject(T object) {
        this.object = object;
    }
}