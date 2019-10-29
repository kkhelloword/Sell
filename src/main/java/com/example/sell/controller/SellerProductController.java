package com.example.sell.controller;

import com.example.sell.dataobject.OrderMaster;
import com.example.sell.dataobject.ProductCategory;
import com.example.sell.dataobject.ProductInfo;
import com.example.sell.enums.RestEnum;
import com.example.sell.service.CategoryService;
import com.example.sell.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 卖家断商品
 * @Author: baiyj
 * @Date: 2019/10/22
 */


@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 查询商品列表
     *
     * @param [page,size, model]
     * @return java.lang.String
     */
    @GetMapping("/list")
    public String findList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                           Model model) {
        Page<ProductInfo> productInfos = productService.findAll(PageRequest.of(page - 1, size));
        model.addAttribute("productPage", productInfos);
        model.addAttribute("currentPage", page);
        model.addAttribute("size",size);
        return "/product/list";
    }

    /**
    * 商品上下架
    *@param  [productId, model]
    *@return java.lang.String
    */
    @GetMapping("/on_sale")
    public String onSale(@RequestParam(value = "productId") String productId,
                         Model model){

        try {
            productService.onSale(productId);
        } catch (Exception e) {
            model.addAttribute("msg",e);
            model.addAttribute("url","/sell/seller/product/list");
            return "/common/error";
        }
        model.addAttribute("msg", RestEnum.ONSALE_SUCCESS.getMessage());
        model.addAttribute("url","/sell/seller/product/list");
        return "/common/success";
    }

    @GetMapping("/off_sale")
    public String offSale(@RequestParam(value = "productId") String productId,
                         Model model){

        try {
            productService.offSale(productId);
        } catch (Exception e) {
            model.addAttribute("msg",e);
            model.addAttribute("url","/sell/seller/product/list");
            return "/common/error";
        }
        model.addAttribute("msg", RestEnum.OFFSELE_SUCCESS .getMessage());
        model.addAttribute("url","/sell/seller/product/list");
        return "/common/success";
    }

    /**
    *添加商品
    *@param  [productId, model]
    *@return java.lang.String
    */
    @GetMapping("/index")
    public String addProduct(@RequestParam(value = "productId",required = false) String productId,
                             Model model){

        if (!StringUtils.isEmpty(productId)){
            /*查询出商品对象*/
            ProductInfo productInfo = productService.findOne(productId);
            model.addAttribute("productInfo",productInfo);
        }
        /*查询出所有的类目*/
        List<ProductCategory> categoryList = categoryService.findAll();
        model.addAttribute("categoryList",categoryList);
        return "product/index";
    }
}
