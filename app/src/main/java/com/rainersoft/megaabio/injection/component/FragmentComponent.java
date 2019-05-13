package com.rainersoft.megaabio.injection.component;

import dagger.Subcomponent;
import com.rainersoft.megaabio.injection.PerFragment;
import com.rainersoft.megaabio.injection.module.FragmentModule;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
}
