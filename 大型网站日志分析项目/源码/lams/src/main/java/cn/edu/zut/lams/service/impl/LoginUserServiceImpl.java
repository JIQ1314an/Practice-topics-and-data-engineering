package cn.edu.zut.lams.service.impl;

import cn.edu.zut.lams.entry.LoginUser;
import cn.edu.zut.lams.mapper.LoginUserMapper;
import cn.edu.zut.lams.service.ILoginUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jiquan
 * @since 2020-12-25
 */
@Service
public class LoginUserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser> implements ILoginUserService {

}
