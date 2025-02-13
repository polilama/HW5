import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.microsoft.playwright.*;
import org.example.PlaywrightFixture;
import org.example.TestModule;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TeacherTest {
    @Inject
    private PlaywrightFixture playwrightFixture;

    private Page page;
    @AfterEach
    public void tearDown() {
        if (playwrightFixture != null) {
            playwrightFixture.close();
        }
    }


    @BeforeEach
    public void setup() {
        Injector injector = Guice.createInjector(new TestModule());
        this.playwrightFixture = injector.getInstance(PlaywrightFixture.class);

        page = playwrightFixture.getPage();
    }


    @Test
    public void testTeachersTiles() {
        page.navigate("https://otus.ru/lessons/clickhouse/");

        // Проверка наличия блока "Преподаватели"
        Locator teachersSection = page.locator("h2:has-text('Преподаватели')");
        assertTrue(teachersSection.isVisible(), "Блок 'Преподаватели' не найден");

        // Проверка плиток преподавателей
        Locator teacherTiles = page.locator(".teachers-list .teacher-tile");
        assertTrue(teacherTiles.count() > 0, "Плитки преподавателей не отображаются");

        // Прокрутка с drag-and-drop
        Locator firstTile = teacherTiles.locator(":first-child");
        Locator lastTile = teacherTiles.locator(":last-child");
        firstTile.hover();
        firstTile.dragTo(lastTile);

        // Проверка, что список прокрутился
        Locator newFirstTile = teacherTiles.locator(":first-child");
        assertNotEquals(firstTile, newFirstTile, "Список преподавателей не прокрутился");

        // Клик на плитку преподавателя
        firstTile.click();
        Locator popup = page.locator(".teacher-popup");
        assertTrue(popup.isVisible(), "Popup преподавателя не открылся");

        // Проверка кнопок навигации
        page.locator(".next-button").click();
        assertTrue(page.locator(".teacher-card").isVisible(), "Карточка следующего преподавателя не открылась");

        page.locator(".prev-button").click();
        assertTrue(page.locator(".teacher-card").isVisible(), "Карточка предыдущего преподавателя не открылась");
    }


    @Test
    public void testCourseDevelopment() {
        page.navigate("https://otus.ru/uslugi-kompaniyam");

        // Клик по тексту "Не нашли нужный курс?"
        Locator notFoundText = page.locator("text='Не нашли нужный курс?'");
        assertTrue(notFoundText.isVisible(), "Текст 'Не нашли нужный курс?' не найден");
        notFoundText.click();

        // Проверка, что страница "Разработка курса для бизнеса" открылась
        Locator courseDevelopmentText = page.locator("text='Разработка курса для бизнеса'");
        assertTrue(courseDevelopmentText.isVisible(), "Страница 'Разработка курса для бизнеса' не открылась");

        // Проверка наличия направлений обучения
        Locator directions = page.locator(".directions");
        assertTrue(directions.isVisible(), "Направления обучения не отображаются");

        // Клик по первому направлению
        Locator firstDirection = directions.locator(":first-child");
        assertTrue(firstDirection.isVisible(), "Первое направление не найдено");
        firstDirection.click();

        // Проверка, что каталог курсов открылся
        Locator courseCatalog = page.locator(".course-catalog");
        assertTrue(courseCatalog.isVisible(), "Каталог курсов не открылся");
    }

    @Test
    public void testCourseFilter() {
        page.navigate("https://otus.ru/catalog/courses");

        // Проверка, что выбраны все направления и уровень сложности
        Locator allDirections = page.locator("text='Все направления'");
        Locator anyLevel = page.locator("text='Любой уровень сложности'");

        // чекбоксы
        assertTrue(allDirections.isChecked(), "Не выбраны все направления");
        assertTrue(anyLevel.isChecked(), "Не выбран любой уровень сложности");

        // Выбор продолжительности курса
        page.locator("input[name='duration'][value='3-10']").check();

        // Обновление courseTiles после применения фильтра
        Locator courseTiles = page.locator(".course-tile");
        assertTrue(courseTiles.count() > 0, "Не отображаются курсы с выбранной продолжительностью");

        // Выбор направления "Архитектура"
        page.locator("text='Архитектура'").click();

        // Обновление courseTiles после выбора направления
        courseTiles = page.locator(".course-tile");
        assertTrue(courseTiles.count() > 0, "Курсы по архитектуре не отображаются");

        // Сброс фильтра
        page.locator("text='Сбросить фильтр'").click();

        // Обновление courseTiles
        courseTiles = page.locator(".course-tile");
        assertTrue(courseTiles.count() > 0, "Фильтр не сброшен");
    }
    @Test
    public void testSubscriptionOptions() {
        page.navigate("https://otus.ru/subscription");

        // Проверка отображения вариантов подписки
        Locator subscriptionTiles = page.locator(".subscription-tile");
        assertTrue(subscriptionTiles.count() > 0, "Варианты подписки не отображаются");

        // Клик на плитку подписки
        subscriptionTiles.locator(":first-child").locator("text='Подробнее'").click();
        assertTrue(page.locator(".subscription-description").isVisible(), "Описание не развернулось");

        // Клик на кнопку "Свернуть"
        page.locator("text='Свернуть'").click();
        assertTrue(page.locator(".subscription-description").isVisible(), "Описание не свернулось");

        // Открытие страницы оплаты
        page.locator("button:has-text('Купить')").click();
        assertTrue(page.locator(".payment-page").isVisible(), "Страница оплаты не открылась");

        // Проверка стоимости
        Locator price = page.locator(".course-price");
        assertTrue(price.isVisible(), "Стоимость курса не отображается");

        // Выбор Trial подписки и проверка стоимости
        page.locator("input[name='subscription'][value='trial']").check();
        assertTrue(price.textContent().contains("0"), "Стоимость не изменилась для Trial подписки");
    }
}

