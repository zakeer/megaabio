package com.rainersoft.megaabio.injection.component;

import dagger.Component;
import com.rainersoft.megaabio.features.base.BaseActivity;
import com.rainersoft.megaabio.features.base.BaseFragment;
import com.rainersoft.megaabio.injection.ConfigPersistent;
import com.rainersoft.megaabio.injection.module.ActivityModule;
import com.rainersoft.megaabio.injection.module.FragmentModule;

/**
 * A dagger component that will live during the lifecycle of an Activity or Fragment but it won't be
 * destroy during configuration changes. Check {@link BaseActivity} and {@link BaseFragment} to see
 * how this components survives configuration changes. Use the {@link ConfigPersistent} scope to
 * annotate dependencies that need to survive configuration changes (for example Presenters).
 */
@ConfigPersistent
@Component(dependencies = AppComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

    FragmentComponent fragmentComponent(FragmentModule fragmentModule);
}
