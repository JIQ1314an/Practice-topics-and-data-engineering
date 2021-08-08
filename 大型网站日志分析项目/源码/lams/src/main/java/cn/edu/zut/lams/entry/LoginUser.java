package cn.edu.zut.lams.entry;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author jiquan
 * @since 2020-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("login_user")
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日登录用户数
     */
    private Integer loginUserNum;

    /**
     * 创建时间
     */
    private String createTime;


}
