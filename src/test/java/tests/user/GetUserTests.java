package tests.user;

import io.qameta.allure.Link;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.AbstractTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static steps.user.UserSteps.createUser;
import static steps.user.UserSteps.getUser;

public class GetUserTests extends AbstractTest {

    @DisplayName("Check the functionality of getting user by user name")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void gettingUserByNameTest() {
        String body = """
                {
                  "id": 3,
                  "username": "anna_123",
                  "firstName": "anna",
                  "lastName": "brown",
                  "email": "annaBrown123@gmail.com",
                  "password": "annaBrown123",
                  "phone": "010-1987-146",
                  "userStatus": 0
                }
                """;

        var creatingUser = createUser(spec, body);

        assertThat(creatingUser.getStatusCode())
                .as("Status-code is different from expected")
                .isEqualTo(200);

        var gettingUser = getUser(spec, "anna_123");

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(gettingUser.getStatusCode())
                    .as("Status-code is different from expected")
                    .isEqualTo(200);
            softly.assertThat(gettingUser.jsonPath().getString("id"))
                    .as("Id is different from expected").isNotNull();
        });
    }

    @DisplayName("Check the functionality of getting user by user name - negative case (user does not exist)")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void gettingNonexistentUserByNameTest() {
        var response = getUser(spec, "userName");

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode())
                    .as("Status-code is different from expected")
                    .isEqualTo(404);
            softly.assertThat(response.jsonPath().getString("message"))
                    .as("Message is different from expected").isEqualTo("User not found");
        });
    }
}
