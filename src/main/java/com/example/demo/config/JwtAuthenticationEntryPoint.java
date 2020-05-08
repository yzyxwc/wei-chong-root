package com.example.demo.config;

import com.example.demo.bean.ResultJson;
import com.example.demo.enums.ResultCode;
import com.sun.xml.internal.fastinfoset.QualifiedName;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * 认证失败处理类，返回401
 * @author wc
 * @date 2020-04-16
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException {
        //验证为未登录状态 会进入此方法 认证错误
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw =response.getWriter();
        String body = ResultJson.failure(ResultCode.UNAUTHORIZED, e.getMessage()).toString();
        pw.write(body);
        pw.flush();
        pw.close();
    }
}
