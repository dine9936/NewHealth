package com.rama.newhealth.Activity;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rama.newhealth.R;

public class ItemDetailsActicity extends AppCompatActivity {

    String itemImage;
    private TextView textViewItemName, textViewOneTimeCut, textViewItemPrice, textViewItemName2, textViewQuantity, textViewSub, textViewDiscount, textViewDetails, textViewPrevent, textViewBenefits;
    private ImageView imageViewItem;
    private Toolbar toolbar;
    private LinearLayout buttonWhatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_acticity);

        toolbar = findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final String itemName = getIntent().getStringExtra("itemname");
        String itemId = getIntent().getStringExtra("itemid");
        String itemPrice = getIntent().getStringExtra("itemprice");
        String itemsubcription = getIntent().getStringExtra("itemsubcription");
        String itemdetail = getIntent().getStringExtra("itemdetail");
        String itemquantity = getIntent().getStringExtra("itemquantity");
        String itembenefits = getIntent().getStringExtra("itembenefits");
        String itemprevent = getIntent().getStringExtra("itemprevent");
        String itemonetime = getIntent().getStringExtra("itemonetime");
        itemImage = getIntent().getStringExtra("itemimage");


       textViewDetails = findViewById(R.id.item_details);
        textViewDetails.setText(itemdetail);
        textViewItemName = findViewById(R.id.text_item_name);

        textViewOneTimeCut = findViewById(R.id.cross_one_time);
        textViewOneTimeCut.setText("₹"+itemonetime);

        textViewItemName2 = findViewById(R.id.item_name);
        textViewItemPrice = findViewById(R.id.item_price);
        imageViewItem = findViewById(R.id.item_image);
        textViewDetails = findViewById(R.id.item_details);
        textViewOneTimeCut.setPaintFlags(textViewOneTimeCut.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        textViewQuantity = findViewById(R.id.item_quantity);
        textViewQuantity.setText("Quantity "+itemquantity + " ML");
        textViewSub = findViewById(R.id.item_advance_subcription);
        textViewSub.setText("₹" + itemsubcription);

        textViewDiscount= findViewById(R.id.item_discount);
        textViewDiscount.setText("One Time Price ₹"+itemonetime);
        textViewBenefits = findViewById(R.id.item_benefits);
        textViewBenefits.setText(itembenefits);
        textViewPrevent = findViewById(R.id.item_prevent);
        textViewPrevent.setText(itemprevent);


        findViewById(R.id.item_detail_subscription).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemDetailsActicity.this, SubscrActivity.class);
                startActivity(intent);
            }
        });

        textViewItemName2.setText(itemName);
        textViewItemPrice.setText("MRP ₹" + itemPrice);

        textViewItemName.setText(itemName);


        Glide.with(this).load(itemImage).into(imageViewItem);

        buttonWhatsapp = findViewById(R.id.button_whatsapp);
        buttonWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = "+91  8874554433"; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = getBaseContext().getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));

                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getBaseContext(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
           Intent intent = new Intent(ItemDetailsActicity.this,LoginActivity.class);
           startActivity(intent);
           finish();
        }

    }
}