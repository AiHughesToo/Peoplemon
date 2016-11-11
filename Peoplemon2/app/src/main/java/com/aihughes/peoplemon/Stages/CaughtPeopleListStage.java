package com.aihughes.peoplemon.Stages;

import android.app.Application;

import com.aihughes.peoplemon.PeoplemonApplication;
import com.aihughes.peoplemon.R;
import com.aihughes.peoplemon.Riggers.SlideRigger;
import com.davidstemmer.screenplay.stage.XmlStage;

/**
 * Created by alexhughes on 11/10/16.
 */

public class CaughtPeopleListStage extends IndexedStage {

    private final SlideRigger rigger;

    public CaughtPeopleListStage(Application context){
        super(LoginStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public CaughtPeopleListStage() {
        this(PeoplemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.caught_people_listview;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
