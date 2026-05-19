package ui.elements;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;

public class SearchBar {

    private final String searchFieldLocator = "[data-testid='search-field']";
    private final String searchButtonLocator = "[data-testid='text-field-prefix']";
    private final String clearSearchButtonLocator = "[data-testid='search-clear-button']";

    private final SelenideElement mainWrapper = $("[class*='search__main']");
    private final SelenideElement mobileWrapper = $("[class*='search-form']");

    private final SelenideElement searchField = mainWrapper.$(searchFieldLocator);
    private final SelenideElement searchButton = mainWrapper.$(searchButtonLocator);
    private final SelenideElement clearSearchButton = mainWrapper.$(clearSearchButtonLocator);

    // long-term I would probably refactor this to have separate page objects & elements for desktop and mobile
    // but for the sake of this task I kept them in the same class
    private final SelenideElement mobileSearchField = mobileWrapper.$(searchFieldLocator);
    private final SelenideElement mobileSearchButton = mobileWrapper.$(searchButtonLocator);

    private final SelenideElement clearRecentSearchButton = $("[data-testid='clear-recent-search']");

    private final ElementsCollection autocompleteSuggestions = $$("[class*='c-search-field__option']");

    @Step("Enter [{query}] in the search field")
    public void enterQuery(String query) {
        searchField.shouldBe(interactable).setValue(query);
        searchField.shouldHave(value(query));
    }

    @Step("Mobile View: Enter [{query}] in the search field")
    public void enterQueryOnMobile(String query) {
        mobileSearchField.shouldBe(interactable).setValue(query);
        mobileSearchField.shouldHave(value(query));
    }

    @Step("Click in the search field and wait for autocomplete options")
    public void openAutocomplete() {
        searchField.shouldBe(interactable).click();
        autocompleteSuggestions.shouldHave(sizeGreaterThan(0));
    }

    @Step("Verify autocomplete suggestions are shown in the dropdown list")
    public void shouldHaveAutocompleteSuggestions() {
        autocompleteSuggestions.shouldHave(sizeGreaterThan(0));
    }

    @Step("Verify autocomplete suggestions contain text [{text}]")
    public void shouldHaveAutocompleteSuggestionsContaining(String text) {
        autocompleteSuggestions.findBy(text(text)).shouldBe(visible);
    }

    @Step("Verify autocomplete suggestion [{suggestion}] is not shown in the dropdown list")
    public void shouldNotIncludeAutocompleteSuggestion(String suggestion) {
        autocompleteSuggestions.find(text(suggestion)).shouldNotBe(visible);
    }

    @Step("Click on search icon in the search field")
    public void clickSearchButton() {
        searchButton.shouldBe(interactable).click();
    }

    @Step("Mobile View: Click on search icon in the search field")
    public void clickSearchButtonOnMobile() {
        mobileSearchButton.shouldBe(interactable).click();
    }

    @Step("Press Enter in the search field")
    public void pressEnter() {
        searchField.pressEnter();
    }

    @Step("Click on autocomplete suggestion containing text: [{suggestion}]")
    public void clickOnAutocompleteSuggestionWithText(String suggestion) {
        autocompleteSuggestions.find(text(suggestion)).shouldBe(interactable).click();
        autocompleteSuggestions.shouldHave(size(0));
    }

    @Step("Clear search field")
    public void clearSearch() {
        clearSearchButton.shouldBe(interactable).click();
        searchField.shouldHave(value(""));
    }

    @Step("Clear recent search history")
    public void clearRecentSearchHistory() {
        clearRecentSearchButton.shouldBe(interactable).click();
        clearRecentSearchButton.shouldNotBe(visible);
    }
}
