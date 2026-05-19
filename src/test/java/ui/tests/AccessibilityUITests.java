package ui.tests;

import com.deque.html.axecore.results.Results;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.*;
import utils.CommonUISteps;

@DisplayName("Accessibility Tests")
public class AccessibilityUITests {

    @Severity(SeverityLevel.MINOR)
    @DisplayName("Home page should comply with WCAG AA accessibility standards")
    @Description("Example of analyzing pages for accessibility violations using WCAG AA standards." +
            "Currently pages do not meet those standards, so tests are failing")
    @Tags({ @Tag("accessibility"), @Tag("ui")})
    @Test
    public void homePageShouldComplyWithAccessibilityStandards() {
        CommonUISteps.openHomePage();
        Results axeResults = CommonUISteps.analyzePageAccessibilityForWcagAA();
        CommonUISteps.pageShouldNotHaveWcagViolations(axeResults);
    }

    @Severity(SeverityLevel.MINOR)
    @DisplayName("Search results page should comply with WCAG AA accessibility standards")
    @Description("Example of analyzing pages for accessibility violations using WCAG AA standards." +
            "Currently pages do not meet those standards, so tests are failing")
    @Tags({ @Tag("accessibility"), @Tag("ui")})
    @Test
    public void searchResultsPageShouldComplyWithAccessibilityStandards() {
        CommonUISteps.openSearchResultsPageForQuery("batman");
        Results axeResults = CommonUISteps.analyzePageAccessibilityForWcagAA();
        CommonUISteps.pageShouldNotHaveWcagViolations(axeResults);
    }
}
