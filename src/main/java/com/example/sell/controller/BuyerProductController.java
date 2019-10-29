package com.example.sell.controller;


import com.example.sell.VO.ProductInfoVO;
import com.example.sell.VO.ProductVO;
import com.example.sell.VO.ResultVO;
import com.example.sell.dataobject.ProductCategory;
import com.example.sell.dataobject.ProductInfo;
import com.example.sell.service.CategoryService;
import com.example.sell.service.ProductService;
import com.example.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @Cacheable(cacheNames = "product",key = "#sellerId",condition = "#sellerId.length()>3",unless = "#result.getCode() !=0")
    public ResultVO list(@RequestParam("sellerId") String sellerId) {
        /*查询所有的上架商品*/
        List<ProductInfo> productInfoList = productService.findUpAll();
        /*查询类目*/
        List<Integer> list = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(list);

        ArrayList<ProductVO> ProductVOList = new ArrayList<>();
        /*数据拼装*/
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            ArrayList<ProductInfoVO> arrayList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType() .equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    arrayList.add(productInfoVO);
                }
            }
            productVO.setListFoods(arrayList);
            ProductVOList.add(productVO);
        }
        return ResultVOUtil.success(ProductVOList);
    }
}
