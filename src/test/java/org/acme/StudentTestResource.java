package org.acme;

import org.junit.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

@QuarkusTest
public class StudentTestResource {

    @Test
    public void testCreateStudent() {
        String studentJson = "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\" }";

        given()
            .contentType(ContentType.JSON)
            .body(studentJson)
            .when()
            .post("/students")
            .then()
            .statusCode(201);
    }


    @Test
    public void testGetAllStudents() {
        // Create a student first
        String studentJson = "{ \"firstName\": \"Jane\", \"lastName\": \"Doe\", \"email\": \"jane.doe@example.com\" }";

        given()
            .contentType(ContentType.JSON)
            .body(studentJson)
            .when()
            .post("/students")
            .then()
            .statusCode(201);

        
    }

    @Test
    public void testGetStudentById() {
        String studentJson = "{ \"firstName\": \"Mike\", \"lastName\": \"Smith\", \"email\": \"mike.smith@example.com\" }";

        Long studentId = given()
            .contentType(ContentType.JSON)
            .body(studentJson)
            .when()
            .post("/students")
            .then()
            .statusCode(201)
            .extract().path("id");

        // Get the student by ID
        given()
            .when().get("/students/" + studentId)
            .then()
            .statusCode(200);
    }

    @Test
    public void testUpdateStudent() {
        String studentJson = "{ \"firstName\": \"Tom\", \"lastName\": \"Hanks\", \"email\": \"tom.hanks@example.com\" }";

        Long studentId = given()
            .contentType(ContentType.JSON)
            .body(studentJson)
            .when()
            .post("/students")
            .then()
            .statusCode(201)
            .extract().path("id");

        String updatedStudentJson = "{ \"firstName\": \"Thomas\", \"lastName\": \"Hanks\", \"email\": \"thomas.hanks@example.com\" }";

        given()
            .contentType(ContentType.JSON)
            .body(updatedStudentJson)
            .when()
            .put("/students/" + studentId)
            .then()
            .statusCode(200);
    }

    @Test
    public void testDeleteStudent() {
        String studentJson = "{ \"firstName\": \"Alice\", \"lastName\": \"Wonderland\", \"email\": \"alice.wonderland@example.com\" }";

        Long studentId = given()
            .contentType(ContentType.JSON)
            .body(studentJson)
            .when()
            .post("/students")
            .then()
            .statusCode(201)
            .extract().path("id");

        given()
            .when().delete("/students/" + studentId)
            .then()
            .statusCode(204);

        given()
            .when().get("/students/" + studentId)
            .then()
            .statusCode(404);
    }
}
