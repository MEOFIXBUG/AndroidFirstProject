package com.ygaps.travelapp.extras;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.ygaps.travelapp.R;

public class MyApplication {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public MyApplication(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.s_pref_file), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //Setting login status
    public void setLoginStatus(boolean status){
        editor.putBoolean(String.valueOf(R.string.s_pref_login_status), status);
        editor.commit();
    }
    public void setFirebaseToken(String token)
    {
        editor.putString("firebase_token", token);
        editor.commit();
    }
    public String getFirebaseToken()
    {
       return sharedPreferences.getString("firebase_token", "");
    }
    public boolean getLoginStatus(){
        return sharedPreferences.getBoolean(String.valueOf(R.string.s_pref_login_status), false);
    }
    public void setFirebaseSTT(boolean isReg)
    {
        editor.putBoolean("FirebaseSTT", isReg);
        editor.commit();
    }
    public Boolean getFirebaseSTT()
    {
        return sharedPreferences.getBoolean("FirebaseSTT", true);
    }
    //Setting Token
    public void setToken(String token){
        editor.putString(String.valueOf(R.string.s_pref_token), token);
        editor.commit();
    }
    public String  getToken() {
        return sharedPreferences.getString(String.valueOf(R.string.s_pref_token), "");
    }
    public String  getUserID() {
        return sharedPreferences.getString(String.valueOf(R.string.s_pref_id), "");
    }
    public void setUserID(String userID){
        editor.putString(String.valueOf(R.string.s_pref_id), userID);
    }
    public String  getEmailVerified() {
        return sharedPreferences.getString(String.valueOf(R.string.s_pref_email_verified), "");
    }
    public void setEmailVerified(Boolean eV){
        editor.putString(String.valueOf(R.string.s_pref_email_verified), eV.toString());
    }

    // For Name
    public void setDisplayName(String name){
        editor.putString(String.valueOf(R.string.s_pref_name), name);
        editor.commit();
    }
    public String getDisplayName(){
        return sharedPreferences.getString(String.valueOf(R.string.s_pref_name), "Name");
    }

    //For email
    public void setDisplayEmail(String email){
        editor.putString(String.valueOf(R.string.s_pref_email), email);
        editor.commit();
    }
    public String getDisplayEmail(){
        return sharedPreferences.getString(String.valueOf(R.string.s_pref_email), "email");
    }

    //For createDate
    public void setCreDate(String date){
        editor.putString(String.valueOf(R.string.s_pref_date), date);
        editor.commit();
    }
    public String getCreDate(){
        return sharedPreferences.getString(String.valueOf(R.string.s_pref_date), "date");
    }

    // For TOAST Message for response
    public void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
