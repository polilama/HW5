package org.example;

import com.google.inject.Inject;
import com.microsoft.playwright.*;
import config.ConfigLoader;

public class PlaywrightFixture {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private String baseUrl;

    @Inject
    public PlaywrightFixture(ConfigLoader configLoader) {
        this.playwright = Playwright.create();
        this.browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        this.context = browser.newContext();
        this.page = context.newPage();
        this.baseUrl = configLoader.getBaseUrl(); // Получаем базовый URL из конфигурации
    }
    public Page getPage() {
        return page;
    }

    public String getBaseUrl() {
        return baseUrl;
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