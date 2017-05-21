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
    private List<SharesData> getDataList;

    myadapter(List<SharesData> getDataList) {
        this.getDataList = getDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //cardrow is the xml file of card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //getdata name = getDataList.get(position);
        //holder.textView.setText(name.getname("get your data here"));


    }

    @Override
    public int getItemCount() {
        return getDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);

        }
    }
}
