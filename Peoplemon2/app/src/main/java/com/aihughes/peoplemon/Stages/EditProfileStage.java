package com.aihughes.peoplemon.Stages;

import android.app.Application;

import com.aihughes.peoplemon.PeoplemonApplication;
import com.aihughes.peoplemon.R;
import com.aihughes.peoplemon.Riggers.SlideRigger;

/**
 * Created by alexhughes on 11/9/16.
 */

public class EditProfileStage extends IndexedStage {

    private final SlideRigger rigger;

    public EditProfileStage(Application context){
        super(LoginStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public EditProfileStage() {
        this(PeoplemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.edit_profile_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}