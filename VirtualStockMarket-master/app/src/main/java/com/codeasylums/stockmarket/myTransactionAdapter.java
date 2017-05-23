package com.codeasylums.stockmarket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vic on 5/21/2017.
 */
public class myTransactionAdapter extends RecyclerView.Adapter<myTransactionAdapter.ViewHolder> {

    //getdata is class to get the data
    private List<ShareTransactionObject> TransactionList;
    Context context;
    myTransactionAdapter(List<ShareTransactionObject> TransactionList,Context context) {
        this.TransactionList = TransactionList;
        this.context=context;
       // Log.d("ask",TransactionList.get(0).getCompanyName());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //cardrow is the xml file of card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactionadapterlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.companyNameTextView.setText(TransactionList.get(position).getCompanyName());
        holder.sharerateTextView.setText(TransactionList.get(position).getShareRate());
        holder.amountShares.setText(TransactionList.get(position).getAmountShares());
        Log.d("sd",TransactionList.get(0).getShareRate());
    }

    @Override
    public int getItemCount() {
        return TransactionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView companyNameTextView, sharerateTextView,amountShares,boughtORsold;
        SQLiteHandler sqLiteHandler;
        public ViewHolder(View itemView) {
            super(itemView);
            companyNameTextView = (TextView) itemView.findViewById(R.id.companynametransaction);
            sharerateTextView = (TextView) itemView.findViewById(R.id.shareratetransaction);
            amountShares= (TextView) itemView.findViewById(R.id.amountofsharestransaction);
           // boughtORsold = (TextView) itemView.findViewById(R.id.textView2);
            sqLiteHandler=new SQLiteHandler(context);
            sqLiteHandler.getTransactions();
           // sharerateTextView.setText(TransactionList.get(0).amountShares);

        }


    }


    public  void update(List<ShareTransactionObject> TransactionList)
    {

        this.TransactionList.get(0).setShareRate(TransactionList.get(0).getShareRate());
        this.TransactionList.get(1).setShareRate(TransactionList.get(1).getShareRate());
        this.TransactionList.get(2).setShareRate(TransactionList.get(2).getShareRate());
        this.TransactionList.get(3).setShareRate(TransactionList.get(3).getShareRate());

        notifyDataSetChanged();
    }
}
