package com.codeasylums.stockmarket;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vic on 5/21/2017.
 */
public class myadapter extends RecyclerView.Adapter<myadapter.ViewHolder> {

  //getdata is class to get the data
  private List<SharesData> sharesDataList;

  myadapter(List<SharesData> sharesDataList) {
    this.sharesDataList = sharesDataList;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    //cardrow is the xml file of card view
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrow, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.companyNameTextView.setText(sharesDataList.get(position).getShareName());
  holder.sharerateTextView.setText(sharesDataList.get(position).getShareRate());
  }

  @Override
  public int getItemCount() {
    return sharesDataList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView companyNameTextView, sharerateTextView;

    public ViewHolder(View itemView) {
      super(itemView);
      companyNameTextView = (TextView) itemView.findViewById(R.id.companyNameTextView);
      sharerateTextView = (TextView) itemView.findViewById(R.id.shreRaTextView);
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
