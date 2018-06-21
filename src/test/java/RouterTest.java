import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.despegar.sparkjava.test.SparkServer;
import game.Board;
import org.junit.ClassRule;
import org.junit.Test;
import spark.servlet.SparkApplication;
import static game.Mark.EMPTY;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RouterTest {

    public static class WebApplicationTestSparkApplication implements SparkApplication {
        public void init() {
            new Router().run();
        }
    }

    @ClassRule
    public static SparkServer<WebApplicationTestSparkApplication> testServer = new SparkServer<>(WebApplicationTestSparkApplication.class, 4567);

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
   public void itFormatsQueryStringFromEmptyGridState() {
       Router router = new Router();
       Board board = new Board();
       board.grid = asList(EMPTY, EMPTY, EMPTY);

       assertEquals("123", router.createQueryValueForGridState(board.grid));
   }
}
