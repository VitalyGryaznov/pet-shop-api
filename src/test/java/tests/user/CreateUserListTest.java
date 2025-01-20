package tests.user;

import io.qameta.allure.Link;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.AbstractTest;

import static steps.user.UserSteps.createUserList;

public class CreateUserListTest extends AbstractTest {

    @DisplayName("Check the functionality of creating list of users with given input array")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void creatingUserListWithArrayTest() {
        String body = """
                [
                  {
                    "id": 1,
                    "username": "natalia_123",
                    "firstName": "natalia",
                    "lastName": "kim",
                    "email": "natalia123@gmail.com",
                    "password": "nataliakim123",
                    "phone": "010-123-123",
                    "userStatus": 0
                  },
                  {
                    "id": 2,
                    "username": "alexey_123",
                    "firstName": "alexey",
                    "lastName": "nosov",
                    "email": "alexey123@gmail.com",
                    "password": "alexey123",
                    "phone": "010-123-1234",
                    "userStatus": 0
                  }
                ]
                   
                """;

        var response = createUserList(spec, body);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode())
                    .as("Status-code is different from expected")
                    .isEqualTo(200);
            softly.assertThat(response.jsonPath().getString("message"))
                    .as("Message is different from expected").isEqualTo("ok");
        });
    }
}
