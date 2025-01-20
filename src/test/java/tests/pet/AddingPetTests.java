package tests.pet;

import io.qameta.allure.Link;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.AbstractTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static steps.pet.PetSteps.addPet;
import static steps.pet.PetSteps.findPetById;
import models.Pet;
import models.Category;
import models.Tag;

import java.util.List;


public class AddingPetTests extends AbstractTest {

    @DisplayName("After creating pet I am able to get it by id  with correct details")
    @Link(name = "Specification", url = "https://petstore.swagger.io/#/")
    @Test
    public void createPetAndVerifyItWasCreated() {
        // TODO: need to use getters, setters and constructor instead.
        Pet pet = new Pet();
        pet.id = 777;
        pet.category = new Category();
        pet.category.name = "small dogs";
        pet.category.id = 555;
        pet.name = "Rosi";
        pet.photoUrls = List.of("http://mysite.com/mydog.jpg");

        Tag tag1 = new Tag();
        tag1.name = "tag1";
        Tag tag2 = new Tag();
        tag2.name = "tag2";

        pet.tags = List.of(tag1, tag2);
        pet.status = "available";

        var createPetResponse = addPet(spec, pet);
        createPetResponse.then().statusCode(200);

        String petId = createPetResponse.jsonPath().getString("id");

        var getPetResponse = findPetById(spec, petId);
        getPetResponse.then().statusCode(200);

        Pet actualPet = getPetResponse.as(Pet.class);
        assertThat(actualPet).usingRecursiveComparison().isEqualTo(pet);
    }
}
