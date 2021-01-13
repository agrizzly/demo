package com.grizzly.security.utils;

import com.grizzly.utils.result.Result;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author Bxsheng
 * @blogAddress www.kdream.cn
 * @createTIme 2020/9/16
 * since JDK 1.8
 */
public class JwtTokenUtils {
    public static final String TOKEN_HEADER = "token";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SECRET = "com.grizzly.security";
    public static final String ISS = "grizzly";

    private static final Long EXPIRATION = 60 * 60 * 3L; //过期时间3小时
    private static final String ROLES = "roles";

    //创建token
    public static String createToken(String username, List<String> roles, boolean isRememberMe){
        Map map = new HashMap();
        map.put(ROLES, roles);
        String token = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(map)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .compact();
        return TOKEN_PREFIX + token;
    }

    //从token中获取用户名
    public static String getUserName(String token){
        String username;
        try {
            username = getTokenBody(token).getSubject();
        } catch (    Exception e){
            username = null;
        }
        return username;
    }

    public static List getUserRoles(String token){
        return (List) getTokenBody(token).get(ROLES);
    }

    private static Claims getTokenBody(String token){
        Claims claims = null;
        try{
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch(ExpiredJwtException e){
            e.printStackTrace();
        } catch(UnsupportedJwtException e){
            e.printStackTrace();
        } catch(MalformedJwtException e){
            e.printStackTrace();
        } catch(SignatureException e){
            e.printStackTrace();
        } catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        return claims;
    }

    //是否已过期
    public static boolean isExpiration(String token){
        try{
            return getTokenBody(token).getExpiration().before(new Date());
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return true;
    }
}