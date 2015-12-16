package uk.ivanc.archimvvm.model;

import javax.inject.Singleton;

import dagger.Component;
import uk.ivanc.archimvvm.viewmodel.MainViewModel;
import uk.ivanc.archimvvm.viewmodel.RepositoryViewModel;

/**
 * Created by fyunli on 15/12/16.
 */
@Singleton
@Component(modules = {GithubModule.class})
public interface GithubComponent {

    void inject(MainViewModel model);

    void inject(RepositoryViewModel model);

}
