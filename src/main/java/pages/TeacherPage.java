package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TeacherPage {
    private final Page page;

    public TeacherPage(Page page) {
        this.page = page;
    }

    public void navigate() {
        page.navigate("https://otus.ru/lessons/clickhouse/");
    }

    public boolean isTeachersSectionVisible() {
        Locator teachersSection = page.locator("h2:has-text('Преподаватели')");
        return teachersSection.isVisible();
    }

    public int getTeacherTilesCount() {
        Locator teacherTiles = page.locator(".teachers-list .teacher-tile");
        return teacherTiles.count();
    }
    public void dragAndDropFirstTile() {
        Locator teacherTiles = page.locator(".teachers-list .teacher-tile");
        Locator firstTile = teacherTiles.locator(":first-child");
        Locator lastTile = teacherTiles.locator(":last-child");
        firstTile.hover();
        firstTile.dragTo(lastTile);
    }

    public boolean isTeacherPopupVisible() {
        Locator popup = page.locator(".teacher-popup");
        return popup.isVisible();
    }

    public void clickFirstTeacherTile() {
        Locator teacherTiles = page.locator(".teachers-list .teacher-tile");
        teacherTiles.locator(":first-child").click();
    }

    public void clickNextButton() {
        page.locator(".next-button").click();
    }

    public void clickPrevButton() {
        page.locator(".prev-button").click();
    }
}

