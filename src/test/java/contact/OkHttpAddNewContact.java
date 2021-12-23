package contact;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.ContactDto;
import dto.ErrorDto;
import dto.GetAllContactsDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpAddNewContact {

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im5vYUBnbWFpbC5jb20ifQ.G_wfK7FRQLRTPu9bs2iDi2fcs69FHmW-0dTY4v8o5Eo";
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();

    @Test
    public void addNewContact() throws IOException {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;
        ContactDto contactDto = ContactDto.builder()
                .description("friend")
                .email("er"+i+"@mail.ru")
                .lastName("rone"+i)
                .name("eria"+i)
                .address("Haifa")
                .phone("45678"+i)
                .build();

        RequestBody requestBody = RequestBody.create(gson.toJson(contactDto), JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization", token)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        ContactDto contactDtoResponse = gson.fromJson(response.body().string(), ContactDto.class);
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(contactDtoResponse.getName(), contactDto.getName());

    }


}
