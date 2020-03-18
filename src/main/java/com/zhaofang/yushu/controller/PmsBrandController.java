package com.zhaofang.yushu.controller;


import com.zhaofang.yushu.entity.CommonPage;
import com.zhaofang.yushu.entity.CommonResult;
import com.zhaofang.yushu.mbg.model.PmsBrand;
import com.zhaofang.yushu.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 品牌管理Controller
 */
@Api(tags = "PmsBrandController", description = "商品品牌管理")
@RestController
@RequestMapping("/brand")
public class PmsBrandController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsBrandController.class);

    @Autowired
    private PmsBrandService demoService;

    /**
     * 获取返回的 BrandData
     *
     */
    @ApiOperation("获取所有品牌列表")
    @RequestMapping(value = "/listBrandAll",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsBrand>> getBrandList(){
        return CommonResult.success(demoService.listAllBrand());
    }

    /**
     * 新增 Brand
     * @param pmsBrand
     * @return
     */
    @ApiOperation("创建品牌")
    @RequestMapping(value = "/createBrand",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createBrand(@RequestParam PmsBrand pmsBrand){
        CommonResult commonResult;
        int count = demoService.CreateBrand(pmsBrand);
        if (count == 1){
            commonResult = CommonResult.success(pmsBrand);
            LOGGER.info("createBrand success: {}" + pmsBrand);
        }else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.info("createBrand failed: {}" + pmsBrand);
        }
        return commonResult;

    }

    /**
     * 修改 Brand
     * @param id
     * @param pmsBrandDto
     * @return
     */
    @ApiOperation("更新指定id品牌信息")
    @RequestMapping(value = "updateBrand/{id}",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateBrand(@PathVariable("id") Long id, @RequestParam PmsBrand pmsBrandDto){

        CommonResult commonResult;
        int count = demoService.updateBrand(id, pmsBrandDto);
        if (count == 1){
            commonResult = CommonResult.success(pmsBrandDto);
            LOGGER.info("updateBrand success: {}" + pmsBrandDto);

        }else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("updateBrand failed :{}" +pmsBrandDto );
        }

        return commonResult;
    }

    /**
     * 删除 Brand
     * @param id
     * @return
     */
    @ApiOperation("删除指定id的品牌")
    @RequestMapping(value = "/deleteBrand/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteBrand(@PathVariable("id") Long id){
        CommonResult commonResult;
        int count = demoService.deleteBrand(id);
        if (count == 1){
            commonResult = CommonResult.success("操作成功!");
            LOGGER.debug("deleteBrand success : id{}" + id);
        }else {
            commonResult = CommonResult.failed("操作失败");
            LOGGER.debug("deleteBrand failed : id{}" + id);
        }
        return commonResult;

    }

    /**
     * 分页查询 BrandData
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("分页查询品牌列表")
    @RequestMapping(value = "/listPmsBrand", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> listBrand(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {

            List<PmsBrand> brandList = demoService.listPmsBrand(pageNum,pageSize);
            return CommonResult.success(CommonPage.restPage(brandList));

    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @ApiOperation("获取指定id的品牌详情")
    @RequestMapping(value = "/getBrand/{id}" ,method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<PmsBrand> getBrand(@PathVariable("id") Long id){
        return CommonResult.success(demoService.getBrand(id));

    }











}
