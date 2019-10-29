package com.example.sell.service.impl;

import com.example.sell.dataobject.ProductInfo;
import com.example.sell.dto.CartDto;
import com.example.sell.enums.ProductStatusEnum;
import com.example.sell.enums.RestEnum;
import com.example.sell.exception.SellException;
import com.example.sell.repository.ProductInfoRepository;
import com.example.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames = "productName")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository repository;

    @Override
    @Cacheable(key = "123")
    public ProductInfo findOne(String productId) {
        return repository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Override
    @CachePut(cacheNames = "productName",key = "123")
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            ProductInfo productInfo = repository.findById(cartDto.getProductId()).orElse(null);
            if (productInfo == null){
                throw new SellException(RestEnum.PRODUCT_NO_EXIST);
            }
            int result = productInfo.getProductStock() + cartDto.getProductQuantity();
            if (result < 0){
                throw new SellException(RestEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            ProductInfo productInfo = repository.findById(cartDto.getProductId()).orElse(null);
            if (productInfo == null){
                throw new SellException(RestEnum.PRODUCT_NO_EXIST);
            }
            int result = productInfo.getProductStock() - cartDto.getProductQuantity();
            if (result < 0){
                throw new SellException(RestEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).orElse(null);
        if (productInfo == null){
            throw new SellException(RestEnum.PRODUCT_NO_EXIST);
        }
        if (productInfo.getProductStatus() == ProductStatusEnum.UP){
            throw new SellException(RestEnum.PRODUCT_STATUS_ERROR);
        }
        /*修改状态*/
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).orElse(null);
        if (productInfo == null){
            throw new SellException(RestEnum.PRODUCT_NO_EXIST);
        }
        if (productInfo.getProductStatus().getCode() == ProductStatusEnum.DOWN.getCode()){
            throw new SellException(RestEnum.PRODUCT_STATUS_ERROR);
        }
        /*修改状态*/
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }
}
