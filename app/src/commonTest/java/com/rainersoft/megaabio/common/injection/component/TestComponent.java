package com.rainersoft.megaabio.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import com.rainersoft.megaabio.common.injection.module.ApplicationTestModule;
import com.rainersoft.megaabio.injection.component.AppComponent;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends AppComponent {
}
