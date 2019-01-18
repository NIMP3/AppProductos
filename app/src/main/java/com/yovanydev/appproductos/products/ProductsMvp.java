package com.yovanydev.appproductos.products;

import com.yovanydev.appproductos.products.domain.model.Producto;

import java.util.List;

public interface ProductsMvp {
    interface View {
        /**
         * Presenta el listado de productos
         * @param productos : Listado de productos a presentar
         */
        void showProducts(List<Producto> productos);

        /**
         * Mostrar el estado de carga
         * @param show  : Bandera que determina si se muestra o no el estado de carga
         */
        void showLoadingState(boolean show);

        /**
         * Mostrar el Estado vacio en caso de no encontrar productos
         */
        void showEmptyState();

        /**
         * Mostrar mensaje de error
         * @param msg : Mensaje de error
         */
        void showProductsError(String msg);

        /**
         * Monstrar nuevos productos agregados a la pagina
         * @param productos : Nuevos productos que seran agregados a la pagina
         */
        void showProductsPage(List<Producto> productos);

        /**
         * Mostrar un indicador circular inferior para nuevos productos
         * @param show : Bandera que determina si se muestra o no el indicador circular
         */
        void showLoadMoreIndicator(boolean show);

        /**
         * Controlar el indicador circular
         * @param show : Bandera que controla el indicador circular
         */
        void allowMoreData(boolean show);
    }

    interface Presenter {
        /**
         * Cargar productos del repositorio
         * @param reload : Bandera que determina si la carga es inicial o no
         */
        void loadProducts(boolean reload);
    }
}
