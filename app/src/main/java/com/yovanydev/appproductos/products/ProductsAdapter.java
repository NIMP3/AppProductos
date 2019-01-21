package com.yovanydev.appproductos.products;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.yovanydev.appproductos.R;
import com.yovanydev.appproductos.products.domain.model.Producto;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.EventListener;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DataLoading {

    private List<Producto> productos;
    private ProductItemListener mItemListener;

    private final static int TYPE_PRODUCT = 1;
    private final static int TYPE_LOADING_MORE = 2;

    private boolean mLoading = false;
    private boolean mMoreData = false;

    public ProductsAdapter(List<Producto> productos, ProductItemListener mItemListener) {
        setList(productos);
        this.mItemListener = mItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;

        if (i == TYPE_LOADING_MORE) {
            view = inflater.inflate(R.layout.item_loading_footer, viewGroup, false);
            return new LoadingMoreHolder(view);
        }

        view = inflater.inflate(R.layout.cardview_product, viewGroup, false);
        return new ProductsHolder(view, mItemListener);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getDataItemCount() && getDataItemCount() > 0) return TYPE_PRODUCT;
        return TYPE_LOADING_MORE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (getItemViewType(i)) {
            case TYPE_PRODUCT:
                Producto producto = productos.get(i);

                ProductsHolder productsHolder = (ProductsHolder) viewHolder;
                productsHolder.tvPrice.setText(producto.getFormatedPrice());
                productsHolder.tvName.setText(producto.getmName());
                productsHolder.tvUnitsInStock.setText(producto.getFormatedUnitsInStock());

                Picasso.get()
                        .load(producto.getmImageUrl())
                        .into(productsHolder.ivFeaturedImage);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHolder((LoadingMoreHolder) viewHolder, i);
                break;
        }
    }

    private void bindLoadingViewHolder(LoadingMoreHolder viewHolder, int position) {
        viewHolder.progress.setVisibility((position > 0 && mLoading && mMoreData)
                ? View.VISIBLE : View.INVISIBLE);
    }

    public void replaceData(List<Producto> notes) {
        setList(notes);
        notifyDataSetChanged();
    }

    private void setList(List<Producto> notes) {
        productos = checkNotNull(notes);
    }

    public void addData(List<Producto> products) {
        products.addAll(products);
    }

    @Override
    public int getItemCount() {
        return getDataItemCount();
    }

    public Producto getItem(int position) {
        return productos.get(position);
    }

    public void dataStartedLoading() {
        if (mLoading) return;
        mLoading = true;
        notifyItemInserted(getLoadingMoreItemPosition());
    }
    public void dataFinishedLoading() {
        if (!mLoading) return;
        mLoading = false;
        notifyItemRemoved(getLoadingMoreItemPosition());
    }
    public void setMoreData(boolean more) {
        mMoreData = more;
    }

    private int getLoadingMoreItemPosition() {
        return mLoading ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    public int getDataItemCount() {
        return productos.size();
    }

    @Override
    public boolean isLoadinData() {
        return mLoading;
    }

    @Override
    public boolean isThereMoreData() {
        return mMoreData;
    }

    public class ProductsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tvName;
        public TextView tvPrice;
        public ImageView ivFeaturedImage;
        public TextView tvUnitsInStock;

        private ProductItemListener mItemListener;

        public ProductsHolder(@NonNull View itemView, ProductItemListener listener) {
            super(itemView);

            mItemListener = listener;

            tvName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
            tvUnitsInStock = itemView.findViewById(R.id.tvUnitsInStock);
            ivFeaturedImage = itemView.findViewById(R.id.ivFeaturedImage);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Producto producto = getItem(position);
            mItemListener.onProductClick(producto);
        }
    }

    private class LoadingMoreHolder extends RecyclerView.ViewHolder {
        public ProgressBar progress;
        public LoadingMoreHolder(View view) {
            super(view);
            progress = view.findViewById(R.id.progressBar);
        }
    }

    public interface ProductItemListener {
        void onProductClick(Producto clickedProduct);
    }
}
