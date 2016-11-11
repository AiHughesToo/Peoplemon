package com.aihughes.peoplemon.Stages;

import android.app.Application;

import com.aihughes.peoplemon.PeoplemonApplication;
import com.aihughes.peoplemon.R;
import com.aihughes.peoplemon.Riggers.SlideRigger;

/**
 * Created by alexhughes on 11/9/16.
 */

public class NearbyPeopleListStage extends IndexedStage {

    private final SlideRigger rigger;

    public NearbyPeopleListStage(Application context){
        super(LoginStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public NearbyPeopleListStage() {
        this(PeoplemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.nearby_people_listview;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}