package okhttp;

import com.google.gson.Gson;
import dto.ErrorDTO;
import dto.MessageDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteContactByIdOkhttp {

    String token ="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiZEBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTc0NTM5MTIxNywiaWF0IjoxNzQ0NzkxMjE3fQ.xczMbyo4C0AwLKCdvVavlKKrICa8cNHh_GF6CB73SAg";
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();//отправляет запросы на сервер
    String id;

    @BeforeMethod
    public void preCondition(){
        //create contact
    }

    @Test
    public void deleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/955f8dd9-e1d5-4693-bb98-d556885a0687")
                .delete()
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
        MessageDTO messageDTO = gson.fromJson(response.body().string(),MessageDTO.class);
        System.out.println(messageDTO.getMassage());
        //Assert.assertEquals(messageDTO.getMassage(),"Contact was deleted!");
    }

    @Test
    public void deleteContactByIdWrongToken() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/955f8dd9-e1d5-4693-bb98-d556885a0687")
                .delete()
                .addHeader("Authorization","dfhilurehfiurewhf")
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(),ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(),"Unauthorized");
    }

    @Test
    public void deleteContactByIdNotFound() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/955f8dd9")
                .delete()
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(),ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(),"Bad Request");
        //System.out.println(errorDTO.getMessage());
        Assert.assertEquals(errorDTO.getMessage(),"Contact with id: 955f8dd9 not found in your contacts!");
    }
}
