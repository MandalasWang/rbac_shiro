package ink.boyuan.rbac_shiro.enums;

import java.io.Serializable;

/**
 * @author wyy
 * @version 1.0
 * @Classname JsonData
 * @date 2020/4/20 10:48
 * @description
 **/
public class JsonData implements Serializable {

    private Integer code;

    private String msg;

    private Object data;


    public JsonData() {
    }

    public JsonData(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonData(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static JsonData buildSuccess(int code,String msg){
        return new JsonData(code,msg);
    }

    public static JsonData buildSuccess(int code,String msg,Object obj){
        return new JsonData(code,msg,obj);
    }

    public static JsonData buildSuccess(){
        return new JsonData(200,"请求成功");
    }

    public static JsonData buildSuccess(Object obj){
        return new JsonData(200,"请求成功",obj);
    }
}
