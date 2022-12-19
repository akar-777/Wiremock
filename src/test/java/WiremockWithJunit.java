import com.github.tomakehurst.wiremock.junit.WireMockRule;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;


public class WiremockWithJunit {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(9095));

    @Test
    public void wiremock_with_junit_test() throws IOException {
        // stub configuration
        configStubForPostMethod();
        // call request in WireMock through OkHttpClient
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:9095/bpms/service2")
                .method("GET", null)
                .build();

        Response response = client.newCall(request).execute();

        // assert the response
        assertEquals("Hello :)", response.body().string());

    }
    private void configStubForPostMethod() {
//        configureFor("localhost", 9090);
//        stubFor(post(urlPathMatching("/bpms/([a-z]*)"))
//                .willReturn(status(401)
//                        .withBody("Not Valid!!!")));
                        //.withHeader("content-type", "application/json")));
        configureFor("localhost", 9095);
        // create a stub
        stubFor(get(urlEqualTo("/bpms/service2")).willReturn(aResponse().withBody("Hello :)")));
    }
}
