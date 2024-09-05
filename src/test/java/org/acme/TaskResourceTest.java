package org.acme;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class TaskResourceTest {
   
    @Test
    void testGetTaskById() {
        Long taskId = 1L; 

        given()
          .pathParam("id", taskId)
          .when().get("/task/{id}")
          .then()
             .statusCode(200)
             .body("id", is(taskId.intValue())); 
    }

    @Test
    void testSaveTask() {
        String newTaskJson = "{\"name\": \"New Task\", \"description\": \"A new task for testing\", \"completed\": false}";

        given()
          .header("Content-Type", "application/json")
          .body(newTaskJson)
          .when().post("/task")
          .then()
             .statusCode(201); 
    }

    @Test
    void testGetNonExistentTask() {
        Long nonExistentTaskId = 999L;
        given()
          .pathParam("id", nonExistentTaskId)
          .when().get("/task/{id}")
          .then()
             .statusCode(204); 
    }

    
}