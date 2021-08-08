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
@TableName("top10_page_access")
public class Top10PageAccess implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * url页面地址
     */
    private String url;

    /**
     * 页面被访问的次数
     */
    private Long pageAccessedNum;

    /**
     * 创建时间
     */
    private String createTime;


}
