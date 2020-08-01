package com.rama.newhealth.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rama.newhealth.CommonClass;
import com.rama.newhealth.Models.CustomerInfo;
import com.rama.newhealth.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {
    Context context;
    DatabaseReference reference;
    ImageButton imageButton;
    CircleImageView profileImage;
    FirebaseUser user;
    ProgressDialog progressDialog;

    Toolbar toolbar;

    TextView textViewName, textViewPhone, textViewRegion, textViewLocation;


    ArrayList<String> regionList1 = new ArrayList<>();
    ArrayList<String> regionList2 = new ArrayList<>();
    SpinnerDialog spinnerDialog, spinnerDialog2;


    private FirebaseAuth firebaseAuth;
    private StorageReference mStorageReference;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        regionList1.add("Lucknow");
        reference = FirebaseDatabase.getInstance().getReference();
        textViewName = view.findViewById(R.id.name_text);
        textViewPhone = view.findViewById(R.id.phone_text);
        textViewRegion = view.findViewById(R.id.text_view_region);
        textViewLocation = view.findViewById(R.id.text_view_region2);


        firebaseAuth = FirebaseAuth.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference();

        imageButton = view.findViewById(R.id.add_image_button);
        profileImage = view.findViewById(R.id.image_view_profile);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(getActivity(), ProfileFragment.this);
            }
        });


        textViewRegion = view.findViewById(R.id.text_view_region);
        textViewLocation = view.findViewById(R.id.text_view_region2);

        spinnerDialog = new SpinnerDialog(getActivity(), regionList1, "Select Region");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                textViewLocation.setText("");
                textViewRegion.setText(item);
                CommonClass.region = item;
                CustomerInfo customerInfo = new CustomerInfo(CommonClass.name, CommonClass.region, CommonClass.location, CommonClass.imageuri,CommonClass.phone);
                reference.child("customer").child(CommonClass.phone).setValue(customerInfo);

                //firestore.collection("Hello").add(customerInfo);

                if (item.equals(regionList1.get(0))) {
                    regionList2.clear();
                    regionList2.add("Alambagh, Alambagh");
                    regionList2.add("Alambagh, Anand Nagar");
                    regionList2.add("Alambagh, Ashiyana");
                    regionList2.add("Alambagh, Azad Nagar");
                    regionList2.add("Alambagh, Chander Nagar");
                    regionList2.add("Alambagh, GFS Krishnanagar");
                    regionList2.add("Alambagh, Jalvayu Vihar");
                    regionList2.add("Alambagh, Kanausi");
                    regionList2.add("Alambagh, LDA Ashiyana");
                    regionList2.add("Alambagh, Mavaiya");
                    regionList2.add("Alambagh, RDSC");
                    regionList2.add("Alambagh, Sunrise");
                    regionList2.add("Arjunganj,Ansal Society");
                    regionList2.add("Arjunganj,Arjunganj");
                    regionList2.add("Arjunganj, Omaxe");
                    regionList2.add("Arjunganj, Devi Kheda");

                } else if (item.equals(regionList1.get(1))) {
                    regionList2.clear();
                    regionList2.add("Delhaaa");
                    regionList2.add("Delhbbb");
                    regionList2.add("Delhccc");
                    regionList2.add("Delhddd");
                }


            }
        });


        spinnerDialog2 = new SpinnerDialog(getActivity(), regionList2, "Select Nearest Location");
        spinnerDialog2.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                textViewLocation.setText(item);
                CommonClass.location = item;
                CustomerInfo customerInfo = new CustomerInfo(CommonClass.name, CommonClass.region, CommonClass.location, CommonClass.imageuri,CommonClass.phone);
                reference.child("customer").child(CommonClass.phone).setValue(customerInfo);

                // firestore.collection("Hello").add(customerInfo);

            }
        });

        view.findViewById(R.id.ll_region).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinnerDialog.showSpinerDialog();
            }
        });
        view.findViewById(R.id.ll_region2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinnerDialog2.showSpinerDialog();
            }
        });

        progressDialog = new ProgressDialog(getActivity());

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                final Uri resultUri = result.getUri();

                CommonClass.imageuri = resultUri.toString();
                profileImage.setImageURI(resultUri);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                    byte[] datai = baos.toByteArray();
                    mStorageReference.child("customer").child(CommonClass.phone).putBytes(datai).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();


                            mStorageReference.child("customer").child(CommonClass.phone).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    CustomerInfo customerInfo = new CustomerInfo(CommonClass.name, CommonClass.region, CommonClass.location, uri.toString(),CommonClass.phone);
                                    reference.child("customer").child(CommonClass.phone).setValue(customerInfo);
//                                    firestore.collection("Hello").add(customerInfo);

//                                    Glide.with(ProfileActivity.this)
//                                            .load(uri.toString())
//                                            .into(profileImage);
                                    progressDialog.dismiss();

                                    // Toast.makeText(getActivity(), uri.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage((int) progress + "% Loaded...");
                            progressDialog.show();
                        }
                    })
                            .addOnCanceledListener(new OnCanceledListener() {
                                @Override
                                public void onCanceled() {
                                    Toast.makeText(getContext(), "Not Success", Toast.LENGTH_SHORT).show();
                                }
                            });

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        user = firebaseAuth.getCurrentUser();
        if (user != null) {
            textViewName.setText(CommonClass.name);
            textViewPhone.setText(CommonClass.phone);
            textViewRegion.setText(CommonClass.region);
            textViewLocation.setText(CommonClass.location);
            if (!CommonClass.imageuri.equals("image")) {
                Glide.with(this).load(CommonClass.imageuri).into(profileImage);
            }
        }
    }
}