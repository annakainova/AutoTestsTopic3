package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.Color;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;

public class cardDeliveryTest {

    //get date after today
    //in - how many days after today
    //out - string with date
    String getDate(int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Using today's date
        c.add(Calendar.DATE, days); // Adding x days
        String date = sdf.format(c.getTime());
        return date;
    }

    @Test
    public void sendFormSuccessfulTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(getDate(4));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//div[contains(text(), 'Успешно!')]").should(appear, Duration.ofSeconds(15));
    }

    @Test
    public void sendFormCityNotFromListTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Рыбинск");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(getDate(4));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='city']//span[@class='input__sub'][contains(text(), 'Доставка в выбранный город недоступна')]").should(appear);
    }

    @Test
    public void sendFormEmptyCityTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(getDate(4));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='city']//span[@class='input__sub'][contains(text(), 'Поле обязательно для заполнения')]").should(appear);
    }

    @Test
    public void sendFormEmptyDateTest() {
        Configuration.holdBrowserOpen = true;
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
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(getDate(1));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='date']//span[@class='input__sub'][contains(text(), 'Заказ на выбранную дату невозможен')]").should(appear);
    }

    @Test
    public void sendFormEmptyNameTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(getDate(4));
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='name']//span[@class='input__sub'][contains(text(), 'Поле обязательно для заполнения')]").should(appear);
    }

    @Test
    public void sendFormLatinInNameTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(getDate(4));
        $("[data-test-id='name'] input").setValue("Ivan Ivanov");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='name']//span[@class='input__sub'][contains(text(), 'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]").should(appear);
    }

    @Test
    public void sendFormEmptyPhoneTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(getDate(4));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='phone']//span[@class='input__sub'][contains(text(), 'Поле обязательно для заполнения')]").should(appear);
    }

    @Test
    public void sendFormTooMuchSymbolsPhoneTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(getDate(4));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678999");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='phone']//span[@class='input__sub'][contains(text(), 'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]").should(appear);
    }

    @Test
    public void sendFormNotEnoughSymbolsPhoneTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(getDate(4));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012378");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='phone']//span[@class='input__sub'][contains(text(), 'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]").should(appear);
    }

    @Test
    public void sendFormWithoutPlusSymbolsPhoneTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(getDate(4));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//span[@data-test-id='phone']//span[@class='input__sub'][contains(text(), 'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]").should(appear);
    }

    @Test
    public void sendFormEmptyCheckBoxTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(getDate(4));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button__text").click();
        String color = $x("//label[@data-test-id='agreement']//span[@class='checkbox__text']").getCssValue("color");
        String actual = Color.fromString(color).asHex();
        Assertions.assertEquals("#ff5c5c", actual);
    }

}
