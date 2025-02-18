import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.microsoft.playwright.*;
import org.example.PlaywrightFixture;
import org.example.TestModule;
import org.junit.jupiter.api.*;
import pages.TeacherPage;
import pages.CoursePage;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherTest {
    @Inject
    private PlaywrightFixture playwrightFixture;

    private Page page;
    private TeacherPage teacherPage;
    private CoursePage coursePage;

    @BeforeEach
    public void setup() {
        Injector injector = Guice.createInjector(new TestModule());
        this.playwrightFixture = injector.getInstance(PlaywrightFixture.class);
        page = playwrightFixture.getPage();
        teacherPage = new TeacherPage(page);
        coursePage = new CoursePage(page);
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
        assertTrue(page.locator(".teacher-card").isVisible(), "Карточка следующего преподавателя не открылась");

        teacherPage.clickPrevButton();
        assertTrue(page.locator(".teacher-card").isVisible(), "Карточка предыдущего преподавателя не открылась");
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
        page.navigate("https://otus.ru/subscription");

        Locator subscriptionTiles = page.locator(".subscription-tile");
        assertTrue(subscriptionTiles.count() > 0, "Варианты подписки не отображаются");

        subscriptionTiles.locator(":first-child").locator("text='Подробнее'").click();
        assertTrue(page.locator(".subscription-description").isVisible(), "Описание не развернулось");

        page.locator("text='Свернуть'").click();
        assertTrue(page.locator(".subscription-description").isVisible(), "Описание не свернулось");

        page.locator("button:has-text('Купить')").click();
        assertTrue(page.locator(".payment-page").isVisible(), "Страница оплаты не открылась");

        Locator price = page.locator(".course-price");
        assertTrue(price.isVisible(), "Стоимость курса не отображается");

        page.locator("input[name='subscription'][value='trial']").check();
        assertTrue(price.textContent().contains("0"), "Стоимость не изменилась для Trial подписки");
    }
}