package uk.ivanc.archimvvm;

import android.app.Application;
import android.content.Context;

import rx.Scheduler;
import rx.schedulers.Schedulers;
import uk.ivanc.archimvvm.model.DaggerGithubComponent;
import uk.ivanc.archimvvm.model.GithubComponent;

public class ArchiApplication extends Application {

    private Scheduler defaultSubscribeScheduler;
    private GithubComponent githubComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        githubComponent = DaggerGithubComponent.create();
    }

    public static ArchiApplication get(Context context) {
        return (ArchiApplication) context.getApplicationContext();
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }

    public GithubComponent getGithubComponent(){
        return githubComponent;
    }

}
