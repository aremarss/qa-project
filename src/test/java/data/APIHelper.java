package data;

import io.restassured.http.ContentType;

import static data.DataHelper.AuthInfo.validUser;
import static io.restassured.RestAssured.given;

public class APIHelper {

    private final static String baseUri = "http://localhost:8080/api/v1";

    public static void payFromCard(String cardType, String post, int statusCode) {
        given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .body(validUser(cardType))
                .when()
                .post(post)
                .then()
                .statusCode(statusCode);
    }
}