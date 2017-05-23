package com.codeasylums.stockmarket;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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
public class myadapter extends RecyclerView.Adapter<myadapter.ViewHolder> {

  //getdata is class to get the data
  private List<SharesData> sharesDataList;
Context context;
  myadapter(List<SharesData> sharesDataList,Context context) {
    this.sharesDataList = sharesDataList;
    this.context=context;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    //cardrow is the xml file of card view
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrow, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    holder.companyNameTextView.setText(sharesDataList.get(position).getShareName());
  holder.sharerateTextView.setText(sharesDataList.get(position).getShareRate());
    final View dialogView = LayoutInflater.from(context).inflate(R.layout.buy_share_alert_box, null);
    holder.buyButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {

        holder.alertDialog.setTitle(sharesDataList.get(position).getShareName());
        holder.alertDialog.setCancelable(false);
        holder.alertDialog.setMessage("Please Enter The Number Of Shares");


        final EditText shareNumberEditTask = (EditText) dialogView.findViewById(R.id.buyNumberShareEditBox);

        holder.alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("ST",sharesDataList.get(position).getShareName());
                String numShares=shareNumberEditTask.getText().toString();
                Log.d("SHAREs",numShares);
                ShareTransactionObject shareTransactionObject= new ShareTransactionObject();
                shareTransactionObject.setSoldOrBought(true);
                shareTransactionObject.setShareRate(sharesDataList.get(position).getShareRate());
            shareTransactionObject.setCompanyName(sharesDataList.get(position).getShareName());
                shareTransactionObject.setAmountShares(numShares);

            holder.sqLiteHandler.addTransactions(shareTransactionObject);
            holder.sqLiteHandler.addShareDataToSqlite(shareTransactionObject);
              }
            });

            holder.alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {

                  }
                });

        holder.alertDialog.setView(dialogView);
        holder.alertDialog.show();
      }
    });

  }

  @Override
  public int getItemCount() {
    return sharesDataList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView companyNameTextView, sharerateTextView;
Button buyButton;
    SQLiteHandler sqLiteHandler;
    AlertDialog alertDialog ;
    CardView cardView ;
    public ViewHolder(View itemView) {
      super(itemView);
      companyNameTextView = (TextView) itemView.findViewById(R.id.companyNameTextView);
      sharerateTextView = (TextView) itemView.findViewById(R.id.shreRaTextView);
    buyButton= (Button) itemView.findViewById(R.id.buyButton);
      cardView= (CardView) itemView.findViewById(R.id.card);
      alertDialog =new AlertDialog.Builder(context).create();
      sqLiteHandler=new SQLiteHandler(context);
    }
  }

  public  void update(List<SharesData> sharesDataList)
  {

    this.sharesDataList.get(0).setShareRate(sharesDataList.get(0).getShareRate());
    this.sharesDataList.get(1).setShareRate(sharesDataList.get(1).getShareRate());
    this.sharesDataList.get(2).setShareRate(sharesDataList.get(2).getShareRate());
    this.sharesDataList.get(3).setShareRate(sharesDataList.get(3).getShareRate());

    notifyDataSetChanged();
  }
}
