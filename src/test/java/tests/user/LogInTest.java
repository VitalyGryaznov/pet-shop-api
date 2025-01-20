package tests.user;

import io.qameta.allure.Link;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.AbstractTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static steps.user.UserSteps.*;

public class LogInTest extends AbstractTest {

    @DisplayName("Check the functionality of logging user into the system")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void loginUserTest() {
        String body = """
                {
                  "id": 230,
                  "username": "igor_123",
                  "firstName": "igor",
                  "lastName": "davis",
                  "email": "igor123@gmail.com",
                  "password": "igor123",
                  "phone": "010-8405-4532",
                  "userStatus": 0
                }
                """;

        var creatingUser = createUser(spec, body);

        assertThat(creatingUser.getStatusCode())
                .as("Status-code is different from expected")
                .isEqualTo(200);

        var response = logIn(spec, "igor_123", "igor123");

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode())
                    .as("Status-code is different from expected")
                    .isEqualTo(200);
            softly.assertThat(response.jsonPath().getString("message"))
                    .as("Message is different from expected").contains("logged in user session");
            softly.assertThat(response.getHeader("X-Expires-After"))
                    .as("X-Expires-After header is missing")
                    .isNotNull();
            softly.assertThat(response.getHeader("X-Rate-Limit"))
                    .as("X-Rate-Limit header is missing")
                    .isNotNull();
        });
    }
}
