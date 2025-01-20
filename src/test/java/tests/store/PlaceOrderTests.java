package tests.store;

import io.qameta.allure.Link;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.AbstractTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static steps.store.StoreSteps.placePetOrder;

public class PlaceOrderTests extends AbstractTest {

    @DisplayName("Check the functionality of placing an order for a pet")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void placingOrderTest() {
        String body = """
                {
                  "id": 25,
                  "petId": 23,
                  "quantity": 1,
                  "shipDate": "2023-12-13T17:28:47.116Z",
                  "status": "placed",
                  "complete": true
                }
                """;

        var response = placePetOrder(spec, body);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode())
                    .as("Status-code is different from expected")
                    .isEqualTo(200);
            softly.assertThat(response.jsonPath().getString("status"))
                    .as("Field 'status' is different from expected").isEqualTo("placed");
        });
    }

    @DisplayName("Check the functionality of placing an order for a pet - negative case (invalid Id)")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void placingOrderInvalidIdTest() {
        String body = """
                {
                  "id": !(@*#!,
                  "petId": 34,
                  "quantity": 4,
                  "shipDate": "2023-12-13T17:28:47.116Z",
                  "status": "placed",
                  "complete": true
                }
                """;

        var response = placePetOrder(spec, body);

        assertThat(response.getStatusCode())
                .as("Status-code is different from expected")
                .isEqualTo(400);
    }
}
