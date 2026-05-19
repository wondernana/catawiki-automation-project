package api.assertions;

import api.dto.BaseGetAllResponse;
import api.dto.Lot;
import io.qameta.allure.Step;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuyerAssertions {

    @Step("Verify that search response contains lots")
    public void searchResponseShouldContainLots(BaseGetAllResponse<Lot> response) {
        assertThat(response.total(), is(greaterThan(0)));
        assertThat(response.data(), is(not(empty())));
    }

    @Step("Verify that search response contains at least one lot directly mentioning the search query [{query}]")
    public void searchResponseLotsShouldContainQuery(String query, BaseGetAllResponse<Lot> response) {
        // not strictly validating that all lot titles contain query in their title, since that's unlikely to be the only criteria
        assertTrue(response.data().stream()
                .anyMatch(lot -> lot.title().toLowerCase().contains(query.toLowerCase())),
                "Expected at least one lot title to contain the search query:" + query);
    }

    @Step("Verify that search response is empty")
    public void searchResponseShouldBeEmpty(BaseGetAllResponse<Lot> response) {
        assertThat(response.total(), is(equalTo(0)));
        assertThat(response.data(), is(empty()));
    }
}
