package utils;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;

import java.nio.charset.StandardCharsets;

import static org.openqa.selenium.logging.LogType.BROWSER;

// used to add different attachments to test report
// can be extended to add videos of test runs as well
public class AllureAttachmentManager {

    public static void addBrowserConsoleLogs() {
        addMessage("Browser console logs", String.join("\n", Selenide.getWebDriverLogs(BROWSER)));
    }

    @Attachment(value = "{name}", type = "image/png")
    // screenshots on test failure are handled automatically by Allure
    // but this method can be used to add screenshots at any point of the test (e.g. after some important step)
    public static byte[] addScreenshotAs(String name) {
        return Selenide.screenshot(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/html")
    public static byte[] addPageSource() {
        return Selenide.webdriver().object().getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "{name}", type = "text/plain")
    private static String addMessage(String name, String text) {
        return text;
    }
}
