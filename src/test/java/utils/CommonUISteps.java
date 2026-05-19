package utils;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import io.qameta.allure.Step;
import org.openqa.selenium.Dimension;
import ui.pages.HomePage;
import ui.pages.LotPage;
import ui.pages.SearchResultsPage;

import java.util.Arrays;

import static com.codeborne.selenide.Selenide.open;
import static config.OwnerConfig.CONFIG;
import static org.junit.jupiter.api.Assertions.assertTrue;

// stores helper methods used across different UI tests/classes
public class CommonUISteps {

    @Step("Open home page")
    public static HomePage openHomePage() {
        return open(CONFIG.getBaseUrl() + CONFIG.getLocale(), HomePage.class);
    }

    @Step("Open search results page for [{query}]")
    public static SearchResultsPage openSearchResultsPageForQuery(String query) {
        // needs url-encoding to work with multiword queries
        return open(CONFIG.getBaseUrl() + CONFIG.getLocale() + "/s?q=" + query, SearchResultsPage.class);
    }

    @Step("Open lot page with id [{lotId}]")
    public static LotPage openLotPage(String lotId) {
        return open(CONFIG.getBaseUrl() + CONFIG.getLocale() +"/l/" + lotId, LotPage.class);
    }

    @Step("Go back to the previous page")
    public static void goBack() {
        Selenide.back();
    }

    @Step("Set screen size {width}x{height} to simulate mobile viewport")
    public static void setMobileViewport(int width, int height) {
        WebDriverRunner.getWebDriver()
                       .manage()
                       .window()
                       .setSize(new Dimension(width, height));
    }

    @Step("Analyze page against WCAG A and AA success criteria")
    public static Results analyzePageAccessibilityForWcagAA() {
        return new AxeBuilder()
                .withTags(Arrays.asList("wcag2a", "wcag2aa", "wcag21a", "wcag21aa"))
                .analyze(WebDriverRunner.getWebDriver());
    }

    @Step("Page should not have accessibility criteria violations")
    public static void pageShouldNotHaveWcagViolations(Results accessibilityScanResults) {
        assertTrue(accessibilityScanResults.getViolations().isEmpty(),
                "Expected no WCAG A/AA accessibility violations, but found: " + accessibilityScanResults.getViolations().size() +
                        "\nViolations details: \n\n" + accessibilityScanResults.getViolations());
    }
}
