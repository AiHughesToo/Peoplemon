package com.aihughes.peoplemon.Views;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aihughes.peoplemon.Adapters.CaughtPeopleListAdapter;
import com.aihughes.peoplemon.Adapters.NearbyPeopleListAdapter;
import com.aihughes.peoplemon.Models.User;
import com.aihughes.peoplemon.Network.RestClient;
import com.aihughes.peoplemon.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alexhughes on 11/10/16.
 */

public class NearbyPeopleListView extends LinearLayout {

    private Context context;
    private NearbyPeopleListAdapter nearbyAdapter;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;


    public NearbyPeopleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        nearbyAdapter = new NearbyPeopleListAdapter(new ArrayList<User>(), context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(nearbyAdapter);

        listCaughtPeople();
    }

    private void listCaughtPeople(){

        RestClient restClient = new RestClient();
        restClient.getApiService().findNearby(500).enqueue(new Callback<User[]>() {

            @Override
            public void onResponse(Call<User[]> call, Response<User[]> response) {
                // Is the server response between 200 to 299
                if (response.isSuccessful()){
                    int i = 0;

                    nearbyAdapter.users = new ArrayList<User>(Arrays.asList(response.body()));

                    for (User user : nearbyAdapter.users){

                        nearbyAdapter.notifyDataSetChanged();
                    }

                }else{
                    Toast.makeText(context,"Failed" + ": " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User[]> call, Throwable t) {
                Toast.makeText(context,"Failed", Toast.LENGTH_LONG).show();
            }
        });
    }
}