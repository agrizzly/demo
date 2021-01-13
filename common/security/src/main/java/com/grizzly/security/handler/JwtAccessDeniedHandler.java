package com.grizzly.security.handler;

import com.grizzly.utils.result.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Bxsheng
 * @blogAddress www.kdream.cn
 * @createTIme 2020/9/17
 * since JDK 1.8
 * 当用户在没有授权的时候，返回的指定信息
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        System.out.println("用户没有访问权限");
        System.out.println(e.getMessage());
//        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e==null?"用户没有访问权限":e.getMessage());

        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        out.write(Result.fail("用户没有访问权限").toJSONString());
        out.flush();
        out.close();
    }
}