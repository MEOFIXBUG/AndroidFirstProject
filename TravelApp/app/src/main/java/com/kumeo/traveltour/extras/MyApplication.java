package com.kumeo.traveltour.extras;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.kumeo.traveltour.R;

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
    public boolean getLoginStatus(){
        return sharedPreferences.getBoolean(String.valueOf(R.string.s_pref_login_status), false);
    }
    //Setting Token
    public void setToken(String token){
        editor.putString(String.valueOf(R.string.s_pref_token), token);
        editor.commit();
    }
    public String  getToken() {
        return sharedPreferences.getString(String.valueOf(R.string.s_pref_token), "");
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
