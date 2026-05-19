package api.tests;

import api.assertions.BuyerAssertions;
import api.clients.BuyerAPIClient;
import api.dto.BaseGetAllResponse;
import api.dto.Lot;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

// just an example of how API tests can be used in the same project
@DisplayName("Search API Tests")
public class SearchAPITests {

    private final BuyerAPIClient client = new BuyerAPIClient();
    private final BuyerAssertions assertions = new BuyerAssertions();

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Should be able to search lots with valid query")
    @Tags({ @Tag("functional"), @Tag("api")})
    @Test
     public void shouldSearchLotsWithValidQuery() {
         String query = "train";
         BaseGetAllResponse<Lot> searchResponse = client.searchLots(query);

         assertions.searchResponseShouldContainLots(searchResponse);
         assertions.searchResponseLotsShouldContainQuery(query, searchResponse);
     }

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Should be able to search lots with query containing Unicode characters")
    @Tags({ @Tag("functional"), @Tag("api")})
    @Test
    public void shouldSearchLotsWithUnicodeQuery() {
        String query = "列车";
        String translatedQuery = "train";
        BaseGetAllResponse<Lot> searchResponse = client.searchLots(query);

        assertions.searchResponseShouldContainLots(searchResponse);
        assertions.searchResponseLotsShouldContainQuery(translatedQuery, searchResponse);
    }

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Should be able to search lots with long query")
    @Tags({ @Tag("functional"), @Tag("api")})
    @Test
    public void shouldSearchLotsWithLongQuery() {
        String query = "star wars ";
        BaseGetAllResponse<Lot> searchResponse = client.searchLots(query.repeat(500));

        assertions.searchResponseShouldContainLots(searchResponse);
        assertions.searchResponseLotsShouldContainQuery(query, searchResponse);
    }

    @Severity(SeverityLevel.MINOR)
    @DisplayName("Should be able to search lots with empty query")
    @Description("Verifies that searching with empty query does not cause server errors or crashes and doesn't return giant unrelated dataset")
    @Tags({ @Tag("functional"), @Tag("api")})
    @Test
    public void shouldSearchLotsWithEmptyQuery() {
        String query = "";
        BaseGetAllResponse<Lot> searchResponse = client.searchLots(query);

        assertions.searchResponseShouldBeEmpty(searchResponse);
    }
}
