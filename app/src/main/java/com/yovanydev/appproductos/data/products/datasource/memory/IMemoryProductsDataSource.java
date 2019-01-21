package com.yovanydev.appproductos.data.products.datasource.memory;

import com.yovanydev.appproductos.products.domain.criteria.ProductCriteria;
import com.yovanydev.appproductos.products.domain.model.Producto;

import java.util.List;

public interface IMemoryProductsDataSource {
    List<Producto> find(ProductCriteria criteria);
    void save(Producto producto);
    void deleteAll();
    boolean mapIsNull();
}
