import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.despegar.sparkjava.test.SparkServer;
import org.junit.ClassRule;
import org.junit.Test;
import spark.servlet.SparkApplication;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RouterTest {

    public static class RouterTestForSparkApplication implements SparkApplication {
        public void init() {
            new Router().run();
        }
    }

    @ClassRule
    public static SparkServer<RouterTestForSparkApplication> testServer = new SparkServer<>(RouterTestForSparkApplication.class, 4567);

    @Test
    public void serverRespondsSuccessfully() throws HttpClientException {
        GetMethod request = testServer.get("/", false);
        HttpResponse httpResponse = testServer.execute(request);
        assertEquals(200, httpResponse.code());
    }

    @Test
    public void responseBodyContainsGridNumbers() throws HttpClientException {
        GetMethod request = testServer.get("/", false);
        HttpResponse httpResponse = testServer.execute(request);
        assertTrue((new String(httpResponse.body()).matches(".*1.*2.*3.*4.*5.*6.*7.*8.*9.*")));
    }

    @Test
    public void moveMadeAndUpdatedOnBoard() throws HttpClientException {
        GetMethod request = testServer.get("/makeMove/1?move=1&currentBoard=123456789&gameMode=8", false);
        HttpResponse httpResponse = testServer.execute(request);

        assertTrue((new String(httpResponse.body()).matches(".*1.*X.*3.*4.*5.*6.*7.*8.*9.*")));
    }

    @Test
    public void PlayerTwoMakesAMove() throws HttpClientException {
        GetMethod request = testServer.get("/makeMove/5?move=5&currentBoard=1X3456789&gameMode=8", false);
        HttpResponse httpResponse = testServer.execute(request);

        assertTrue((new String(httpResponse.body()).matches(".*1.*X.*3.*4.*5.*O.*7.*8.*9.*")));
    }

    @Test
    public void GameIsScoredAndXWins() throws HttpClientException {
        GetMethod request = testServer.get("/makeMove/2?move=2&currentBoard=XX345OO89&gameMode=8", false);
        HttpResponse httpResponse = testServer.execute(request);

        assertTrue(new String(httpResponse.body()).contains("X wins!"));
    }
}
