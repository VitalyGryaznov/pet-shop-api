package tests.user;

import io.qameta.allure.Link;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.AbstractTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static steps.user.UserSteps.createUser;

public class CreateUserTests extends AbstractTest {

    @DisplayName("Check the functionality of creating user")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void creatingUserTest() {
        String body = """
                {
                  "id": 3,
                  "username": "Mike_123",
                  "firstName": "Mike",
                  "lastName": "Wright",
                  "email": "mikeWright123@gmail.com",
                  "password": "mikeWright123",
                  "phone": "010-125-146",
                  "userStatus": 0
                }
                """;

        var response = createUser(spec, body);

        assertThat(response.getStatusCode())
                .as("Status-code is different from expected")
                .isEqualTo(200);
    }

    @DisplayName("Check the functionality of creating user - negative case (invalid id)")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void creatingUserInvalidIdTest() {
        String body = """
                {
                  "id": @#$%^&,
                  "username": "jasper_123",
                  "firstName": "jasper",
                  "lastName": "smith",
                  "email": "jasper123@gmail.com",
                  "password": "jasper123",
                  "phone": "010-9753-0387",
                  "userStatus": 0
                }
                """;

        var response = createUser(spec, body);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode())
                    .as("Status-code is different from expected")
                    .isEqualTo(400);
            softly.assertThat(response.jsonPath().getString("message"))
                    .as("Field 'message' is different from expected").isEqualTo("bad input");
        });
    }
}
