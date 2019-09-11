package Specs;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import java.util.Random;
import static io.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.JVM)

public class EmployeeSpec {
    private static final String BASE_URL = "http://dummy.restapiexample.com/api/v1";
    private static Response response;
    private static String userId = "";
    private static String randomName;

    @BeforeClass
    public static void setUp() {
        Random random = new Random();
        randomName = "loadtest" + random.nextInt(200);
        createNewUser();
    }

    @Test
    public void verifyUserExists() {
        response = given()
                .baseUri(BASE_URL).log().all()
                .pathParam("id", userId).
                        when()
                .get("/employee/{id}").
                        then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println(response.jsonPath().get("employee_name"));

        Assert.assertEquals(randomName, response.jsonPath().get("employee_name"));
    }

    @Test
    public void updateUserWithWrongHTTPMethod() {
        response = given()
                .baseUri(BASE_URL).log().all()
                .contentType(ContentType.JSON)
                .pathParam("id", userId)
                .body("{\"salary\":\"9550\"}").
                        when()
                .patch("/update/{id}").
                        then()
                .statusCode(405)
                .extract()
                .response();

        System.out.println(response.body().asString());
    }

    @Test
    public void updateUser() {
        String newUserJSON = String.format("{\"name\":\"%s\",\"salary\":\"1950\",\"age\":\"24\"}", randomName);

        response = given()
                .baseUri(BASE_URL).log().all()
                .contentType(ContentType.JSON)
                .pathParam("id", userId)
                .body(newUserJSON).
                        when()
                .put("/update/{id}").
                        then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println(response.body().asString());
    }
    @Test
    public void ListAndVerifyUpdate() {
        response = given()
                .baseUri(BASE_URL).log().all()
                //.pathParam("id", userId)
                         .when()
                .get("/employees").
                        then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println(response.body().asString());

        //Assert.assertEquals(randomName, response.jsonPath().get("employee_name"));
    }
    @Test
    public void DeleteUser() {
        response = given()
                .baseUri(BASE_URL).log().all()
                .pathParam("id", userId).
                        when()
                .delete("/delete/{id}").
                        then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println(response.body().asString());

       // Assert.assertEquals(randomName, response.jsonPath().get("employee_name"));
    }
    @Test
    public void VerifyDeleteUser() {
        response = given()
                .baseUri(BASE_URL).log().all()
                .pathParam("id", userId).
                        when()
                .get("/employee/{id}").
                        then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println(response.body().asString());
    }


    private static void createNewUser() {
        String newUserJSON = String.format("{\"name\":\"%s\",\"salary\":\"950\",\"age\":\"22\"}", randomName);

        response = given()
                .baseUri(BASE_URL).log().all()
                .contentType(ContentType.JSON)
                .body(newUserJSON).
                        when()
                .post("/create").
                        then()
                .statusCode(200)
                .extract()
                .response();

        userId = response.jsonPath().get("id");
    }

}
