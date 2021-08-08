package cn.edu.zut.lams.controller;


import cn.edu.zut.lams.common.entry.ResultVO;
import cn.edu.zut.lams.common.util.GetDate;
import cn.edu.zut.lams.entry.Top10IpAccess;
import cn.edu.zut.lams.service.ITop10IpAccessService;
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
@RequestMapping("/ip")
public class Top10IpAccessController {
    @Autowired
    ITop10IpAccessService top10IpAccessService;

    @GetMapping("/top10")
    public Object getAccessTop10Ip() {
        String yesterdayDate = GetDate.getYesterdayDate();
        Dict dict = Dict.create();
        QueryWrapper<Top10IpAccess> wrapper = new QueryWrapper<>();
        wrapper.select().in("create_time", yesterdayDate);
        List<Top10IpAccess> list = top10IpAccessService.list(wrapper);
        String[] ips = new String[10];
        Integer[] count = new Integer[10];
        int num = 0;
        for (Top10IpAccess top10IpAccess : list) {
            ips[num] = top10IpAccess.getIp();
            count[num] = top10IpAccess.getAccessPageNum().intValue();
            num++;
            if(num>=10) break;
        }
        dict.set("ips", ips).set("count", count);

        return dict;
    }

}
