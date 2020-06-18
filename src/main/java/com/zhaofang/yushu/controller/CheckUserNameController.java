package com.zhaofang.yushu.controller;

import com.zhaofang.yushu.common.RedisCommonUtil;
import com.zhaofang.yushu.entity.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Random;

@Api(tags = "CheckUserNameController",description = "校验用户手机号")
@RestController
@RequestMapping("/checkUserPhone")
public class CheckUserNameController {

    private static final String REDIS_KEY_PREFIX_AUTH_CODE ="";

    @Autowired
    private RedisCommonUtil redisCommonUtil;



    @ApiOperation("获取验证码")
    @RequestMapping("/getAuthCode")
    public CommonResult generateAuthCode(String code){

        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6;i++){
            sb.append(random.nextInt(10));
        }
        redisCommonUtil.setEx(REDIS_KEY_PREFIX_AUTH_CODE + code.toString(),REDIS_KEY_PREFIX_AUTH_CODE + code.toString(),1200L);
        return CommonResult.success(code.toString(),"获取验证码成功");

    }

    @ApiOperation("校验验证码")
    @RequestMapping("/verifyAuthCode")
    public CommonResult verifyAuthCode(String checkCode){

        if (StringUtils.isEmpty(checkCode)){
            return CommonResult.failed("请输入验证码");
        }

        Object realAuthCode = redisCommonUtil.get(REDIS_KEY_PREFIX_AUTH_CODE + checkCode);
        boolean result = checkCode.equals(realAuthCode);
        if (result) {
            return CommonResult.success(null, "验证码校验成功");
        } else {
            return CommonResult.failed("验证码不正确");
        }

    }











}
