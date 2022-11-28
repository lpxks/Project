package com.root.Filter;

import com.alibaba.fastjson.JSON;
import com.root.Common.R;
import com.root.Utlis.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//检查是否登录的过滤器
@Slf4j
@WebFilter(urlPatterns = {"/*"})//配置拦截路径
public class LoginFilter implements Filter {

    //要放行的路径
    private final String[] url = new String[]{"/employee/login","/employee/logout",
                "/backend/**","/front/**","/code/getCode","/user/login"};

    //虽然放行了页面 当时一加载页面就会发送ajax请求 这样就会被过滤器拦截
    //路径匹配器 可以"翻译类似于 * 之类的通配符"
    //匹配规则如下
    /*
        1.'？'匹配一个字符
        2.'*'匹配0个或多个字符
        3.'**'匹配0个或多个目录
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //假如是非必要的处理请求路径直接放行
        if(check(request.getRequestURI()))
        {
            filterChain.doFilter(request,response);
            return;
        }
        //后端登录
        if(request.getSession().getAttribute("employee") != null)
        {
            //从session取不到数据 表示当前未登录 跳转至登录页面
            //将页面要认证失败的数据带过去 code = 0 msg = NOTLOGIN
            ThreadLocalUtils.setData((Long)request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }
        //前端登录
        else if(request.getSession().getAttribute("user") != null)
        {
            //从session取不到数据 表示当前未登录 跳转至登录页面
            //将页面要认证失败的数据带过去 code = 0 msg = NOTLOGIN
            ThreadLocalUtils.setData((long)request.getSession().getAttribute("user"));
            filterChain.doFilter(request,response);
            return;
        }
        //不是前端登录也不是后端登录
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    //判断当前请求是否是必要处理的路径 不必要返回true 反之返回false
    public boolean check(String requestUrl)
    {
        for(String str : url)
        {
            if(antPathMatcher.match(str,requestUrl))
                return true;
        }
        return false;
    }
}
