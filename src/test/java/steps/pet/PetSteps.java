package steps.pet;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Pet;

import java.io.File;

import static endpoints.PetUrl.*;
import static io.restassured.RestAssured.given;

public class PetSteps {
    @Step("Uploading an image of a pet" + UPLOAD_IMAGE)
    public static Response uploadImage(RequestSpecification spec, String petId) {
        String requestUrl = UPLOAD_IMAGE.replace("{petId}", petId);

        return given().spec(spec)
                .multiPart(new File("src/test/resources/images/756723916815974.webp"))
                .header("Content-Type", "multipart/form-data")
                .when().post(requestUrl)
                .then().extract().response();
    }

    @Step("Adding a new pet to the store " + PET_ACTION)
    public static Response addPet(RequestSpecification spec, String body) {

        return given().spec(spec)
                .body(body)
                .when().post(PET_ACTION)
                .then().extract().response();
    }


    @Step("Adding a new pet to the store " + PET_ACTION)
    public static Response addPet(RequestSpecification spec, Pet body) {

        return given().spec(spec)
                .body(body)
                .when().post(PET_ACTION)
                .then().extract().response();
    }

    @Step("Updating an existing pet " + PET_ACTION)
    public static Response updatePet(RequestSpecification spec, String body) {

        return given().spec(spec)
                .body(body)
                .when().put(PET_ACTION)
                .then().extract().response();
    }

    @Step("Finds Pets by status " + FIND_PET_BY_STATUS)
    public static Response findPetByStatus(RequestSpecification spec, String body) {

        return given().spec(spec)
                .body(body)
                .when().get(FIND_PET_BY_STATUS)
                .then().extract().response();
    }

    @Step("Find pet by ID " + PET_BY_ID_ACTION)
    public static Response findPetById(RequestSpecification spec, String petId) {
        String requestUrl = PET_BY_ID_ACTION.replace("{petId}", petId);

        return given().spec(spec)
                .when().get(requestUrl)
                .then().extract().response();
    }

    @Step("Updates a pet in the store with form data " + PET_BY_ID_ACTION)
    public static Response updatePetFormData(RequestSpecification spec, String petId, String name, String status) {
        String requestUrl = PET_BY_ID_ACTION.replace("{petId}", petId);

        return given().spec(spec)
                .formParam("name", name)
                .formParam("status", status)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .when().post(requestUrl)
                .then().extract().response();
    }

    @Step("Deletes a pet " + PET_BY_ID_ACTION)
    public static Response deletePet(RequestSpecification spec, String petId) {
        String requestUrl = PET_BY_ID_ACTION.replace("{petId}", petId);

        return given().spec(spec)
                .when().delete(requestUrl)
                .then().extract().response();
    }
}
