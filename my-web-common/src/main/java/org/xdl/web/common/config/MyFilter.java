package org.xdl.web.common.config;

import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author XieDuoLiang
 * @date 2021/11/17 下午3:36
 */
@Component
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Long startTime = System.currentTimeMillis();
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("接口执行时间：" + (System.currentTimeMillis() - startTime) + "ms");
    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }
}
