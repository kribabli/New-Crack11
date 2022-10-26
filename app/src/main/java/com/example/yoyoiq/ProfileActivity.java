package com.example.yoyoiq;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.yoyoiq.KYC.ViewKycResponse;
import com.example.yoyoiq.Model.UpdateProfileResponse;
import com.example.yoyoiq.Retrofit.ApiClient;
import com.example.yoyoiq.common.DataArray;
import com.example.yoyoiq.common.HelperData;
import com.example.yoyoiq.common.LocalDataBase;
import com.example.yoyoiq.common.SessionManager;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.util.FileUriUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView backPress, skillScore, share;
    SessionManager sessionManager;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    CircleImageView userProfile;
    ImageView profileChange;
    String code = "", profileImagePath = " ";
    boolean status = false;
    LocalDataBase localDataBase;
    String dob, address;
    ArrayList<DataArray> mListItem;
    TextView userName, userEmail, phone, userAddress, userDate_of_Birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initMethod();
        setAction();
    }

    private void initMethod() {
        localDataBase=new LocalDataBase(this);
        backPress = findViewById(R.id.backPress);
        userProfile = findViewById(R.id.profilePic);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        phone = findViewById(R.id.phone);
        userAddress = findViewById(R.id.userAddress);
        userDate_of_Birth = findViewById(R.id.userDate_of_Birth);
        profileChange = findViewById(R.id.profileChange);
        share = findViewById(R.id.share);
        sessionManager = new SessionManager(getApplicationContext());

        userName.setText("" + sessionManager.getUserData().getUserName());
        userEmail.setText("" + sessionManager.getUserData().getEmailId());
        phone.setText("" + sessionManager.getUserData().getMobileNo());
        Log.d("Amit","Value "+sessionManager.getUserLoginImage());

        if(sessionManager.getUserLoginImage()!=null){
            Glide.with(this)
                    .load(sessionManager.getUserLoginImage())
                    .into(userProfile);
        }
        else{
            userProfile.setImageResource(R.drawable.ic_profile_pic);
        }

        getUserDetailFromAPI();
    }

    private void setAction() {
        backPress.setOnClickListener(view -> onBackPressed());
        //Profile Share-----------------------------------------
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Crack11");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        profileChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = "profileImage";
                ImagePicker.with(ProfileActivity.this)
                        .crop(16f, 9f)
                        .start();
            }
        });
    }

    private void UpdateProfileImage(String profileImagePath, Uri selectedImage) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setTitle("Please wait..");

        MultipartBody.Part fileToUpload1 = null;
        File myFile1 = new File(profileImagePath);

        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), myFile1);
        fileToUpload1 = MultipartBody.Part.createFormData("image", myFile1.getName(), requestBody1);

        RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"), sessionManager.getUserData().getUser_id());
        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), sessionManager.getUserData().getUserName());



        Call<UpdateProfileResponse> call=ApiClient.getInstance().getApi().UpdateProfile(user_id,name,fileToUpload1);
        call.enqueue(new Callback<UpdateProfileResponse>() {
            @Override
            public void onResponse(Call<UpdateProfileResponse> call, Response<UpdateProfileResponse> response) {
                UpdateProfileResponse updateProfileResponse=response.body();
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, ""+updateProfileResponse.getResponse(), Toast.LENGTH_SHORT).show();
                    HelperData.ProfileImage.setValue(String.valueOf(selectedImage));
                }
            }
            @Override
            public void onFailure(Call<UpdateProfileResponse> call, Throwable t) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (code.equalsIgnoreCase("profileImage")) {
                profileImagePath = FileUriUtils.INSTANCE.getRealPath(this, data.getData());
                Uri selectedImage = data.getData();
                userProfile.setImageURI(selectedImage);
                sessionManager.UserLoginImage(String.valueOf(selectedImage));
                UpdateProfileImage(profileImagePath,selectedImage);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap lastBitmap = null;
                lastBitmap = bitmap;
                //encoding image to string
//                String image = getStringImage(lastBitmap);
//                SendImage(image);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.myInfo:
                return true;
            case R.id.searchProfile:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void getUserDetailFromAPI() {
        Call<ViewKycResponse> call = ApiClient.getInstance().getApi().getkycDetails(sessionManager.getUserData().getUser_id());
        call.enqueue(new Callback<ViewKycResponse>() {
            @Override
            public void onResponse(Call<ViewKycResponse> call, Response<ViewKycResponse> response) {
                ViewKycResponse viewKycResponse = response.body();
                if (response.isSuccessful()) {
                    String data = new Gson().toJson(viewKycResponse.isStatus());
                    status = Boolean.parseBoolean(data);
                    JSONArray jsonArray1 = null;
                    if (status != false) {
                        String data2 = new Gson().toJson(viewKycResponse.getKycDetails());
                        try {
                            jsonArray1 = new JSONArray(data2);
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject jsonObject = jsonArray1.getJSONObject(i);
                                dob = jsonObject.getString("dob");
                                address = jsonObject.getString("address");
                                String status = jsonObject.getString("status");
                            }
                            userAddress.setText(address);
                            userDate_of_Birth.setText(dob);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewKycResponse> call, Throwable t) {
            }
        });
    }
}