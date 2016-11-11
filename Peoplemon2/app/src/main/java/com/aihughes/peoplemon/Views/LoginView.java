package com.aihughes.peoplemon.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aihughes.peoplemon.Models.Account;
import com.aihughes.peoplemon.Network.RestClient;
import com.aihughes.peoplemon.Network.UserStore;
import com.aihughes.peoplemon.R;
import com.aihughes.peoplemon.Stages.MapsViewStage;
import com.aihughes.peoplemon.Stages.RegisterStage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aihughes.peoplemon.Components.Constants.grantType;
import static com.aihughes.peoplemon.PeoplemonApplication.getMainFlow;

/**
 * Created by alexhughes on 11/6/16.
 */

public class LoginView extends LinearLayout {

    private Context context;

    @Bind(R.id.full_name_field)
    EditText fullNameField;

    @Bind(R.id.password_field)
    EditText passwordField;

    @Bind(R.id.login_button)
    Button loginButton;

    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    public void showRegisterView(){
        Log.d("****", "RegisteredClicked");
        Flow flow = getMainFlow();
        History newHistory = flow.getHistory().buildUpon()
                .push(new RegisterStage())
                .build();
        flow.setHistory(newHistory, Flow.Direction.FORWARD);
    }

    @OnClick(R.id.login_button)
    public void login(){

        InputMethodManager imm = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(fullNameField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordField.getWindowToken(), 0);

        String fullName = fullNameField.getText().toString();
        String password = passwordField.getText().toString();
        String grant_Type = grantType;

        if (fullName.isEmpty() || password.isEmpty()){
            Toast.makeText(context,"Please provide full name and password",
                    Toast.LENGTH_LONG).show();
        } else{
            loginButton.setEnabled(true);
            registerButton.setEnabled(true);
            spinner.setVisibility(VISIBLE);


            //Account account = new Account(grant_Type,fullName,password);
            RestClient restClient = new RestClient();
            restClient.getApiService().login(grant_Type, fullName, password).enqueue(new Callback<Account>() {

                @Override
                public void onResponse(Call<Account> call, Response<Account> response) {

                    if (response.isSuccessful()){

                        Account authUser = response.body();


                        //set auth token and expiration date
                        UserStore.getInstance().setToken(authUser.getToken());
                        UserStore.getInstance().setTokenExpiration(authUser.getExpires());

                        Log.d("****", UserStore.getInstance().getToken().toString());

                        //Get main flow and set to single history
                        Flow flow = getMainFlow();
                        History newHistory = History.single(new MapsViewStage());
                        flow.setHistory(newHistory, Flow.Direction.REPLACE);

                    }else{
                        resetView();
                        Toast.makeText(context,"Login Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Account> call, Throwable t) {
                    resetView();
                    Toast.makeText(context,"Login Failed", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void resetView(){
        loginButton.setEnabled(true);
        registerButton.setEnabled(true);
        spinner.setVisibility(GONE);
    }

}
