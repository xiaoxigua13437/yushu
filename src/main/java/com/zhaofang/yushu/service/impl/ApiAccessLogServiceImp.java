package com.zhaofang.yushu.service.impl;


import com.zhaofang.yushu.dto.ApiAccessLog;
import com.zhaofang.yushu.service.ApiAccessLogRepository;
import com.zhaofang.yushu.service.ApiAccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApiAccessLogServiceImp implements ApiAccessLogService {


    @Autowired
    private ApiAccessLogRepository apiAccessLogRepository;

    @Override
    public ApiAccessLog save(ApiAccessLog info) {
        return apiAccessLogRepository.save(info);
    }


    @Override
    public List<ApiAccessLog> bathSave(List<ApiAccessLog> lst) {

        return apiAccessLogRepository.saveAll(lst);
    }
}
