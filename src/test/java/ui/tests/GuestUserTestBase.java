package ui.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.CommonUISteps;
import ui.elements.CookieBanner;
import utils.AllureAttachmentManager;

import static com.codeborne.selenide.Selenide.*;
import static config.OwnerConfig.CONFIG;

// doesn't include login setup
public abstract class GuestUserTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(GuestUserTestBase.class);
    // max time that Selenide would wait for an element to appear before trying to interact with it
    // can also be set in config, but since it can be different for different types of tests I set it here
    private static final long defaultTimeoutMs = 15000;

    @Step("Pre-condition: browser setup and open home page")
    @BeforeEach
    public void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.headless = CONFIG.isHeadlessBrowser();
        Configuration.browser = CONFIG.getBrowserType();
        Configuration.timeout = defaultTimeoutMs;

        // safari has some quirks that don't apply to other browsers, so it needs separate setup
        if (Configuration.browser.equals("safari")) {
            setUpSafari();
        }

        CommonUISteps.openHomePage().waitForHomePageToLoad();
        new CookieBanner().acceptAllCookies();
    }

    @AfterEach
    public void tearDown() {
        addAttachments();
        closeWebDriver();
    }

    private void setUpSafari() {
        SafariOptions options = new SafariOptions();
        // optional properties that may help to avoid some safari-related issues
        options.setCapability("safari:diagnose", false);
        options.setCapability("safari:automaticInspection", false);
        options.setCapability("safari:automaticProfiling", false);

        // safari doesn't support headless mode
        Configuration.headless = false;
        Configuration.browserCapabilities = options;

        // safari doesn't support executing tests in parallel
        System.setProperty("junit.jupiter.execution.parallel.enabled", "false");

        // safari needs a "warm up" - opening a blank page before the driver can be fully used
        try {
            Selenide.open("about:blank");
        } catch (org.openqa.selenium.SessionNotCreatedException e) {
            LOG.info("Warm-up for SafariDriver");
        }
    }

    private void addAttachments() {
        // adds last screenshot, page source & console logs to test report for better visibility/easier debugging
        AllureAttachmentManager.addScreenshotAs("Last screenshot");
        AllureAttachmentManager.addPageSource();

        // browser console logs can only be retrieved from chrome & edge
        if(Configuration.browser.equals("chrome") || Configuration.browser.equals("edge")) {
            AllureAttachmentManager.addBrowserConsoleLogs();
        }
    }
}
