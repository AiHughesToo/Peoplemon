package com.aihughes.peoplemon.Network;

import android.content.Context;
import android.content.SharedPreferences;

import com.aihughes.peoplemon.Components.Constants;
import com.aihughes.peoplemon.PeoplemonApplication;

import java.util.Date;

/**
 * Created by alexhughes on 11/6/16.
 */

public class UserStore {
    private static UserStore ourInstance = new UserStore();

    public static UserStore getInstance(){

        return ourInstance;
    }

    private SharedPreferences sharedPrefs = PeoplemonApplication.getInstance().getSharedPreferences("BudgetPrefs",Context.MODE_PRIVATE);

    public String getToken(){
        String theToken = sharedPrefs.getString(Constants.token, null);
        return theToken;
    }
    public void setToken(String token){

        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(Constants.token, token);
        editor.apply();
    }

    public Date getTokenExpiration(){
        Long experation = sharedPrefs.getLong(Constants.tokenExpiration, 0);
        Date date = new Date(experation);
        if (date.before(new Date())){
            return null;
        }
        return date;
    }
    public void setTokenExpiration(Date expiration){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putLong(Constants.tokenExpiration, expiration.getTime());
        editor.apply();
    }
}
