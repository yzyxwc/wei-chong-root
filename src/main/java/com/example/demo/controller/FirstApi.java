package com.example.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(description = "测试类")
@RequestMapping("/api/test")
public class FirstApi {
    @RequestMapping(value = "test",method = RequestMethod.GET)
    @ApiOperation(value = "测试API", notes = "测试API")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization",
                    value = "Authorization token",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    public String test(HttpServletRequest request){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return "hello" + userName;
    }
}
