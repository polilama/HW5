package org.example;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PlaywrightFixture.class).toInstance(new PlaywrightFixture());
    }
}