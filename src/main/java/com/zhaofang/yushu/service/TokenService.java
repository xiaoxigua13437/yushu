package com.zhaofang.yushu.service;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    /**
     * 创建token
     */
    public String createToken();

    /**
     * 校验token
     * @param request
     * @return
     */
    public boolean checkToken(HttpServletRequest request);

}
