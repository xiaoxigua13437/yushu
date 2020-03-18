package com.zhaofang.yushu.controller;

import cn.hutool.json.JSONUtil;
import com.zhaofang.yushu.entity.ResultVo;
import com.zhaofang.yushu.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private TokenService tokenService;

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessController.class);



    @RequestMapping(value = "/get/token")
    public String getToken()  {

        try {
            String token = tokenService.createToken();
            if (StringUtils.isNotEmpty(token)){
                ResultVo resultVo = new ResultVo();
                resultVo.setCode(200);
                resultVo.setMessage("success");
                resultVo.setData(token);

                return JSONUtil.toJsonStr(resultVo);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }

        return StringUtils.EMPTY;
    }











}
