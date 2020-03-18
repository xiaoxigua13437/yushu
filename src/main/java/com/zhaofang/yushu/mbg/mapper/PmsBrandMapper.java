package com.zhaofang.yushu.mbg.mapper;


import com.zhaofang.yushu.mbg.model.PmsBrand;
import com.zhaofang.yushu.mbg.model.PmsBrandExample;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface PmsBrandMapper  {

    List<PmsBrand> selectByExample(PmsBrandExample example);

    int insertSelective(PmsBrand brand);

    int updateByPrimaryKeySelective(PmsBrand record);


    int deleteByPrimaryKey(Long id);

    PmsBrand selectByPrimaryKey(Long id);
}
