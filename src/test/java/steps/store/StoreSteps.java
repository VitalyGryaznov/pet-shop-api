package steps.store;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static endpoints.StoreUrl.*;
import static io.restassured.RestAssured.given;

public class StoreSteps {
    @Step("Place an order for a pet " + PLACE_ORDER)
    public static Response placePetOrder(RequestSpecification spec, String body) {
        return given().spec(spec)
                .body(body)
                .when().post(PLACE_ORDER)
                .then().extract().response();
    }

    @Step("Find purchase order by ID " + FIND_ORDER_ID)
    public static Response findPurchase(RequestSpecification spec, String orderId) {
        String requestUrl = FIND_ORDER_ID.replace("{orderId}", orderId);

        return given().spec(spec)
                .when().get(requestUrl)
                .then().extract().response();
    }

    @Step("Delete purchase order by ID " + FIND_ORDER_ID)
    public static Response deletePurchase(RequestSpecification spec, String orderId) {
        String requestUrl = FIND_ORDER_ID.replace("{orderId}", orderId);

        return given().spec(spec)
                .when().delete(requestUrl)
                .then().extract().response();
    }

    @Step("Returns pet inventories by status " + PET_INVENTORIES)
    public static Response getPetInventories(RequestSpecification spec) {
        return given().spec(spec)
                .when().get(PET_INVENTORIES)
                .then().extract().response();
    }
}
