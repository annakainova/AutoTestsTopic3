package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class cardDeliveryTest {

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void sendFormSuccessfulTest() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        String date = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='notification'] .notification__content").should(appear, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(exactText(("Встреча успешно забронирована на " + date)));
    }

    @Test
    public void sendFormCityNotFromListTest() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Рыбинск");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(generateDate(4, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='city']//span[@class='input__sub'][contains(text(), 'Доставка в выбранный город недоступна')]").should(appear);
    }

    @Test
    public void sendFormEmptyCityTest() {
        open("http://localhost:9999/");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(generateDate(4, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='city']//span[@class='input__sub'][contains(text(), 'Поле обязательно для заполнения')]").should(appear);
    }

    @Test
    public void sendFormEmptyDateTest() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='date']//span[@class='input__sub'][contains(text(), 'Неверно введена дата')]").should(appear);
    }

    @Test
    public void sendFormDateEarlierThan3DaysTest() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(generateDate(1, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='date']//span[@class='input__sub'][contains(text(), 'Заказ на выбранную дату невозможен')]").should(appear);
    }

    @Test
    public void sendFormEmptyNameTest() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(generateDate(4, "dd.MM.yyyy"));
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='name']//span[@class='input__sub'][contains(text(), 'Поле обязательно для заполнения')]").should(appear);
    }

    @Test
    public void sendFormLatinInNameTest() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(generateDate(4, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Ivan Ivanov");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='name']//span[@class='input__sub'][contains(text(), 'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]").should(appear);
    }

    @Test
    public void sendFormEmptyPhoneTest() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(generateDate(4, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='phone']//span[@class='input__sub'][contains(text(), 'Поле обязательно для заполнения')]").should(appear);
    }

    @Test
    public void sendFormTooMuchSymbolsPhoneTest() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(generateDate(4, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678999");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='phone']//span[@class='input__sub'][contains(text(), 'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]").should(appear);
    }

    @Test
    public void sendFormNotEnoughSymbolsPhoneTest() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(generateDate(4, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012378");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='phone']//span[@class='input__sub'][contains(text(), 'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]").should(appear);
    }

    @Test
    public void sendFormWithoutPlusSymbolsPhoneTest() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(generateDate(4, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='phone']//span[@class='input__sub'][contains(text(), 'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]").should(appear);
    }

    @Test
    public void sendFormEmptyCheckBoxTest() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(generateDate(4, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button__text").click();
        $("[data-test-id='agreement'].input_invalid").shouldBe(enabled);
        $x("//div[contains(text(), 'Успешно!')]").shouldNot(appear, Duration.ofSeconds(15));
    }

    @Test
    public void sendFormAutoFillingForCityAndDateTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").sendKeys("Ка");
        ElementsCollection cities = $$("div.menu-item");
        cities.findBy(exactText("Казань")).click();

        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        //check if a meeting will be in this month or next
        String todayMonth = generateDate(0, "M");
        String meetingMonth = generateDate(7, "M");

        if (!todayMonth.equals(meetingMonth)) {
            //switch on next month
            $("div.calendar__arrow_direction_right[data-step='1']").click();
        }

        //click on meeting day
        ElementsCollection days = $$("td.calendar__day"); //find all days
        String meetingDay = generateDate(7, "d");
        days.findBy(exactText(meetingDay)).click();

        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $("[data-test-id='notification'] .notification__content").should(appear, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(exactText(("Встреча успешно забронирована на " + generateDate(7, "dd.MM.yyyy"))));
    }
}
