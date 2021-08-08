package cn.edu.zut.lams.common.config;


import cn.edu.zut.lams.common.entry.ResultCode;

/**
 * 自定义API接口异常
 *
 * @author zhang
 * @date 2020/11/1 13:51
 */
public class APIException extends RuntimeException {

    private int code;
    private String msg;

    public APIException() {
        this(ResultCode.RESPONSE_FAILED);
    }

    public APIException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public APIException(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
