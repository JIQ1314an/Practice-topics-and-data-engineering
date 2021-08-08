package cn.edu.zut.lams.controller;


import cn.edu.zut.lams.common.util.GetDate;
import cn.edu.zut.lams.entry.LoginUser;
import cn.edu.zut.lams.service.ILoginUserService;
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
@RequestMapping("/user/login")
public class LoginUserController {
    @Autowired
    ILoginUserService loginUserService;

    @GetMapping("/count")
    public Object getLoginUserNum() {
        String[] times = new String[7];
        Integer[] counts = new Integer[7];
        int num = 0;
        String beforedayDate;
        LoginUser loginUser;
        Dict dict = Dict.create();
        for (int i = 7; i > 0; i--) {
            QueryWrapper<LoginUser> wrapper = new QueryWrapper<>();
            beforedayDate = GetDate.getYesterdayDate(i);
            wrapper.select().in("create_time", beforedayDate);
            loginUser = loginUserService.getOne(wrapper);
            if (loginUser != null) {
                times[num] = GetDate.getMDFormat(beforedayDate).split("/")[1]+"日";// 格式为: 月/日 -->日
                counts[num] = loginUser.getLoginUserNum();
            }
            num++;
        }
        dict.set("times", times).set("counts", counts);

        return dict;
    }
}
