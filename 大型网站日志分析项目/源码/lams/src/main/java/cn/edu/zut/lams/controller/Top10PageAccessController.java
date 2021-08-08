package cn.edu.zut.lams.controller;


import cn.edu.zut.lams.common.entry.ResultVO;
import cn.edu.zut.lams.common.util.GetDate;
import cn.edu.zut.lams.entry.Top10PageAccess;
import cn.edu.zut.lams.service.ITop10PageAccessService;
import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jiquan
 * @since 2020-12-25
 */
@RestController
@RequestMapping("/page")
public class Top10PageAccessController {
    @Autowired
    ITop10PageAccessService top10PageAccessService;

    @GetMapping("/top10")
    public Object getAccessTop10Ip() {
        String yesterdayDate = GetDate.getYesterdayDate();
        Dict dict = Dict.create();
        QueryWrapper<Top10PageAccess> wrapper = new QueryWrapper<>();
        wrapper.select().in("create_time", yesterdayDate);
        List<Top10PageAccess> list = top10PageAccessService.list(wrapper);
        String[] pages = new String[10];
        Integer[] count = new Integer[10];
        int num = 0;
        for (Top10PageAccess top10PageAccess : list) {
            pages[num] = top10PageAccess.getUrl();
            count[num] = top10PageAccess.getPageAccessedNum().intValue();
            num++;
            if(num>=10) break;
        }
        dict.set("pages", pages).set("count", count);

        return dict;
    }

}
