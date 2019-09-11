package Specs;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import static  io.restassured.RestAssured.*;



public class EmployeesSpec {

    private static final String BASE_URL = "http://dummy.restapiexample.com/api/v1";
    private static String userId = "";

    @Test
    public void CreateNewUser(){
        String myjson ="{\"name\":\"Dsa411212\",\"salary\":\"1234\",\"age\":\"50\"}";

        Response response =
        given()
                .contentType(ContentType.JSON)
                .body(myjson).
        when()
                .post(BASE_URL + "/create").
        then()
                .statusCode(200).extract().response();
         System.out.println(response.body().asString());
       // userId = response.then().contentType(ContentType.JSON).extract().sessionId();
       // System.out.println(userId);

    }

    @Test
    public void obtnerusuario(){
        Response response =
         given()
                .contentType(ContentType.JSON).
         when()
                .get(BASE_URL+"/employee/47938").
         then()
                .statusCode(200).extract().response();
        System.out.println(response.body().asString());
    }
    @Test
    public void listarValidar(){
        Response response=
        given()
                .contentType(ContentType.JSON).
        when()
                .get(BASE_URL+"/employees").
        then()
                .statusCode(200).extract().response();
         System.out.println(response.body().asString());
    }
    @Test
    public void Error405(){
         String myjson ="{\"name\":\"Dsa411212\",\"salary\":\"12134\",\"age\":\"150\"}";
         given()
                .contentType(ContentType.JSON)
                .body(myjson).
         when()
                .patch(BASE_URL+"/update/47938").
         then()
                 .assertThat().statusCode(405).extract().response();

    }
    @Test
    public void Update() {
        String myjson = "{\"name\":\"Dsa411212\",\"salary\":\"12134\",\"age\":\"150\"}";

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(myjson).
                        when()
                        .put(BASE_URL + "/update/47938").
                        then()
                        .statusCode(200).extract().response();
        System.out.println(response.body().asString());
    }
    @Test
    public void DeleteUser(){

        Response response = given()
                .contentType(ContentType.JSON).
         when()
                .delete(BASE_URL + "/delete/47909").
          then()
                .assertThat().statusCode(200).extract().response();

        System.out.println(response.body().asString());
    }

    @Test
    public void ReBuscarEliminado(){
          Response response =
            given()
                  .contentType(ContentType.JSON).
            when()
                  .get(BASE_URL + "/employee/47909").
            then()
                  .assertThat().statusCode(200).extract().response();
          System.out.println(response.body().asString());
    }



}
