package tests.user;

import io.qameta.allure.Link;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.AbstractTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static steps.user.UserSteps.*;

public class UpdateUserTests extends AbstractTest {

    @DisplayName("Check the functionality of updating a user")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void updatingUserTest() {
        String body = """
                {
                  "id": 1,
                  "username": "natalia_1234",
                  "firstName": "natalia",
                  "lastName": "kim",
                  "email": "natalia1234@gmail.com",
                  "password": "nataliakim1234",
                  "phone": "010-1234-1234",
                  "userStatus": 0
                }
                """;

        var response = updateUser(spec, body, "natalia_1234");

        assertThat(response.getStatusCode())
                .as("Status-code is different from expected")
                .isEqualTo(200);
    }

    @DisplayName("Check the functionality of updating a user - negative case (invalid id)")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void updatingUserInvalidIdTest() {
        String body = """
                {
                  "id": !@#$%^&^%$#@!,
                  "username": "natalia_1234",
                  "firstName": "natalia",
                  "lastName": "kim",
                  "email": "natalia1234@gmail.com",
                  "password": "nataliakim1234",
                  "phone": "010-1234-1234",
                  "userStatus": 0
                }
                """;

        var response = updateUser(spec, body, "natalia_123");

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode())
                    .as("Status-code is different from expected")
                    .isEqualTo(400);
            softly.assertThat(response.jsonPath().getString("message"))
                    .as("Field 'message' is different from expected").isEqualTo("bad input");
        });
    }
}
