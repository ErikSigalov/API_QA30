package scheduler;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import com.jayway.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import schedulerdto.AuthRequestDto;
import schedulerdto.AuthResponseDto;
import schedulerdto.ErrorDto;

public class LoginRegistrationRestAssered {

    @BeforeMethod

    public void precondition(){
        RestAssured.baseURI = "https://super-scheduler-app.herokuapp.com/";
        RestAssured.basePath = "api";
    }

    @Test
    public void loginSuccess(){
        AuthRequestDto auth = AuthRequestDto.builder()
                .email("noa@gmail.com")
                .password("Nnoa12345$")
                .build();

        AuthResponseDto responseDto = given().body(auth)
                .contentType("application/json")
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(AuthResponseDto.class);
        System.out.println(responseDto.getToken());
        System.out.println(responseDto.isRegistration());
        System.out.println(responseDto.getStatus());

    }

    @Test

    public void wrongPasswordTest(){
        AuthRequestDto auth = AuthRequestDto.builder()
                .email("noa@gmail.com")
                .password("Nna12345")
                .build();

        ErrorDto errorDto = given().body(auth)
                .contentType("application/json")
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(401)
                .extract().response().as(ErrorDto.class);
        System.out.println(errorDto.toString());
        Assert.assertEquals(errorDto.getMessage(),"Wrong email or password");
    }

    @Test

    public void wrongPasswordTest2(){
        AuthRequestDto auth = AuthRequestDto.builder()
                .email("noa@gmail.com")
                .password("Nna12345")
                .build();

        String message = given().body(auth)
                .contentType("application/json")
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(401)
                .extract().path("message");

       Assert.assertEquals(message,"Wrong email or password");
    }

    @Test
    public void registrationTest(){
        int index = (int) (System.currentTimeMillis()/1000)%3600;
        AuthRequestDto auth = AuthRequestDto.builder()
                .email("Nno"+index+"@gmail.com")
                .password("Er"+index+"12346$")
                .build();
        given().contentType("application/json")
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("status",containsString("Registration success"))
                .assertThat().body("registration",equalTo(true))
                .extract().path("token");

    }
}
