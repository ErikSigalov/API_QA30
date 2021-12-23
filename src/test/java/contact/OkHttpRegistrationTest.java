package contact;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.RestAssured;
import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import dto.AuthRequestDto;
import dto.ErrorDto;
import okhttp3.OkHttpClient;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import schedulerdto.AuthResponseDto;

public class OkHttpRegistrationTest {

    @BeforeMethod
    public void precondition() {
        RestAssured.baseURI = "https://contacts-telran.herokuapp.com/";
        RestAssured.basePath = "api";
    }

    @Test
    public void registrationSuccessTest() {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("Erik" + i + "@gmail.ru")
                .password("Er" + i + " 12345$")
                .build();


        AuthResponseDto responseDto = given().body(requestDto)
                .contentType("application/json")
                .when()
                .post("registration")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponseDto.class);
        System.out.println(responseDto.getToken());
        System.out.println(responseDto.isRegistration());
        System.out.println(responseDto.getStatus());
        System.out.println(requestDto.getEmail());
        System.out.println(requestDto.getPassword());
    }
    @Test
    public void registrationWrongEmailTest() {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("Erik" + i + "gmail.ru")
                .password("Er" + i + " 12345$")
                .build();


        AuthResponseDto responseDto = given().body(requestDto)
                .contentType("application/json")
                .when()
                .post("registration")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message",containsString("Wrong email format! Example: name@mail.com"))
                .extract().response().as(AuthResponseDto.class);

    }

    @Test
    public void registrationWrongPasswordTest() {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("Erik" + i + "@gmail.ru")
                .password("Er" + i + " 12345")
                .build();


        AuthResponseDto responseDto = given().body(requestDto)
                .contentType("application/json")
                .when()
                .post("registration")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", containsString("Password must contain at least one special symbol from ['$','~','-','_']!"))
                .extract().response().as(AuthResponseDto.class);

    }
    @Test
    public void registrationUserAlreadyExistTest() {
        //int i = (int) (System.currentTimeMillis() / 1000) % 3600;

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("erik@gmail.ru")
                .password("Erik12345$")
                .build();


        AuthResponseDto responseDto = given().body(requestDto)
                .contentType("application/json")
                .when()
                .post("registration")
                .then()
                .assertThat().statusCode(409)
                .assertThat().body("message", containsString("User already exist!"))
                .extract().response().as(AuthResponseDto.class);

    }

}
