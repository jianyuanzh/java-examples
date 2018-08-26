package cc.databus.springboot.demo.pojo;

public class JsonResponse {

    private int code;
    private String errMsg;
    private Object object;

    public JsonResponse() {

    }

    public JsonResponse(int code, String errMsg, Object object) {
        this.code = code;
        this.errMsg = errMsg;
        this.object = object;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
