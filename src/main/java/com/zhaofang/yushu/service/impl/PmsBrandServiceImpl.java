package com.zhaofang.yushu.service.impl;

import com.github.pagehelper.PageHelper;
import com.zhaofang.yushu.mbg.mapper.PmsBrandMapper;
import com.zhaofang.yushu.mbg.model.PmsBrand;
import com.zhaofang.yushu.mbg.model.PmsBrandExample;
import com.zhaofang.yushu.service.PmsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    @Autowired
    private PmsBrandMapper brandMapper;

    @Override
    public List<PmsBrand> listAllBrand() {
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public int CreateBrand(PmsBrand brand) {
        return brandMapper.insertSelective(brand);
    }

    @Override
    public int updateBrand(Long id, PmsBrand brand) {
        brand.setId(id);
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public int deleteBrand(Long id) {

        return brandMapper.deleteByPrimaryKey(id);

    }

    @Override
    public List<PmsBrand> listPmsBrand(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return brandMapper.selectByExample(new PmsBrandExample());    }

    @Override
    public PmsBrand getBrand(Long id) {
        return brandMapper.selectByPrimaryKey(id);    }
}
