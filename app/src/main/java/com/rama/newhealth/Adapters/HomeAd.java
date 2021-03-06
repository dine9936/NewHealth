package com.rama.newhealth.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rama.newhealth.Activity.ItemDetailsActicity;
import com.rama.newhealth.Models.HomeMo;
import com.rama.newhealth.R;

import java.util.List;

public class HomeAd extends RecyclerView.Adapter<HomeAd.ViewHolder> {
    public Context mContext;
    public List<HomeMo> mHomeList;



    private ProgressDialog progressDialog;


    public HomeAd(Context mContext, List<HomeMo> mHomeList) {
        this.mContext = mContext;
        this.mHomeList = mHomeList;
    }


    @NonNull
    @Override
    public HomeAd.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_card, parent, false);


        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final HomeAd.ViewHolder holder, int position) {

        final HomeMo homemodel = mHomeList.get(position);


        holder.itemname.setText(homemodel.getItemName());

        holder.itemprice.setText("₹" + homemodel.getItemPrice());



            Glide.with(holder.itemimage.getContext())
                    .load(homemodel.getItemImage())
                    .into(holder.itemimage);




        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ItemDetailsActicity.class);
                intent.putExtra("itemid", homemodel.getItemId());
                intent.putExtra("itemname", homemodel.getItemName());
                intent.putExtra("itemprice", homemodel.getItemPrice());
                intent.putExtra("itemimage", homemodel.getItemImage());
                intent.putExtra("itemquantity",homemodel.getItemQuantity());
                intent.putExtra("itemdiscount",homemodel.getItemDiscount());
                intent.putExtra("itemdetail",homemodel.getItemDetail());
                intent.putExtra("itemsubcription",homemodel.getItemSubscriptionprice());
                intent.putExtra("itembenefits",homemodel.getItembenefit());
                intent.putExtra("itemprevent",homemodel.getItemprevent());
                intent.putExtra("itemonetime",homemodel.getItemOneTime());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mHomeList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemname, itemprice;
        public ImageView itemimage;
        public LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.itemname);
            itemprice = itemView.findViewById(R.id.itemprice);

            itemimage = itemView.findViewById(R.id.itemimage);

            linearLayout = itemView.findViewById(R.id.ll_items);

        }
    }
}

