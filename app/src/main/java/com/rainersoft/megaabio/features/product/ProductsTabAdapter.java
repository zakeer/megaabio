package com.rainersoft.megaabio.features.product;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rainersoft.megaabio.data.model.response.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsTabAdapter extends FragmentStatePagerAdapter {
    private final List<ProductsListFragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<List<Product>> productsList = new ArrayList<>();

    ProductsTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public ProductsListFragment getItem(int position) {
        return mFragmentList.get(position);
    }

    public void addFragment(ProductsListFragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        productsList.add(new ArrayList<>());
    }

    public List<Product> getProducts(int position) {
        return productsList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}