package com.zhaofang.yushu.service.impl;

import com.zhaofang.yushu.dto.ApiAccessLog;
import java.util.List;

public interface ApiAccessLogService {

    /**
     * 批量保存
     * @param lst
     * @return
     */
    public List<ApiAccessLog> bathSave(List<ApiAccessLog> lst);
}
