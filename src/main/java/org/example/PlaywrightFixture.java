package org.example;

import com.microsoft.playwright.*;

import javax.inject.Inject;

public class PlaywrightFixture {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @Inject
    public PlaywrightFixture() {
        this.playwright = Playwright.create();
        this.browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        this.context = browser.newContext();
        this.page = context.newPage();
    }

    public Page getPage() {

        return page;
    }

    public void close() {
        if (page != null) {
            page.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}

