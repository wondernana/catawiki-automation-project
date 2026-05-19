package api.clients;

import api.dto.BaseGetAllResponse;
import api.dto.Lot;
import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class BuyerAPIClient extends BaseAPIClient {

    private final String basePath = "/buyer/api/v1/";

    // for guest users
    public BuyerAPIClient() {
        super(null);
        requestSpec.basePath(basePath);
    }

    // for logged-in users
    public BuyerAPIClient(String authToken) {
        super(authToken);
        requestSpec.basePath(basePath);
    }

    @Step("GET /search, expect status code 200 and valid response body")
    public BaseGetAllResponse<Lot> searchLots(String query) {
        return given()
                .spec(requestSpec)
                .queryParam("q", query)
                .get("/search")
                .then()
                .statusCode(SC_OK)
                .extract()
                .response()
                .as(new TypeRef<BaseGetAllResponse<Lot>>() {});
    }
}
