package cn.edu.zut.lams.controller;


import cn.edu.zut.lams.common.util.GetDate;
import cn.edu.zut.lams.entry.NewAddUser;
import cn.edu.zut.lams.service.INewAddUserService;
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
@RequestMapping("/user/add")
public class NewAddUserController {
    @Autowired
    INewAddUserService newAddUserService;

    @GetMapping("/count")
    public Object getNewAddUser() {
        String[] times = new String[7];
        Integer[] counts = new Integer[7];
        int num = 0;
        String beforedayDate;
        NewAddUser newAddUser;
        Dict dict = Dict.create();
        for (int i = 7; i > 0; i--) {
            QueryWrapper<NewAddUser> wrapper = new QueryWrapper<>();
            beforedayDate = GetDate.getYesterdayDate(i);
            wrapper.select().in("create_time", beforedayDate);
            newAddUser = newAddUserService.getOne(wrapper);
            if (newAddUser != null) {
                times[num] = GetDate.getMDFormat(beforedayDate).split("/")[1]+"日";// 格式为: 月/日-->日
                counts[num] = newAddUser.getAddUserNum();
            }
            num++;
        }
        dict.set("times", times).set("counts", counts);

        return dict;
    }
}
