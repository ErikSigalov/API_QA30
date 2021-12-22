package contact;

import com.google.gson.Gson;
import dto.ContactDto;
import dto.DeleteIDDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpDeleteContactById {
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    int id;

    @BeforeMethod

    public void preRequest() throws IOException {
        ContactDto contactDto = ContactDto.builder()
                .description("friend")
                .email("efff@mail.ru")
                .lastName("rotywdn")
                .name("erihds")
                .address("Haifa")
                .phone("7892320115")
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(contactDto),JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization",token)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        ContactDto contactDtoResponse = gson.fromJson(response.body().string(),ContactDto.class);
        id = contactDtoResponse.getId();
    }

@Test
    public void deleteById() throws IOException {
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact/"+id)
                .delete()
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
    Assert.assertTrue(response.isSuccessful());
    DeleteIDDto deleteIDDto = gson.fromJson(response.body().string(),DeleteIDDto.class);
    System.out.println(deleteIDDto.getStatus());
    Assert.assertEquals(deleteIDDto.getStatus(),"Contact was deleted!");
}
}
