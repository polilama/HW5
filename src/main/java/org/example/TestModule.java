package org.example;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import config.ConfigLoader;
import pages.CoursePage;
import pages.TeacherPage;

public class TestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PlaywrightFixture.class).toInstance(new PlaywrightFixture(new ConfigLoader()));
    }

    @Provides
    @Singleton
    public TeacherPage provideTeacherPage(PlaywrightFixture playwrightFixture) {
        return new TeacherPage(playwrightFixture.getPage(), playwrightFixture.getBaseUrl());
    }

    @Provides
    @Singleton
    public CoursePage provideCoursePage(PlaywrightFixture playwrightFixture) {
        return new CoursePage(playwrightFixture.getPage(), playwrightFixture.getBaseUrl());
    }
}