package com.rainersoft.megaabio.features.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rainersoft.megaabio.R;
import com.rainersoft.megaabio.data.model.response.OrderItemDetail;
import com.rainersoft.megaabio.data.model.response.Product;
import com.rainersoft.megaabio.data.model.response.ResponseData;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class SingleOrderProductsAdapter extends RecyclerView.Adapter<SingleOrderProductsAdapter.OrderViewHolder> {

    private List<OrderItemDetail> orders;
    private Context context;

    @Inject
    SingleOrderProductsAdapter() {
        orders = Collections.emptyList();
    }

    public void setOrders(List<OrderItemDetail> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_single_order, parent, false);

        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        OrderItemDetail order = this.orders.get(position);
        holder.onBind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tvProductCompany)
        TextView tvProductCompany;

        @BindView(R.id.productName)
        TextView productName;

        @BindView(R.id.tvProductType)
        TextView tvProductType;

        @BindView(R.id.tvProductCase)
        TextView tvProductCase;

        @BindView(R.id.tvProductAmount)
        TextView tvProductAmount;

        @BindView(R.id.tvTotal)
        TextView tvTotal;

        private OrderItemDetail order;

        OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void onBind(OrderItemDetail order) {
            this.order = order;

            productName.setText(String.format("# %s", order.getProductName()));
            tvProductCompany.setText(String.format("%s", order.getCompanyName()));
            tvProductType.setText(String.format("Type : %s", order.getTypeName()));
            tvProductCase.setText(String.format("Case : %s", order.getProdCase()));
            int amount = Integer.parseInt( order.getItemAmount() );
            int quantity = Integer.parseInt( order.getQuantity() );
            tvProductAmount.setText(String.format("Rs. %s/- x %s", amount, quantity));
            tvTotal.setText( String.format("Rs. %s/-", amount * quantity) );
        }
    }

}
