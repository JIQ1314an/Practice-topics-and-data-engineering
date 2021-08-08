package cn.edu.zut.lams.common.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhang
 * @date 2020/12/17 9:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO<T> {
    private int code;
    private String msg;
    // private boolean success;
    private T data;

    public ResultVO(int code, String msg, boolean success) {
        this.code = code;
        this.msg = msg;
        // this.success = success;
    }

    public ResultVO(T data) {
        this.code = 0;
        this.msg = "操作成功";
        //  this.success = true;
        this.data = data;
    }
}
