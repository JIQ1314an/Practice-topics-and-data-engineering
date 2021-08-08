package cn.edu.zut.lams.common.entry;

/**
 * @author zhang
 * @date 2020/11/1 13:32
 */
public enum ResultCode {
    LOGIN_FAILED(101, "登录失败,学号或密码错误"),
    SUCCESS(200, "操作成功"),
    AUTHOR_FAILED(104, "授权失败"),
    CANCEL_FAILED(105, "取消失败"),
    RESPONSE_FAILED(102, "响应失败"),
    VALIDATE_FAILED(408, "请参数校验失败");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
