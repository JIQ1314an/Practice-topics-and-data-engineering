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
@TableName("new_add_user")
public class NewAddUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日新增用户数
     */
    private Integer addUserNum;

    /**
     * 创建时间
     */
    private String createTime;


}
