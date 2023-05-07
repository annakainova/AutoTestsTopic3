package ru.netology.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static com.codeborne.selenide.Condition.*;
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
        $("[data-test-id='date'] input").setValue(getDate(1));
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
        $("[data-test-id='date'] input").setValue(getDate(4));
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
        $("[data-test-id='date'] input").setValue(getDate(4));
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
        $("[data-test-id='date'] input").setValue(getDate(4));
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
        $("[data-test-id='date'] input").setValue(getDate(4));
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
        $("[data-test-id='date'] input").setValue(getDate(4));
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
        $("[data-test-id='date'] input").setValue(getDate(4));
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
        $("[data-test-id='date'] input").setValue(getDate(4));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button__text").click();
        $("[data-test-id='agreement'].input_invalid").shouldBe(enabled);
        $x("//div[contains(text(), 'Успешно!')]").shouldNot(appear, Duration.ofSeconds(15));
    }

    @Test
    public void sendFormAutoFillingForCityTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").sendKeys("Ка");
        ElementsCollection col = $$("div.menu-item");
        col.get(5).click();

        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);
        $("[data-test-id='date'] input").setValue(getDate(4));
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//div[contains(text(), 'Успешно!')]").should(appear, Duration.ofSeconds(15));
    }

    @Test
    public void sendFormAutoFillingForDateTest() {
        open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL,"a",Keys.DELETE);

        //get month and year from calendar
        String monthYearFromCalendar = $("div.calendar__name").getText();
        String [] monthYear = monthYearFromCalendar.split(" ");
        //get day from calendar
        String day = $("td.calendar__day_state_today").getText();

        HashMap<String, Integer> months = new HashMap<>();
        months.put("Январь", 0);
        months.put("Февраль", 1);
        months.put("Март", 2);
        months.put("Апрель", 3);
        months.put("Май", 4);
        months.put("Июнь", 5);
        months.put("Июль", 6);
        months.put("Август", 7);
        months.put("Сентябрь", 8);
        months.put("Октябрь", 9);
        months.put("Ноябрь", 10);
        months.put("Декабрь", 11);

        //set the today date in Calendar
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(Integer.parseInt(monthYear[1]) - 1900, months.get(monthYear[0]), Integer.parseInt(day))); // Using today's date
        c.add(Calendar.DATE, 7); // Adding 7 days
        String oneWeekAfterToday = Long.toString(c.getTimeInMillis());

        //check if a week will be in this month or next
        if (!(c.getTime().getMonth() == months.get(monthYear[0]))) {
            //switch on next month
            $("div.calendar__arrow_direction_right[data-step='1']").click();
        }

        ElementsCollection col = $$("td.calendar__day"); //find all days
        //try to find day 7 day after today
        for(int i = 0; i < col.size(); i++) {
            String dayFromCalendar = col.get(i).getAttribute("data-day");

            if (dayFromCalendar != null) {
                if (dayFromCalendar.equals(oneWeekAfterToday)) {
                    col.get(i).click();
                }
            }
        }

        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//div[contains(text(), 'Успешно!')]").should(appear, Duration.ofSeconds(15));
    }
}
