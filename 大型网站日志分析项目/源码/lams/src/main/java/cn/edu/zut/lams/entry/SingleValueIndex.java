package cn.edu.zut.lams.entry;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("single_value_index")
public class SingleValueIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ip访问总数
     */
    @TableField("total_ip_access")
    private Long totalIpAccess;

    /**
     * 页面的访问总量
     */
    @TableField("total_page_num")
    private Long totalPageNum;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private String createTime;


}
