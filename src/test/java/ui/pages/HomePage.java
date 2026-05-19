package ui.pages;

import io.qameta.allure.Step;
import ui.elements.Header;

public class HomePage {

    private final Header header = new Header();
    // other elements from the home page

    public Header header() {
        return header;
    }

    @Step("Wait for Home page to load")
    public HomePage waitForHomePageToLoad() {
        header.waitForHeaderToLoad();
        return this;
    }
}
