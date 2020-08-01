package com.rama.newhealth.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rama.newhealth.Adapters.HomeAd;
import com.rama.newhealth.Adapters.SliderAdapterExample;
import com.rama.newhealth.DialogLoading;
import com.rama.newhealth.Models.HomeMo;
import com.rama.newhealth.Models.SliderItem;
import com.rama.newhealth.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference reference, refslider;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter homeadapter;

    private List<HomeMo> mHomeList;
    SliderView sliderView;
    private SliderAdapterExample adapter;

    DialogLoading loading;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        loading = new DialogLoading();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("productss");
        refslider = database.getReference().child("sliderimages");

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        mHomeList = new ArrayList<>();

        homeadapter = new HomeAd(getContext(), mHomeList);


        recyclerView.setAdapter(homeadapter);


        sliderView = view.findViewById(R.id.imageSlider);


        adapter = new SliderAdapterExample(getContext());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setIndicatorEnabled(true);
        sliderView.setIndicatorVisibility(true);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();


        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data

        refslider.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for (int i = 0; i < 4; i++) {
            SliderItem sliderItem = new SliderItem();
            if (i == 0)
                sliderItem.setImageUrl("https://firebasestorage.googleapis.com/v0/b/newhealth-9d31d.appspot.com/o/slider%2F11.jpg?alt=media&token=6d262ee1-77da-4ddc-a562-d1bf91a52c9b");
            else if (i == 1)
                sliderItem.setImageUrl("https://firebasestorage.googleapis.com/v0/b/newhealth-9d31d.appspot.com/o/slider%2F12%20(1).jpg?alt=media&token=326f644e-09a7-4ffd-91a9-1a6dcd069ac5");
            else if (i == 2)
                sliderItem.setImageUrl("https://firebasestorage.googleapis.com/v0/b/newhealth-9d31d.appspot.com/o/slider%2F13.jpg?alt=media&token=1b3ec09d-99f9-44b9-b525-f97390a008f7");
            else if (i == 3)
                sliderItem.setImageUrl("https://firebasestorage.googleapis.com/v0/b/newhealth-9d31d.appspot.com/o/slider%2F14.jpg?alt=media&token=616f83db-4b15-4097-beda-695a48edef19");
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);

        readPost();

        return view;
    }

    private void readPost() {

        reference.keepSynced(true);

        loading.show(getChildFragmentManager(), "Loading");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (isAdded()) {
                    if (dataSnapshot.exists()) {
                        loading.dismiss();
                        mHomeList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            mHomeList.add(dataSnapshot1.getValue(HomeMo.class));
                        }
                        homeadapter = new HomeAd(getContext(), mHomeList);
                        recyclerView.setAdapter(homeadapter);

                    } else {
                        loading.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();

                loading.dismiss();
            }

        });
    }


}