package ui.tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.CommonUISteps;
import ui.pages.HomePage;
import ui.pages.LotPage;
import ui.pages.SearchResultsPage;

import java.util.stream.Stream;

@DisplayName("Search UI Tests")
public class SearchUITests extends GuestUserTestBase {

    // used as an example of how the same test can be extended for searching different items and opening different lot numbers
    // (or potentially randomizing those values)
    private static Stream<Arguments> searchConfigProvider() {
        return Stream.of(
                // scenario from the task
                Arguments.of("train", 2),
                // same values, checking for case insensitivity
                Arguments.of("TRAIN", 2),
                // different values
                Arguments.of("pokemon cards", 1)
        );
    }

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Should be able to search for an item and open a lot from search results")
    // tags can be used to group tests and run specific groups when needed (as well as for reporting purposes)
    @Tags({ @Tag("functional"), @Tag("ui")})
    @MethodSource("searchConfigProvider")
    @ParameterizedTest(name = "(search for {0} and open lot {1} from search results)")
    public void shouldSearchAndOpenLotFromSearchResults(String query, int lotNumber) {
        HomePage homePage = new HomePage();
        SearchResultsPage searchResultsPage = homePage
                .header()
                .searchByClickingIcon(query);

        LotPage lotPage = searchResultsPage
                .shouldDisplayLotCards()
                .openLotNumber(lotNumber);

        lotPage
                .shouldDisplayLotTitle()
                .shouldDisplayFavoritesCount()
                .shouldDisplayCurrentBid()
                .shouldDisplayTimeTillAuctionClosed();
    }

    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Should be able to search for an item on mobile screens")
    @Tags({ @Tag("functional"), @Tag("ui")})
    @Test
    // search behavior is slightly different on mobile screens, so can't reuse the same search tests for both desktop and mobile
    public void shouldOpenAndUseSearchInMobileView() {
        //screen sizes can be parametrized as well, using the most common mobile screen size as an example
        CommonUISteps.setMobileViewport(360, 800);
        String query = "vintage";

        HomePage homePage = new HomePage();
        SearchResultsPage searchResultsPage = homePage
                .header()
                .openMobileSearch()
                .searchByClickingIconOnMobile(query);

        searchResultsPage.shouldDisplayLotCards();
    }

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Should be able to re-open search results from recent searches history")
    @Tags({ @Tag("functional"), @Tag("ui")})
    @Test
    public void shouldReOpenResultsFromRecentSearch() {
        // not parametrizing this test, since the query itself shouldn't matter in this scenario
        String query = "vintage";

        HomePage homePage = new HomePage();
        homePage
                .header()
                .searchByPressingEnter(query);

        CommonUISteps.goBack();

        SearchResultsPage searchResultsPage = homePage
                .waitForHomePageToLoad()
                .header()
                .selectOptionFromAutocompleteWithoutSearch(query);

        searchResultsPage.shouldDisplayLotCards();
    }

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Should be able to use autocomplete suggestions when searching for an item")
    @Tags({ @Tag("functional"), @Tag("ui")})
    @Test
    public void shouldAutocompleteUserQuery() {
        String partialQuery = "van g";
        String expectedAutocompleteOption = "van gogh";

        HomePage homePage = new HomePage();
        SearchResultsPage searchResultsPage = homePage
                .header()
                .searchBySelectingFromAutocomplete(partialQuery, expectedAutocompleteOption);

        searchResultsPage.shouldDisplayLotCards();
    }
}
