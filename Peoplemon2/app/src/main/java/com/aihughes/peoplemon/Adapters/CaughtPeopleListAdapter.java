package com.aihughes.peoplemon.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aihughes.peoplemon.Models.User;
import com.aihughes.peoplemon.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by alexhughes on 11/10/16.
 */

public class CaughtPeopleListAdapter extends RecyclerView.Adapter<CaughtPeopleListAdapter.UserHolder> {

    public ArrayList<User> users;
    private Context context;

    public CaughtPeopleListAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }


    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflateView = LayoutInflater.from(context).inflate(R.layout.caught_people_item_view, parent, false);
        return new UserHolder(inflateView);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {

        if(position <users.size()){
            User user = users.get(position);
            holder.bindUser(user);

        }
    }

    @Override
    public int getItemCount() {

        return users.size()+1;
    }


    class UserHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.caught_avatar)
        ImageView caughtAvatar;

        @Bind(R.id.caught_username)
        TextView caughtUserName;

        public UserHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


        public void bindUser(User user){
            caughtUserName.setText(user.getUserName());


        }
    }
}