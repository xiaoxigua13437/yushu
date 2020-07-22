package com.zhaofang.yushu.service;

import com.zhaofang.yushu.dto.ApiAccessLog;
import java.util.List;

public interface ApiAccessLogService {


    /**
     * 单条保存
     * @param info
     * @return
     */
    public ApiAccessLog save(ApiAccessLog info);


    /**
     * 批量保存
     * @param lst
     * @return
     */
    public List<ApiAccessLog> bathSave(List<ApiAccessLog> lst);
}
