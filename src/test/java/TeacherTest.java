import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.TeacherPage;
import pages.CoursePage;
import org.example.PlaywrightFixture;
import org.example.TestModule;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherTest {
    @Inject
    private PlaywrightFixture playwrightFixture;

    @Inject
    private TeacherPage teacherPage;

    @Inject
    private CoursePage coursePage;

    @BeforeEach
    public void setup() {
        Injector injector = Guice.createInjector(new TestModule());
        this.playwrightFixture = injector.getInstance(PlaywrightFixture.class);
        injector.injectMembers(this);
    }

    @AfterEach
    public void tearDown() {
        if (playwrightFixture != null) {
            playwrightFixture.close();
        }
    }

    @Test
    public void testTeachersTiles() {
        teacherPage.navigate();

        assertTrue(teacherPage.isTeachersSectionVisible(), "Блок 'Преподаватели' не найден");
        assertTrue(teacherPage.getTeacherTilesCount() > 0, "Плитки преподавателей не отображаются");

        teacherPage.dragAndDropFirstTile();

        // Проверка, что список прокрутился
        assertNotEquals(teacherPage.getTeacherTilesCount(), teacherPage.getTeacherTilesCount(), "Список преподавателей не прокрутился");

        teacherPage.clickFirstTeacherTile();
        assertTrue(teacherPage.isTeacherPopupVisible(), "Popup преподавателя не открылся");

        teacherPage.clickNextButton();
        assertTrue(teacherPage.isTeacherPopupVisible(), "Карточка следующего преподавателя не открылась");

        teacherPage.clickPrevButton();
        assertTrue(teacherPage.isTeacherPopupVisible(), "Карточка предыдущего преподавателя не открылась");
    }

    @Test
    public void testCourseDevelopment() {
        coursePage.navigateToDevelopment();

        assertTrue(coursePage.isNotFoundTextVisible(), "Текст 'Не нашли нужный курс?' не найден");
        coursePage.clickNotFoundText();

        assertTrue(coursePage.isCourseDevelopmentTextVisible(), "Страница 'Разработка курса для бизнеса' не открылась");
        assertTrue(coursePage.areDirectionsVisible(), "Направления обучения не отображаются");

        coursePage.clickFirstDirection();
        assertTrue(coursePage.isCourseCatalogVisible(), "Каталог курсов не открылся");
    }

    @Test
    public void testCourseFilter() {
        coursePage.navigateToCatalog();

        assertTrue(coursePage.areAllDirectionsChecked(), "Не выбраны все направления");
        assertTrue(coursePage.isAnyLevelChecked(), "Не выбран любой уровень сложности");

        coursePage.checkCourseDuration("3-10");
        assertTrue(coursePage.getCourseTilesCount() > 0, "Не отображаются курсы с выбранной продолжительностью");

        coursePage.clickArchitecture();
        assertTrue(coursePage.getCourseTilesCount() > 0, "Курсы по архитектуре не отображаются");

        coursePage.resetFilter();
        assertTrue(coursePage.getCourseTilesCount() > 0, "Фильтр не сброшен");
    }

    @Test
    public void testSubscriptionOptions() {
        coursePage.navigateToSubscription();

        Locator subscriptionTiles = coursePage.getSubscriptionTiles();
        assertTrue(subscriptionTiles.count() > 0, "Варианты подписки не отображаются");

        subscriptionTiles.locator(":first-child").locator("text='Подробнее'").click();
        assertTrue(coursePage.isSubscriptionDescriptionVisible(), "Описание не развернулось");

        coursePage.clickSubscriptionCollapse();
        assertTrue(coursePage.isSubscriptionDescriptionVisible(), "Описание не свернулось");

        coursePage.clickBuyButton();
        assertTrue(coursePage.isPaymentPageVisible(), "Страница оплаты не открылась");

        Locator price = coursePage.getCoursePrice();
        assertTrue(price.isVisible(), "Стоимость курса не отображается");

        coursePage.selectTrialSubscription();
        assertTrue(price.textContent().contains("0"), "Стоимость не изменилась для Trial подписки");
    }
}