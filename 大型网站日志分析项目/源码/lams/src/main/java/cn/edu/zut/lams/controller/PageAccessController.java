package cn.edu.zut.lams.controller;


import cn.edu.zut.lams.common.util.GetDate;
import cn.edu.zut.lams.entry.PageAccess;
import cn.edu.zut.lams.service.IPageAccessService;
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
@RequestMapping("/page")
public class PageAccessController {
    @Autowired
    IPageAccessService ipaService;

    @GetMapping("")
    public Integer getPageAccessNum(@RequestBody String urlString) {
        String yesterdayDate = GetDate.getYesterdayDate();//获取昨日日期
        JSONObject jsonObject = JSON.parseObject(urlString);
        String url = jsonObject.getString("url");
        QueryWrapper<PageAccess> wrapper = new QueryWrapper<>();
        wrapper.select().in("create_time", yesterdayDate).in("url", url);
        PageAccess pageAccess = ipaService.getOne(wrapper);
        if (pageAccess == null) return -1;
        return pageAccess.getPageAccessedNum().intValue();
    }
}
