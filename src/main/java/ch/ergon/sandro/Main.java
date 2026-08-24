import io.javalin.Javalin;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final String SERVICE_A_URL = "http://localhost:8085/hello";

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8086);

        app.get("/hello", ctx -> ctx.result("Hello from Service B"));

        pollServiceA();
    }

    private static void pollServiceA() {
        HttpClient client = HttpClient.newHttpClient();

        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> {
                    try {
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(SERVICE_A_URL))
                                .GET()
                                .build();

                        HttpResponse<String> response = client.send(
                                request,
                                HttpResponse.BodyHandlers.ofString()
                        );

                        System.out.println("Service A response: " + response.body());

                    } catch (Exception e) {
                        System.out.println("Service A is not available");
                    }
                }, 0, 5, TimeUnit.SECONDS);
    }
}