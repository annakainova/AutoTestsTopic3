package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;

public class cardDeliveryTest {

    @Test
    public void sendFormSuccessfulTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        $("[data-test-id='name'] input").setValue("Иван Петров-Иванов");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button__text").click();
        $x("//div[contains(text(), 'Успешно!')]").should(appear, Duration.ofSeconds(15));
    }

}
