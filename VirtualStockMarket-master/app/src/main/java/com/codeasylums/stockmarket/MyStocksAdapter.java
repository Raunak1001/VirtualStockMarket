package com.codeasylums.stockmarket;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vic on 5/21/2017.
 */
public class MyStocksAdapter extends RecyclerView.Adapter<MyStocksAdapter.ViewHolder> {

    //getdata is class to get the data
    private List<ShareTransactionObject> ShareTransactionObjectList;
    Context context;
    MyStocksAdapter(List<ShareTransactionObject> ShareTransactionObjectList, Context context) {
        this.ShareTransactionObjectList = ShareTransactionObjectList;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //cardrow is the xml file of card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mystocks_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.companyNameTextView.setText(ShareTransactionObjectList.get(position).getCompanyName());
        holder.noOfStocks.setText(ShareTransactionObjectList.get(position).getAmountShares());
        holder.Price.setText(ShareTransactionObjectList.get(position).getShareRate());
    }


    @Override
    public int getItemCount() {
        return ShareTransactionObjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView companyNameTextView,noOfStocks,Price;
        SQLiteHandler sqLiteHandler;
        public ViewHolder(View itemView) {
            super(itemView);
            companyNameTextView = (TextView) itemView.findViewById(R.id.companyNameTextView);
            noOfStocks = (TextView) itemView.findViewById(R.id.noofstocks);
            Price = (TextView) itemView.findViewById(R.id.price);
            sqLiteHandler=new SQLiteHandler(context);
        }
    }


}
