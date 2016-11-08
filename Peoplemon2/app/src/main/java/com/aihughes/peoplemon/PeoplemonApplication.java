package com.aihughes.peoplemon;

import android.app.Application;

import com.aihughes.peoplemon.Stages.MapsViewStage;

import flow.Flow;
import flow.History;

/**
 * Created by alexhughes on 11/6/16.
 */

public class PeoplemonApplication extends Application {
    private static PeoplemonApplication application;
    public final Flow mainFlow = new Flow(History.single(new MapsViewStage()));

    public static final String API_BASE_URL = "https://efa-peoplemon-api.azurewebsites.net/";
   //public static final String API_BASE_URL = "https://efa-peoplemon-api.azurewebsites.net/swagger/ui/index#/";
    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

    // like globals singleton is loaded into app and can be used everywhere

    public static PeoplemonApplication getInstance(){
        return application;
    }

    public static Flow getMainFlow(){return getInstance().mainFlow;}

}
