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
@TableName("top10_ip_access")
public class Top10IpAccess implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ip地址
     */
    private String ip;

    /**
     * ip对应访问页面的次数
     */
    private Long accessPageNum;

    /**
     * 创建时间
     */
    private String createTime;


}
