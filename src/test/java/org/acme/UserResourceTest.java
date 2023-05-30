package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.acme.entity.User;
import org.acme.service.UserService;
import org.hibernate.engine.jdbc.connections.internal.UserSuppliedConnectionProviderImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static io.restassured.RestAssured.given;


//@QuarkusTest
public class UserResourceTest {

    @Inject
    UserService userService;

    @Test
    public void testAddUser() {
        String requestBody = "{\"username\": \"user1\", \"email\": \"user@gmail.com\"}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetUser() {
        User user = userService.getAllUsers().get(0);

        given()
                .when().get("/users/" + user.getId())
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetAllUsers() {
        given()
                .when().get("/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void testUpdateUser() {
        String requestBody = "{\"username\": \"updated_user\", \"email\": \"updated_user@gmail.com\"}";
        User user = userService.getAllUsers().get(0);

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().put("/users/" + user.getId())
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteUser() {
        User user = userService.getAllUsers().get(0);

        given()
                .when().delete("/users/" + user.getId())
                .then()
                .statusCode(200);
    }




    @Test
    public void getNonexistentUser(){
        List<User> allUsers = userService.getAllUsers();

        Random random = new Random();
        Long randLong = random.nextLong();

        boolean stayInLoop = true;

        while(stayInLoop){
            stayInLoop = false;
            for(User user : allUsers){
                if(Objects.equals(user.getId(), randLong)){
                    stayInLoop = true;
                    randLong = random.nextLong();
                    break;
                }
            }
        }

        given()
                .when().get("/users/" + randLong)
                .then()
                .statusCode(204);

    }

    @Test
    public void postBadUsername(){
        String requestBody = "{\"username\": \"us\", \"email\": \"user@gmail.com\"}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/users")
                .then()
                .statusCode(400);
    }

    @Test
    public void postBadEmail(){
        String requestBody = "{\"username\": \"user\", \"email\": \"usercom\"}";

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/users")
                .then()
                .statusCode(400);
    }

    @Test
    public void putBadUsername() {
        String requestBody = "{\"username\": \"up\", \"email\": \"updated_user@gmail.com\"}";
        User user = userService.getAllUsers().get(0);

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().put("/users/" + user.getId())
                .then()
                .statusCode(400);
    }

    @Test
    public void putBadEmail() {
        String requestBody = "{\"username\": \"updated_user\", \"email\": \"updatedcom\"}";
        User user = userService.getAllUsers().get(0);

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().put("/users/" + user.getId())
                .then()
                .statusCode(400);
    }

    @Test
    public void putNonexistentUser() {
        String requestBody = "{\"username\": \"updated_user\", \"email\": \"updated_user@gmail.com\"}";

        List<User> allUsers = userService.getAllUsers();

        Random random = new Random();
        Long randLong = random.nextLong();

        boolean stayInLoop = true;

        while(stayInLoop){
            stayInLoop = false;
            for(User user : allUsers){
                if(Objects.equals(user.getId(), randLong)){
                    stayInLoop = true;
                    randLong = random.nextLong();
                    break;
                }
            }
        }

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().put("/users/" + randLong)
                .then()
                .statusCode(404);
    }

    @Test
    public void deleteNonexistentUser() {

        List<User> allUsers = userService.getAllUsers();

        Random random = new Random();
        Long randLong = random.nextLong();

        boolean stayInLoop = true;

        while(stayInLoop){
            stayInLoop = false;
            for(User user : allUsers){
                if(Objects.equals(user.getId(), randLong)){
                    stayInLoop = true;
                    randLong = random.nextLong();
                    break;
                }
            }
        }

        given()
                .when().delete("/users/" + randLong)
                .then()
                .statusCode(404);
    }

}
