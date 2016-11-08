package com.aihughes.peoplemon.Stages;
import android.app.Application;
import com.aihughes.peoplemon.PeoplemonApplication;
import com.aihughes.peoplemon.R;
import com.aihughes.peoplemon.Riggers.SlideRigger;

/**
 * Created by alexhughes on 11/6/16.
 */

public class LoginStage extends IndexedStage {

    private final SlideRigger rigger;

    public LoginStage(Application context){
        super(LoginStage.class.getName());
        this.rigger = new SlideRigger(context);
    }
    public LoginStage() {
        this(PeoplemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.login_view;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
