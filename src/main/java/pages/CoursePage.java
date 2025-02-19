package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CoursePage {
    private final Page page;
    private final String baseUrl;

    public CoursePage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public void navigateToDevelopment() {
        page.navigate(baseUrl + "/uslugi-kompaniyam");
    }

    public void navigateToCatalog() {
        page.navigate(baseUrl + "/catalog/courses");
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
    public void navigateToSubscription() {
        page.navigate(baseUrl + "/subscription");
    }

    public Locator getSubscriptionTiles() {
        return page.locator(".subscription-tile");
    }

    public boolean isSubscriptionDescriptionVisible() {
        return page.locator(".subscription-description").isVisible();
    }

    public void clickSubscriptionCollapse() {
        page.locator("text='Свернуть'").click();
    }

    public void clickBuyButton() {
        page.locator("button:has-text('Купить')").click();
    }

    public boolean isPaymentPageVisible() {
        return page.locator(".payment-page").isVisible();
    }

    public Locator getCoursePrice() {
        return page.locator(".course-price");
    }

    public void selectTrialSubscription() {
        page.locator("input[name='subscription'][value='trial']").check();
    }

    public Locator get;
}