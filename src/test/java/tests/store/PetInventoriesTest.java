package tests.store;

import io.qameta.allure.Link;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.AbstractTest;

import static steps.store.StoreSteps.getPetInventories;

public class PetInventoriesTest extends AbstractTest {
    @DisplayName("Check the functionality of getting pet inventories by status")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void gettingPetInventoryTest() {
        var response = getPetInventories(spec);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode())
                    .as("Status-code is different from expected")
                    .isEqualTo(200);
            softly.assertThat(response.body())
                    .as("Response body is null").isNotNull();
        });
    }
}
