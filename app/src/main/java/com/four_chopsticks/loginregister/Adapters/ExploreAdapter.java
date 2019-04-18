package com.four_chopsticks.loginregister.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.four_chopsticks.loginregister.ExploreSingleDataModel;
import com.four_chopsticks.loginregister.R;

import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.MyViewHolder>{
    private Context context;
    private List<ExploreSingleDataModel> exploreSingleDataModelsList;


    public ExploreAdapter(Context context ,
                          List<ExploreSingleDataModel> exploreSingleDataModelsList){

        this.context = context;
        this.exploreSingleDataModelsList =exploreSingleDataModelsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.explore_custom_item,parent ,false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    ExploreSingleDataModel esdm= exploreSingleDataModelsList.get(position);

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView networkImage;
        TextView networkTitle;
        TextView networkDescription;
        TextView networkCreateTime;

        public MyViewHolder(View itemView) {
            super(itemView);

            networkImage = itemView.findViewById(R.id.explore_card_image);
            networkTitle = itemView.findViewById(R.id.explore_card_title);
            networkDescription = itemView.findViewById(R.id.explore_card_desc);
            networkCreateTime = itemView.findViewById(R.id.explore_card_created);

        }
    }
}
