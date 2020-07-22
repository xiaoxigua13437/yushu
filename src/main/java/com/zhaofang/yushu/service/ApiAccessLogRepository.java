package com.zhaofang.yushu.service;

import com.zhaofang.yushu.dto.ApiAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiAccessLogRepository extends JpaRepository<ApiAccessLog,Integer> {
}
