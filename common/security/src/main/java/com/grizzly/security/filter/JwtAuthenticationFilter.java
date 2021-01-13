package com.grizzly.security.filter;

import com.grizzly.security.utils.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        //如果请求头中没有Authorization信息则直接放行了
        if(tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }
        //如果请求头中有token,则进行解析，并且设置认证信息
        String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        if(!JwtTokenUtils.isExpiration(token)){
            //设置上下文
            UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        super.doFilterInternal(request, response, chain);
    }

    //获取用户信息
    private UsernamePasswordAuthenticationToken getAuthentication(String token){
        String username = JwtTokenUtils.getUserName(token);
        // 获得权限 添加到权限上去
        List<String> roleslist = JwtTokenUtils.getUserRoles(token);
        List<GrantedAuthority> roles = new ArrayList<>();
        roleslist.forEach(t -> roles.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return t;
            }
        }));
        if(username != null){
            return new UsernamePasswordAuthenticationToken(username, null,roles);
        }
        return null;
    }
}