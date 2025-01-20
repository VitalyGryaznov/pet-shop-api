package tests.store;

import io.qameta.allure.Link;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.AbstractTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static steps.store.StoreSteps.deletePurchase;
import static steps.store.StoreSteps.placePetOrder;

public class DeleteOrderTests extends AbstractTest {

    @DisplayName("Check the functionality of deleting an order")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void deletingOrderTest() {
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

        var deletingOrder = deletePurchase(spec, orderId);

        assertThat(deletingOrder.getStatusCode())
                .as("Status-code is different from expected")
                .isEqualTo(200);
    }

    @DisplayName("Check the functionality of deleting an order twice- negative case")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void deletingOrderTwiceTest() {
        String body = """
                {
                  "id": 56,
                  "petId": 689,
                  "quantity": 3,
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

        var deletingOrder = deletePurchase(spec, orderId);

        assertThat(deletingOrder.getStatusCode())
                .as("Status-code is different from expected")
                .isEqualTo(200);

        var deletingOrderTwice = deletePurchase(spec, orderId);

        assertThat(deletingOrderTwice.getStatusCode())
                .as("Status-code is different from expected")
                .isEqualTo(404);
    }
}
