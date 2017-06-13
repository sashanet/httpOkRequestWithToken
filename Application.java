package hello;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class Application {

    private static final String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJpZFwiOlwiMTA4MzMxNzM2MDg4ODk1ODE1NzlcIixcImlzQWRtaW5cIjp0cnVlfSIsImV4cCI6MTQ5NzUxODczNSwiaWF0IjoxNDk3MzQ1OTM1fQ.6zA8kl0BqaehMnh8i1MC3cJjA1YXzLJiakeDL79eljo";
    private static final String link = "http://localhost:8080/admin/two-way-numbers/%number%";

    private static  List<String> numbers = Arrays.asList("121323424");


    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        run();
    }

    private static void run() {
        OkHttpClient client = new OkHttpClient();
        numbers.forEach(number -> {
            Request request = getRequest(number);
            try {
                Response response = client.newCall(request).execute();
                if (response.code() == 200) {
                    log.info("Number {} deleted successful with message {}", number, response.body().string());
                } else {
                    log.error("Error deleted number {} with message {}", number, response.body().string());
                }
            } catch (IOException e) {
                log.error("Error deleted number {} with message {}", number, e.getMessage());
            }
        });
    }


    private static Request getRequest(String number) {
        return new Request.Builder()
                .url(link.replace("%number%", number))
                .addHeader("Authorization", token)
                .method("DELETE", RequestBody.create(null, new byte[0]))
                .build();
    }
}