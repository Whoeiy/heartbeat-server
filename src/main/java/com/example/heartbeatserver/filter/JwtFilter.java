package com.example.heartbeatserver.filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.example.heartbeatserver.util.JwtUtil;
import com.example.heartbeatserver.util.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
@WebFilter(filterName = "JwtFilter", urlPatterns = "/a/*")
public class JwtFilter implements Filter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/a/user/register", "/a/user/login", "/a/index/info")));

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request);
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        // 地址是否不需要拦截
        boolean allowedPath = ALLOWED_PATHS.contains(path);

        if (!allowedPath) {
            response.setCharacterEncoding("UTF-8");
            //获取 header里的token
            final String token = request.getHeader("token");
            try {
                // 添加参数
                Map<String, Claim> userClaimMap = jwtUtil.verifyToken(token);

//                requestWrapper.addParameter("name", userClaimMap.get("name").asString());
                requestWrapper.addParameter("customerId", userClaimMap.get("id").asInt());
            } catch (Exception e) {
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(JSON.toJSONString(ResultGenerator.genFailResult(e.getMessage())));
                return;
            }
        }

        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
