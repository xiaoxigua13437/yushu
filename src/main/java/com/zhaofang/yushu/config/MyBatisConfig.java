package com.zhaofang.yushu.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.zhaofang.yushu.mbg.mapper")
public class MyBatisConfig {
}
