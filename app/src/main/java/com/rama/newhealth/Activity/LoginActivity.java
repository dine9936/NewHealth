package com.rama.newhealth.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rama.newhealth.CommonClass;
import com.rama.newhealth.Models.CustomerInfo;
import com.rama.newhealth.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PhoneAuthActivity";
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    ArrayList<String> regionList1 = new ArrayList<>();
    ArrayList<String> regionList2 = new ArrayList<>();
    SpinnerDialog spinnerDialog, spinnerDialog2;
    // [END declare_auth]
    CountDownTimer yourCountDownTimer;
    ProgressBar progressBar, progressBar2;
    private RelativeLayout layoutPhone, layoutOtp, layoutLocation;
    private TextView textViewResendOtp, textViewTimer, textViewRegion, textViewRegion2;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private EditText editTextPhone, editTextOtp, editTextName;
    private TextView textViewPhoneNumber;
    private DatabaseReference reference;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        regionList1.add("Lucknow");


        findViewById(R.id.privacy_policy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PrivacyPolicy.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.terms_of_services).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, TermsCondition.class);
                startActivity(intent);
            }
        });

        progressBar = findViewById(R.id.progressbar);
        progressBar2 = findViewById(R.id.progressbar2);


        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();


        layoutPhone = findViewById(R.id.layout_phone);
        layoutOtp = findViewById(R.id.layout_otp);
        layoutLocation = findViewById(R.id.layout_location);
        editTextPhone = findViewById(R.id.editText_phone);
        editTextOtp = findViewById(R.id.editText_otp);
        editTextName = findViewById(R.id.edit_text_customer_name);


        findViewById(R.id.ll_region).setOnClickListener(this);
        findViewById(R.id.ll_region2).setOnClickListener(this);

        textViewRegion = findViewById(R.id.text_view_region);
        textViewRegion2 = findViewById(R.id.text_view_region2);

        spinnerDialog = new SpinnerDialog(LoginActivity.this, regionList1, "Select Region");

        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                textViewRegion2.setText("");
                textViewRegion.setText(item);
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


        spinnerDialog2 = new SpinnerDialog(LoginActivity.this, regionList2, "Select Nearest Location");
        spinnerDialog2.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                textViewRegion2.setText(item);
            }
        });

        textViewPhoneNumber = findViewById(R.id.text_phone_number);

        textViewResendOtp = findViewById(R.id.textView_resend_otp);
        textViewResendOtp.setOnClickListener(this);
        textViewTimer = findViewById(R.id.textView_timer);

        findViewById(R.id.change_number).setOnClickListener(this);

        findViewById(R.id.button_login).setOnClickListener(this);

        findViewById(R.id.button_otp).setOnClickListener(this);

        findViewById(R.id.button_location).setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                mVerificationInProgress = false;

//                layoutPhone.setVisibility(View.GONE);
//                layoutOtp.setVisibility(View.GONE);
//                layoutLocation.setVisibility(View.VISIBLE);

                if (credential != null) {
                    if (credential.getSmsCode() != null) {
                        yourCountDownTimer.cancel();
                        editTextOtp.setText(credential.getSmsCode());
                    } else {
                        editTextOtp.setText(R.string.instant_validation);
                    }
                }
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    editTextPhone.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {

                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }


                layoutPhone.setVisibility(View.VISIBLE);
                layoutOtp.setVisibility(View.GONE);
                layoutLocation.setVisibility(View.GONE);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                mVerificationId = verificationId;
                mResendToken = token;
                yourCountDownTimer = new CountDownTimer(60000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        textViewTimer.setVisibility(View.VISIBLE);
                        textViewResendOtp.setVisibility(View.GONE);
                        textViewTimer.setText("00:" + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        textViewTimer.setVisibility(View.GONE);
                        textViewResendOtp.setVisibility(View.VISIBLE);
                    }

                }.start();
                textViewPhoneNumber.setText(editTextPhone.getText().toString() + " ");
                layoutPhone.setVisibility(View.GONE);
                layoutOtp.setVisibility(View.VISIBLE);
                layoutLocation.setVisibility(View.GONE);

            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification("+91" + editTextPhone.getText().toString());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);

    }


    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }


    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            final String mobile = user.getPhoneNumber();

                            reference.child("customer").child(mobile).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        CustomerInfo customerInfo = snapshot.getValue(CustomerInfo.class);
                                        CommonClass.name = customerInfo.getUserName();
                                        CommonClass.location = customerInfo.getUserLocation();
                                        CommonClass.region = customerInfo.getUserRegion();
                                        CommonClass.imageuri = customerInfo.getUserImage();
                                        CommonClass.phone = mobile;
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        layoutLocation.setVisibility(View.VISIBLE);
                                        layoutOtp.setVisibility(View.GONE);
                                        layoutPhone.setVisibility(View.GONE);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });



                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                findViewById(R.id.button_otp).setEnabled(true);
                                progressBar2.setVisibility(View.GONE);
                                editTextOtp.setError("Invalid code.");

                            }


                        }
                    }
                });
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            reference.child("customer").child(user.getPhoneNumber()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        editTextPhone.setText(null);
                        editTextName.setText(null);
                        layoutPhone.setVisibility(View.GONE);
                        layoutOtp.setVisibility(View.GONE);
                        layoutLocation.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
//        } else {
//            layoutPhone.setVisibility(View.VISIBLE);
//            editTextPhone.setText(null);
//            layoutOtp.setVisibility(View.GONE);
//            layoutLocation.setVisibility(View.GONE);
//        }
    }


    private boolean validatePhoneNumber() {
        String phoneNumber = editTextPhone.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            editTextPhone.setError("Invalid phone number.");
            return false;
        }

        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:

                findViewById(R.id.button_login).setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                if (!validatePhoneNumber()) {
                    progressBar.setVisibility(View.GONE);
                    findViewById(R.id.button_login).setEnabled(true);
                    return;
                }
                startPhoneNumberVerification("+91" + editTextPhone.getText().toString().trim());

