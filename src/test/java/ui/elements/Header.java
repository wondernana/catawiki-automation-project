package ui.elements;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ui.pages.SearchResultsPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class Header {

    private final SearchBar searchBar = new SearchBar();

    private final SelenideElement helpButton = $("[data-testid='header-help-button']");
    private final SelenideElement favouritesButton = $("[data-testid='header-favorites-button']");

    private final SelenideElement openMobileSearchButton = $("[class*='mobile-nav__search']");

    // other elements from header

    @Step("Wait for the page header to load")
    public Header waitForHeaderToLoad() {
        helpButton.shouldBe(visible, clickable);
        favouritesButton.shouldBe(visible, clickable);
        return this;
    }

    @Step("Search for [{query}] via clicking search icon")
    public SearchResultsPage searchByClickingIcon(String query) {
        searchBar.enterQuery(query);
        searchBar.shouldHaveAutocompleteSuggestionsContaining(query);
        searchBar.clickSearchButton();

        return new SearchResultsPage();
    }

    @Step("Mobile View: Search for [{query}] via clicking search icon")
    public SearchResultsPage searchByClickingIconOnMobile(String query) {
        searchBar.enterQueryOnMobile(query);
        searchBar.shouldHaveAutocompleteSuggestionsContaining(query);
        searchBar.clickSearchButtonOnMobile();

        return new SearchResultsPage();
    }

    @Step("Search for [{query}] via pressing Enter")
    public SearchResultsPage searchByPressingEnter(String query) {
        searchBar.enterQuery(query);
        searchBar.shouldHaveAutocompleteSuggestionsContaining(query);
        searchBar.pressEnter();

        return new SearchResultsPage();
    }

    @Step("Enter [{query}] in the search field and select [{expectedAutocompleteOption}] from autocomplete suggestions")
    public SearchResultsPage searchBySelectingFromAutocomplete(String query, String expectedAutocompleteOption) {
        searchBar.enterQuery(query);
        searchBar.clickOnAutocompleteSuggestionWithText(expectedAutocompleteOption);

        return new SearchResultsPage();
    }

    @Step("Select option [{option}] from autocomplete without entering search query")
    public SearchResultsPage selectOptionFromAutocompleteWithoutSearch(String option) {
        searchBar.openAutocomplete();
        searchBar.clickOnAutocompleteSuggestionWithText(option);

        return new SearchResultsPage();
    }

    @Step("Clear search field")
    public Header clearSearchField() {
        searchBar.clearSearch();
        return this;
    }

    @Step("Open search on mobile by clicking the search icon")
    // search field is hidden on small screens initially
    public Header openMobileSearch() {
        openMobileSearchButton.shouldBe(visible).click();
        return this;
    }

    // other methods from header (clicking sign in, open favourites, navigate to user profile, main page etc.)
}
