import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.despegar.sparkjava.test.SparkServer;
import game.Board;
import org.junit.ClassRule;
import org.junit.Test;
import spark.servlet.SparkApplication;
import static game.Mark.EMPTY;
import static game.Mark.O;
import static game.Mark.X;
import static java.util.Arrays.asList;
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
   public void itFormatsQueryStringFromEmptyGridState() {
       Router router = new Router();
       Board board = new Board();
       board.grid = asList(EMPTY, EMPTY, EMPTY);

       assertEquals("123", router.createQueryValueForGridState(board.grid));
   }

    @Test
    public void itFormatsQueryStringFromGridStateWithMarks() {
        Router router = new Router();
        Board board = new Board();
        board.grid = asList(EMPTY, X, O);

        assertEquals("1XO", router.createQueryValueForGridState(board.grid));
    }
}
