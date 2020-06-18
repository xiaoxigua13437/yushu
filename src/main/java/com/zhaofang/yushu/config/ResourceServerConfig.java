package com.zhaofang.yushu.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


@Configuration
@EnableResourceServer
public class ResourceServerConfig  extends ResourceServerConfigurerAdapter {


    /**
     * /api/* 端点须要被认证且要有USER权限;/user/**的端点须要被认证，但是最终的结果是WebSecurityConfigurerAdapter相关的配置信息没生效
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {

//        http.authorizeRequests()
//                .antMatchers("/login/**").permitAll()
//                .antMatchers("/logout").permitAll()
//                .antMatchers("/api/*").hasRole("USER")
//                .anyRequest().permitAll();

        http.antMatcher("/api/**")
                .authorizeRequests()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }
}
