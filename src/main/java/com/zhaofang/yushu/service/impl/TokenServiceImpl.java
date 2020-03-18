package com.zhaofang.yushu.service.impl;

import com.zhaofang.yushu.common.RedisCommonUtil;
import com.zhaofang.yushu.exception.ApiException;
import com.zhaofang.yushu.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "abc";

    private static final Integer ResponseCode_ILLEGAL_ARGUMENT = 100;

    private static final Integer ResponseCode_REPETITIVE_OPERATION = 200;


    @Autowired
    private RedisCommonUtil redisCommonUtil;

    /**
     * 创建token
     * @return
     */
    @Override
    public String createToken() {

        String str = "cdf";
        StringBuilder token = new StringBuilder();
        token.append(TOKEN_NAME).append(str);
        try {
            redisCommonUtil.setEx(token.toString(),token.toString(),1000L);
            boolean notEmpty = StringUtils.isNotEmpty(token.toString());
            if (notEmpty){
                return token.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 校验token
     * @param request
     * @return
     */
    @Override
    public boolean checkToken(HttpServletRequest request) {

        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isBlank(token)){ //header不存在token
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isBlank(token)){// parameter中也不存在token
                throw new ApiException(ResponseCode_ILLEGAL_ARGUMENT);
            }
        }
        if (redisCommonUtil.exists(token)){
            throw new ApiException(ResponseCode_REPETITIVE_OPERATION);
        }

        boolean removeFlag = redisCommonUtil.remove(token);

        if (!removeFlag){
            throw new ApiException(ResponseCode_REPETITIVE_OPERATION);
        }
        return true;
    }











}
