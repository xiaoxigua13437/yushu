package com.zhaofang.yushu.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SystemConstantTest {

    public static String surroundings;

    @Value("${spring.profiles.active}")
    public String environment;

    @PostConstruct
    public void initialize() {
        System.out.println("初始化环境...");
        surroundings = this.environment;

        System.out.println(surroundings);
    }


}
