package tests.user;

import io.qameta.allure.Link;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.AbstractTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static steps.user.UserSteps.*;

public class DeleteUserTests extends AbstractTest {

    @DisplayName("Check the functionality of deleting user")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void deletingUserTest() {
        String body = """
                {
                  "id": 56,
                  "username": "mark_123",
                  "firstName": "mark",
                  "lastName": "anderson",
                  "email": "mark123@gmail.com",
                  "password": "mark123",
                  "phone": "010-4069-4594",
                  "userStatus": 0
                }
                """;

        var creatingUser = createUser(spec, body);

        assertThat(creatingUser.getStatusCode())
                .as("Status-code is different from expected")
                .isEqualTo(200);

        var deletingUser = deleteUser(spec, "mark_123");

        assertThat(deletingUser.getStatusCode())
                .as("Status-code is different from expected")
                .isEqualTo(200);
    }

    @DisplayName("Check the functionality of deleting user twice - negative case")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void deletingUserTwiceTest() {
        String body = """
                {
                  "id": 87,
                  "username": "jane_123",
                  "firstName": "jane",
                  "lastName": "davis",
                  "email": "jane123@gmail.com",
                  "password": "jane123",
                  "phone": "010-8765-4594",
                  "userStatus": 0
                }
                """;

        var creatingUser = createUser(spec, body);

        assertThat(creatingUser.getStatusCode())
                .as("Status-code is different from expected")
                .isEqualTo(200);

        var deletingUser = deleteUser(spec, "jane_123");

        assertThat(deletingUser.getStatusCode())
                .as("Status-code is different from expected")
                .isEqualTo(200);

        var deletingUserTwice = deleteUser(spec, "jane_123");

        assertThat(deletingUserTwice.getStatusCode())
                .as("Status-code is different from expected")
                .isEqualTo(404);
    }
}
