package cn.edu.zut.lams.controller;


import cn.edu.zut.lams.common.util.GetDate;
import cn.edu.zut.lams.entry.IpAccess;
import cn.edu.zut.lams.service.IIpAccessService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/ip")
public class IpAccessController {
    @Autowired
    IIpAccessService ipAccessService;

    @GetMapping("")
    public Integer getPageAccessNum(@RequestBody String ipString) {
        String yesterdayDate = GetDate.getYesterdayDate();//获取昨日日期
        JSONObject jsonObject = JSON.parseObject(ipString);
        String ip = jsonObject.getString("ip");
        QueryWrapper<IpAccess> wrapper = new QueryWrapper<>();
        wrapper.select().in("create_time", yesterdayDate).in("ip", ip);
        IpAccess ipAccess = ipAccessService.getOne(wrapper);
        if (ipAccess == null) return -1;
        return ipAccess.getAccessPageNum().intValue();
    }
}
