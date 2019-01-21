package com.yovanydev.appproductos.products.domain.criteria;

import com.yovanydev.appproductos.products.domain.model.Producto;

import java.util.List;

/**
 * Comprueba si los elementos de una lista cumplen con ciertos criterios
 */
public interface ProductCriteria {
    List<Producto> match(List<Producto> productos);
}
