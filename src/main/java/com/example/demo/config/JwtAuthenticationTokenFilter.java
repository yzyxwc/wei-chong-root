package com.example.demo.config;

import com.example.demo.entity.User;
import com.example.demo.entity.UserDetail;
import com.example.demo.util.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token校验,引用的stackoverflow一个答案里的处理方式
 * @author wc
 * @date 2020-04-16
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.header}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Resource
    JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(this.tokenHeader);
        if(StringUtils.isNotEmpty(token) && token.startsWith(this.tokenHead)){
            token = token.substring(tokenHead.length());
        }else{
            //不规范的token传入
            token = null;
        }

        String userName = jwtUtils.getUsernameFromToken(token);
        logger.info(String.format("Checking authentication for user %s.", userName));

        if(null != userName && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetail user = jwtUtils.getUserFromToken(token);
            if(jwtUtils.validateToken(token,user)){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info(String.format("Authenticated userDetail %s, setting security context", userName));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        filterChain.doFilter(request,response);
    }
}
