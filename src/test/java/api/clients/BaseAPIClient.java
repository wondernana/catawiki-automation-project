package api.clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static config.OwnerConfig.CONFIG;
import static utils.AllureAPIFilter.customAllureFilter;

public class BaseAPIClient {

    protected RequestSpecification requestSpec;

    public BaseAPIClient(String authToken) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(CONFIG.getBaseUrl())
                .setContentType(ContentType.JSON)
                .addHeader("User-Agent", "PostmanRuntime/7.44.1")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addFilter(customAllureFilter().withCustomTemplates());

        if (authToken != null) {
            builder.addHeader("Authorization", authToken);
        }

        this.requestSpec = builder.build();
    }
}
