package okhttp;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class RegistrationTestsOkhttp {

    Gson gson = new Gson();

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");//что в запросе
    OkHttpClient client = new OkHttpClient();//отправляет запросы на сервер
}
