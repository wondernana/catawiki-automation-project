package ui.pages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import ui.elements.Header;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SearchResultsPage {

    private final Header header = new Header();

    // finds all elements with data-testid starting with 'lot-card-container-' (with the lot id cut off)
    private final ElementsCollection lotCards = $$("[data-testid^='lot-card-container-']");

    public Header header() {
        return header;
    }

    @Step("Wait for search results page to load")
    public SearchResultsPage waitForSearchResultsPageToLoad() {
        lotCards.first().shouldBe(visible);
        return this;
    }

    @Step("Open lot number [{lotNumber}] from search results")
    public LotPage openLotNumber(int lotNumber) {
        // it would be more natural to use 1-based lot numbers in tests, instead of 0-based collection indices
        int index = lotNumber - 1;
        lotCards.get(index).shouldBe(visible).click();
        return new LotPage();
    }

    @Step("Verify that search results display at least one lot card")
    public SearchResultsPage shouldDisplayLotCards() {
        lotCards.shouldHave(sizeGreaterThan(0));
        lotCards.forEach(card -> card.shouldBe(visible));
        return this;
    }

    @Step("Verify that lot cards from the first page of search results contain [{text}]")
    public SearchResultsPage lotCardsShouldContain(String text) {
        lotCards.forEach(lotCard -> lotCard.shouldHave(visible, text(text)));
        return this;
    }
}
