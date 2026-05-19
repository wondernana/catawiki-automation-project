package ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import ui.elements.Header;

import java.util.logging.Logger;

import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LotPage {

    private final Header header = new Header();

    private final SelenideElement lotTitle = $("h1");

    //data-sentry-component locators are not ideal, since they are coupled to the use of sentry, but I thought it was a better option than using complicated xpath
    private final SelenideElement lotFavoritesButton = $("[data-sentry-component='LotDetailsFavoriteButton']").find("button");

    private final SelenideElement bidStatusSection = $("[data-testid='lot-bid-status-section']");
    private final SelenideElement currentBidAmount = bidStatusSection.$("[data-sentry-component='Amount']");

    private final SelenideElement timeTillClosed = $("[data-testid='timer-countdown']");

    private static final Logger LOG = Logger.getLogger("Lot Page");

    private static final String NOT_A_DIGIT_REGEX = "[^\\d]";
    // matches values like "2d 5h 30m 10s" (but doesn't validate that the numbers are in correct ranges for days/hours/minutes/seconds)
    // also assumes that auctions do not last for months
    private static final String TIMER_COUNTDOWN_REGEX = "\\d+d \\d+h \\d+m \\d+s";

    public Header header() {
        return header;
    }

    @Step("Verify that lot title is displayed and not empty")
    public LotPage shouldDisplayLotTitle() {
        // added this logging only because it was a part of the task
        LOG.info("Lot name: " + lotTitle.getText());

        lotTitle.shouldBe(visible);

        String titleText = lotTitle.getText();
        assertThat(titleText, not(emptyString()));

        return this;
    }

    @Step("Verify that favorites counter is displayed and contains a number greater than or equal to 0")
    public LotPage shouldDisplayFavoritesCount() {
        LOG.info("Favorites counter: " + lotFavoritesButton.getText());

        lotFavoritesButton.shouldBe(visible);

        int favorites = Integer.parseInt(lotFavoritesButton.getText());
        assertThat(favorites, is(greaterThanOrEqualTo(0)));

        return this;
    }

    @Step("Verify that current bid is displayed and contains a number greater than or equal to 0")
    public LotPage shouldDisplayCurrentBid() {
        LOG.info("Current bid: " + currentBidAmount.getText());

        currentBidAmount.shouldBe(visible);

        // removes all characters that are not numbers (e.g. currency symbols, commas, spaces)
        String numericText = currentBidAmount.getText().replaceAll(NOT_A_DIGIT_REGEX, "");

        int amount = Integer.parseInt(numericText);
        assertThat(amount, is(greaterThanOrEqualTo(0)));

        return this;
    }

    @Step("Verify that time till auction closed is displayed and in correct format")
    public LotPage shouldDisplayTimeTillAuctionClosed() {
        timeTillClosed.shouldBe(visible).shouldHave(matchText(TIMER_COUNTDOWN_REGEX));

        return this;
    }
}
