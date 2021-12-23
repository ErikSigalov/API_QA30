package contact;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import schedulerdto.AuthResponseDto;

import java.io.IOException;

public class OkHttpLoginTest {

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

   @Test
   public void loginTest() throws IOException {

       AuthRequestDto requestDto = AuthRequestDto.builder()
               .email("erik@gmail.com")
               .password("Erik12345$")
               .build();

       Gson gson = new Gson();
       OkHttpClient client = new OkHttpClient();

       RequestBody requestBody = RequestBody.create(gson.toJson(requestDto),JSON);

       Request request = new Request.Builder()
               .url("https://contacts-telran.herokuapp.com/api/login")
               .post(requestBody)
               .build();
       Response response = client.newCall(request).execute();

       if (response.isSuccessful()){
           String responseJson = response.body().string();

           AuthResponseDto responseDto = gson.fromJson(responseJson,AuthResponseDto.class);
           System.out.println(responseDto.getToken());
           System.out.println(response.code());
           Assert.assertTrue(response.isSuccessful());
       }else{
           System.out.println("Response code ---> " + response.code());
           ErrorDto errorDto = gson.fromJson(response.body().string(),ErrorDto.class);
           System.out.println(errorDto.getCode() + "****" + errorDto.getMessage() + "****" + errorDto.getDetails());
       }


   }
}