//                reference.child("customer").child(CommonClass.phone).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (!snapshot.exists()){
//                            layoutPhone.setVisibility(View.GONE);
//                            layoutOtp.setVisibility(View.GONE);
//                            layoutLocation.setVisibility(View.VISIBLE);
//                        }
//                        else {
//                            findViewById(R.id.button_login).setEnabled(false);
//                            progressBar.setVisibility(View.VISIBLE);
//                            if (!validatePhoneNumber()) {
//                                progressBar.setVisibility(View.GONE);
//                                findViewById(R.id.button_login).setEnabled(true);
//                                return;
//                            }
//                            sendCustomerInfo();
//                            startPhoneNumberVerification("+91" + editTextPhone.getText().toString().trim());
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


//                findViewById(R.id.button_login).setEnabled(false);
//                progressBar.setVisibility(View.VISIBLE);
//                if (!validatePhoneNumber()) {
//                    progressBar.setVisibility(View.GONE);
//                    findViewById(R.id.button_login).setEnabled(true);
//                    return;
//                }
//
//                startPhoneNumberVerification("+91" + editTextPhone.getText().toString().trim());
                break;
            case R.id.button_otp:
                findViewById(R.id.button_otp).setEnabled(false);
                progressBar2.setVisibility(View.VISIBLE);
                yourCountDownTimer.cancel();

                String code = editTextOtp.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    findViewById(R.id.button_otp).setEnabled(true);
                    progressBar2.setVisibility(View.GONE);
                    editTextOtp.setError("Cannot be empty.");
                    return;
                }

                verifyPhoneNumberWithCode(mVerificationId, code);
                break;
            case R.id.textView_resend_otp:
                resendVerificationCode("+91" + editTextPhone.getText().toString(), mResendToken);
                break;
            case R.id.change_number:
                findViewById(R.id.button_login).setEnabled(true);
                progressBar.setVisibility(View.GONE);
                findViewById(R.id.button_otp).setEnabled(true);
                progressBar2.setVisibility(View.GONE);
                yourCountDownTimer.cancel();
                layoutOtp.setVisibility(View.GONE);
                layoutPhone.setVisibility(View.VISIBLE);
                layoutLocation.setVisibility(View.GONE);
                editTextOtp.setText("");
                break;

            case R.id.button_location:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user !=null){
                    sendCustomerInfo();



                }






//                findViewById(R.id.button_location).setEnabled(false);
//                progressBar.setVisibility(View.VISIBLE);
//                if (!validatePhoneNumber()) {
//                    progressBar.setVisibility(View.GONE);
//                    findViewById(R.id.button_location).setEnabled(true);
//                    return;
//                }
//                sendCustomerInfo();
//                startPhoneNumberVerification("+91" + editTextPhone.getText().toString().trim());


                break;

            case R.id.ll_region:
                spinnerDialog.showSpinerDialog();
                break;
            case R.id.ll_region2:
                if (!regionList2.isEmpty()) {
                    spinnerDialog2.showSpinerDialog();
                } else {
                    Toast.makeText(this, "Select Region", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void sendCustomerInfo() {

        FirebaseUser usernew = FirebaseAuth.getInstance().getCurrentUser();

        String name = editTextName.getText().toString().trim();
        String region = textViewRegion.getText().toString().trim();
        String nearest = textViewRegion2.getText().toString().trim();
        if (name.equals("") || region.equals("") || nearest.equals("")) {
            Toast.makeText(this, "Fill All Fields", Toast.LENGTH_SHORT).show();
        } else {
            CommonClass.imageuri = "image";
            CommonClass.name = name;
            CommonClass.region = region;
            CommonClass.location = nearest;
            CommonClass.phone = usernew.getPhoneNumber();

            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setUserName(CommonClass.name);
            customerInfo.setUserRegion(CommonClass.region);
            customerInfo.setUserLocation(CommonClass.location);
            customerInfo.setUserImage(CommonClass.imageuri);
            customerInfo.setUserPhone(CommonClass.phone);

            reference.child("customer").child(CommonClass.phone).setValue(customerInfo);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }

    }


    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onRestart() {
        super.onRestart();

    }
}