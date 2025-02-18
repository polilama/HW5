package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CoursePage {
    private final Page page;

    public CoursePage(Page page) {
        this.page = page;
    }

    public void navigateToDevelopment() {
        page.navigate("https://otus.ru/uslugi-kompaniyam");
    }

    public boolean isNotFoundTextVisible() {
        Locator notFoundText = page.locator("text='Не нашли нужный курс?'");
        return notFoundText.isVisible();
    }

    public void clickNotFoundText() {
        Locator notFoundText = page.locator("text='Не нашли нужный курс?'");
        notFoundText.click();
    }

    public boolean isCourseDevelopmentTextVisible() {
        Locator courseDevelopmentText = page.locator("text='Разработка курса для бизнеса'");
        return courseDevelopmentText.isVisible();
    }

    public boolean areDirectionsVisible() {
        Locator directions = page.locator(".directions");
        return directions.isVisible();
    }

    public void clickFirstDirection() {
        Locator directions = page.locator(".directions");
        directions.locator(":first-child").click();
    }

    public boolean isCourseCatalogVisible() {
        Locator courseCatalog = page.locator(".course-catalog");
        return courseCatalog.isVisible();
    }

    public void navigateToCatalog() {
        page.navigate("https://otus.ru/catalog/courses");
    }

    public boolean areAllDirectionsChecked() {
        Locator allDirections = page.locator("text='Все направления'");
        return allDirections.isChecked();
    }

    public boolean isAnyLevelChecked() {
        Locator anyLevel = page.locator("text='Любой уровень сложности'");
        return anyLevel.isChecked();
    }

    public void checkCourseDuration(String value) {
        page.locator("input[name='duration'][value='" + value + "']").check();
    }

    public int getCourseTilesCount() {
        Locator courseTiles = page.locator(".course-tile");
        return courseTiles.count();
    }

    public void clickArchitecture() {
        page.locator("text='Архитектура'").click();
    }

    public void resetFilter() {
        page.locator("text='Сбросить фильтр'").click();
    }
}