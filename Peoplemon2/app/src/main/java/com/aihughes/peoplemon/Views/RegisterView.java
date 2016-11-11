package com.aihughes.peoplemon.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aihughes.peoplemon.Models.Account;
import com.aihughes.peoplemon.Network.RestClient;
import com.aihughes.peoplemon.PeoplemonApplication;
import com.aihughes.peoplemon.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alexhughes on 11/6/16.
 */

public class RegisterView extends LinearLayout {

    private Context context;

    private String apiKey = "iOSandroid301november2016";

    @Bind(R.id.email_field)
    EditText emailField;

    @Bind(R.id.full_name_field)
    EditText fullNameField;

    @Bind(R.id.password_field)
    EditText passwordField;

    @Bind(R.id.confirm_field)
    EditText confirmField;

    @Bind(R.id.register_button)
    Button registerButton;

    @Bind(R.id.spinner)
    ProgressBar spinner;

    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    public void register(){
        InputMethodManager imm = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(emailField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(fullNameField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(passwordField.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(confirmField.getWindowToken(), 0);


        String email = emailField.getText().toString();
        String fullName = fullNameField.getText().toString();
        String apiKey = "iOSandroid301november2016";
        String password = passwordField.getText().toString();
        String confirm = confirmField.getText().toString();
        String avatarBase64 = "string";

        if (fullName.isEmpty() || email.isEmpty() ||
                password.isEmpty() || confirm.isEmpty()){
            Toast.makeText(context,"Please fill out all fields",
                    Toast.LENGTH_LONG).show();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(context,"Please provide a valid email",
                    Toast.LENGTH_SHORT).show();
        }else if (!password.equals(confirm)){
            Toast.makeText(context,"Passwords do not match",
                    Toast.LENGTH_SHORT).show();
        } else{
            registerButton.setEnabled(false);
            spinner.setVisibility(VISIBLE);

            Account account = new Account(avatarBase64, apiKey, fullName, email, password);
            Log.d("Account info", account.toString());
            RestClient restClient = new RestClient();
            restClient.getApiService().register(account).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    if (response.isSuccessful()) {


                        //This will set up the flow of the application to show the next view upon successful registration
                        Flow flow = PeoplemonApplication.getMainFlow();
                        flow.goBack();
                    } else {

                        //This will return if the user has entered info but they have registered before
                        resetView();
                        Toast.makeText(context, "bad" + ": " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                    //This will show up if the data didn't come back from the server correctly or there is a timeout.
                    resetView();
                    Toast.makeText(context, "I did not call out ", Toast.LENGTH_LONG).show();
                    t.printStackTrace();
                }
            });
        }

    }

    private void resetView(){
        registerButton.setEnabled(true);
        spinner.setVisibility(GONE);
    }

}

