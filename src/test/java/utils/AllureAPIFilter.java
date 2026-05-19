package utils;

import io.qameta.allure.restassured.AllureRestAssured;

// creates better reports for API calls by attaching request & response details in a more readable format using custom templates
public class AllureAPIFilter {
    private static final AllureRestAssured FILTER = new AllureRestAssured();

    private AllureAPIFilter() {}

    public static AllureAPIFilter customAllureFilter() {
        return InitFilter.customAllureFilter;
    }

    public AllureRestAssured withCustomTemplates() {
        FILTER.setRequestTemplate("request.ftl");
        FILTER.setResponseTemplate("response.ftl");
        return FILTER;

    }

    private static class InitFilter {
        private static final AllureAPIFilter customAllureFilter = new AllureAPIFilter();
    }
}
