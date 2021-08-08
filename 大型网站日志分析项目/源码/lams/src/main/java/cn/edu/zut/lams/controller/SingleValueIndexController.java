package cn.edu.zut.lams.controller;


import cn.edu.zut.lams.common.entry.ResultVO;
import cn.edu.zut.lams.common.util.GetDate;
import cn.edu.zut.lams.entry.SingleValueIndex;
import cn.edu.zut.lams.service.ISingleValueIndexService;
import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jiquan
 * @since 2020-12-25
 */
@RestController
@RequestMapping("/single")
public class SingleValueIndexController {
    @Autowired
    ISingleValueIndexService sviService;

    @GetMapping("")
    public Object getIndex() {
        String yesterdayDate = GetDate.getYesterdayDate();
        Dict dict = Dict.create();
        QueryWrapper<SingleValueIndex> wrapper = new QueryWrapper<>();
        wrapper.select().in("create_time", yesterdayDate);
        SingleValueIndex svi = sviService.getOne(wrapper);
        if (svi == null) return new ResultVO(1, "数据不存在", false);
        dict.set("ips", svi.getTotalIpAccess().intValue())
                .set("pages", svi.getTotalPageNum().intValue());
        return dict;
    }
}
