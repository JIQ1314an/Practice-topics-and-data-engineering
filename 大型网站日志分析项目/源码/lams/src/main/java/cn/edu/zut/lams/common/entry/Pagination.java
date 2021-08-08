package cn.edu.zut.lams.common.entry;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * 分页参数
 *
 * @author zhangby
 * @date 2019-05-15 14:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {

    /**
     * 当前页数
     */

    private Integer current;
    /**
     * 每页页数
     */

    private Integer size;

    /**
     * 获取分页对象
     *
     * @return Page
     */
    public Page page() {
        return new Page(
                Optional.ofNullable(this.current).orElse(1),
                Optional.ofNullable(this.size).orElse(10)
        );
    }
}
