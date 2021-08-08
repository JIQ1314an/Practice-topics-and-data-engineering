package cn.edu.zut.lams.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author zhang
 * @date 2020/11/2 23:41
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    /**
     * 进入controller层之前拦截请求
     *
     * @param
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("LOGIN_USER");
        if (user == null) throw new APIException(401, "登录授权失败");
        return true;
    }

    //访问controller之后 访问视图之前被调用
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //访问视图之后被调用
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
