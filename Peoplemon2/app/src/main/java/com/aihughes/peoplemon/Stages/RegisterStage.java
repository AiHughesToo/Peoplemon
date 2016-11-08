package com.aihughes.peoplemon.Stages;

import android.app.Application;

import com.aihughes.peoplemon.PeoplemonApplication;
import com.aihughes.peoplemon.R;
import com.aihughes.peoplemon.Riggers.SlideRigger;

/**
 * Created by alexhughes on 11/6/16.
 */

public class RegisterStage extends IndexedStage {

    private final SlideRigger rigger;

    public RegisterStage(Application context){
        super(RegisterStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public RegisterStage() {
        this(PeoplemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.register_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

}
