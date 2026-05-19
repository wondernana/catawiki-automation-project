package ui.elements;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class CookieBanner {

    private final SelenideElement shadowHost = $("#usercentrics-cmp-ui");
    private final String acceptButtonSelector = "#accept";

    @Step("Accept all cookies")
    public void acceptAllCookies() {
        // can't interact with shadow DOM with Selenide, so using Selenium directly here
        WebElement acceptAllButton = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(5))
                .until(driver -> cookieBannerShadowRoot().findElement(By.cssSelector(acceptButtonSelector)));
        $(acceptAllButton).click();

        shadowHost.should(disappear);
    }

    private SearchContext cookieBannerShadowRoot() {
        shadowHost.should(exist);
        return shadowHost.getShadowRoot();
    }
}
