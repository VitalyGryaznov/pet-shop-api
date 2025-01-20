package tests.store;

import io.qameta.allure.Link;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.AbstractTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static steps.store.StoreSteps.findPurchase;
import static steps.store.StoreSteps.placePetOrder;

public class FindPurchaseTests extends AbstractTest {

    @DisplayName("Check the functionality of finding purchase order by ID")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void findingPurchaseByIdTest() {
        String body = """
                {
                  "id": 23,
                  "petId": 34,
                  "quantity": 1,
                  "shipDate": "2023-12-13T17:28:47.116Z",
                  "status": "placed",
                  "complete": true
                }
                """;

        var placingOrder = placePetOrder(spec, body);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(placingOrder.getStatusCode())
                    .as("Status-code is different from expected")
                    .isEqualTo(200);
            softly.assertThat(placingOrder.jsonPath().getString("status"))
                    .as("Field 'status' is different from expected").isEqualTo("placed");
        });

        String orderId = placingOrder.jsonPath().getString("id");

        var response = findPurchase(spec, orderId);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode())
                    .as("Status-code is different from expected")
                    .isEqualTo(200);
            softly.assertThat(response.body())
                    .as("Response body is null").isNotNull();
            softly.assertThat(response.jsonPath().getString("id"))
                    .as("Purchase id is different from expected").isEqualTo(orderId);
        });
    }

    @DisplayName("Check the functionality of finding purchase order by ID - negative case (id not found)")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void findingPurchaseIdNotFoundTest() {
        var response = findPurchase(spec, "123568993");

        assertThat(response.getStatusCode())
                .as("Status-code is different from expected")
                .isEqualTo(404);
    }
}
