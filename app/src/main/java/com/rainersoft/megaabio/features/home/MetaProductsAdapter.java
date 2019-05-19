package com.rainersoft.megaabio.features.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rainersoft.megaabio.BuildConfig;
import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.response.MetaResponseProduct;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class MetaProductsAdapter extends RecyclerView.Adapter<MetaProductsAdapter.MetaProductViewHolder> {

    private List<MetaResponseProduct> productsList;
    private Subject<MetaResponseProduct> productClickSubject;
    private Context context;

    @Inject
    MetaProductsAdapter() {
        productClickSubject = PublishSubject.create();
        productsList = Collections.emptyList();
    }

    public void setProductsList(List<MetaResponseProduct> productsList) {
        this.productsList = productsList;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public MetaProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_meta_product, parent, false);
        return new MetaProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MetaProductViewHolder holder, int position) {
        MetaResponseProduct product = this.productsList.get(position);
        holder.onBind(product);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    Observable<MetaResponseProduct> getProductClick() {
        return productClickSubject;
    }

    class MetaProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productName)
        TextView productName;

        @BindView(R.id.productImage)
        ImageView productImage;

        private MetaResponseProduct product;

        MetaProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> productClickSubject.onNext(product));
        }

        void onBind(MetaResponseProduct product) {
            this.product = product;
            productName.setText(
                    String.format(
                            "%s", product.getProductName()));

            String imageUrl = String.format("%s%s", BuildConfig.IMAGE_BASE_URL, product.getImage());
            Glide.with(context).load(imageUrl).into(productImage);
        }
    }
}
