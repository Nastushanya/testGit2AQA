package com.learning;

import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Простой тест для метода add класса App.
 */
class AppTest {

    @Test
    void addReturnsSumOfTwoNumbers() {
        assertEquals(5, App.add(2, 3));
        assertEquals(0, App.add(-1, 1));
        assertEquals(-5, App.add(-2, -3));
    }

    @Test
    void addReturnsSumOfTwoNumbers1() {
        assertEquals(-8, App.add(-5, -3));
    }


    @Test
    void createStoreOrderTest() {
        RestAssured.baseURI = "https://petstore.swagger.rv-school.ru/api/v3";
        String requestBody = """
                              {
                              "id": 10,
                                "petId": 198772,
                                "quantity": 7,
                                "shipDate": "2026-02-21T08:21:23.781Z",
                                "status": "approved",
                                "complete": true
                }
                """;
        Response response = RestAssured
                .given()
                .header("Content-Type","application/json")
                .body(requestBody)
                .when()
                .post("/store/order")
                .then()
                .extract().response();
        assertEquals(200,response.statusCode(),"Неверный статус код");
        assertEquals(10,response.jsonPath().getInt("id"),"Неверный id заказа");
        assertEquals(198772,response.jsonPath().getInt("petId"),"Неверный petId заказа");
        assertEquals(7,response.jsonPath().getInt("quantity"),"Неверный quantity заказа");
        assertEquals("approved",response.jsonPath().getString("status"),"Неверный статус заказа");
        assertTrue(response.jsonPath().getBoolean("complete"), "Неверный complete");
    }

    @Test
    void createPetOrderPositiveTestWithIdNameStatus() {
        RestAssured.baseURI = "https://petstore.swagger.rv-school.ru/api/v3";
        String requestBody = """
                             {
                                 "id": 1,
                                 "name": "Buddy",
                                 "status": "available"
                               }
                """;
        Response response = RestAssured
                .given()
                .header("Content-Type","application/json")
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .extract().response();

        assertEquals(200,response.statusCode(),"Неверный статус код");
        assertEquals(1,response.jsonPath().getInt("id"),"Неверный id заказа");
        assertEquals("Buddy",response.jsonPath().getString("name"),"Неверное имя");
        assertEquals("available",response.jsonPath().getString("status"),"Неверное значение поля status");

    }
    @Test
    void createPetOrderPositiveTestWithIdNameStatusUrls() {
        RestAssured.baseURI = "https://petstore.swagger.rv-school.ru/api/v3";
        String requestBody = """
                             {
                                 "id": 2,
                                 "name": "Buddy",
                                 "photoUrls": [
                                   "string"
                                 ],
                                 "status": "available"
                               }
                """;
        Response response = RestAssured
                .given()
                .header("Content-Type","application/json")
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .extract().response();

        assertEquals(200,response.statusCode(),"Неверный статус код");
        assertEquals(2,response.jsonPath().getInt("id"),"Неверный id заказа");
        assertEquals("Buddy",response.jsonPath().getString("name"),"Неверное имя");
        assertEquals(1,response.jsonPath().getList("photoUrls").size(),"Неверное число ссылок на фото");
        assertTrue(response.jsonPath().getList("photoUrls").contains("string"), "Неверная ссылка на фото");
        assertEquals("available",response.jsonPath().getString("status"),"Неверное значение поля status");

    }
    /*@Test
    void createPetOrderNegativeTest() {
        RestAssured.baseURI = "https://petstore.swagger.rv-school.ru/api/v3";
        String requestBody = """
                              {
                              id: 1,
                              name: "Buddy",
                              status: "available"
                              }
                """;
        Response response = RestAssured
                .given()
                .header("Content-Type","application/json")
                .body(requestBody)
                .when()
                .post("/pet")
                .then()
                .extract().response();
        assertEquals(400,response.statusCode());

    }*/
}
