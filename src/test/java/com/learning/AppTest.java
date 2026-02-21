package com.learning;

import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(-7, App.add(-5, -3));
    }

    @Test
    void addReturnsSumOfTwoNumbers1() {
        assertEquals(-7, App.add(-5, -3));
        assertNotNull();
    }

    @Test
    void createStoreOrderTest() {
        RestAssured.baseURI = 'https://petstore.swagger.rv-school.ru/api/v3';
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
                .body(resuestBody)
                .when()
                .post('/store/order')
                .then()
                .extract().response();
        assertEquals(200,response.statusCode(),"Неверный статус код");
        assertEquals(10,response.jsonPath().getInt("id"),"Неверный id заказа");
        assertEquals(198772,response.jsonPath().getInt("petId"),"Неверный petId заказа");
        assertEquals(7,response.jsonPath().getInt("quantity"),"Неверный quantity заказа");
        assertEquals("approved",response.jsonPath().getString("status"),"Неверный статус заказа");
        assertTrue(response.jsonPath().getBoolean("complete"), "Неверный complete");
    }
}
