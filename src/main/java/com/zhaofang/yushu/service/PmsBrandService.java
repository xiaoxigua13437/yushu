package com.zhaofang.yushu.service;

import com.zhaofang.yushu.mbg.model.PmsBrand;

import java.util.List;

public interface PmsBrandService {

    List<PmsBrand> listAllBrand();

    int CreateBrand(PmsBrand pmsBrand);

    int updateBrand(Long id,PmsBrand pmsBrand);

    int deleteBrand(Long id);

    List<PmsBrand> listPmsBrand(int pageNum,int pageSize);

    PmsBrand getBrand(Long id);


}
