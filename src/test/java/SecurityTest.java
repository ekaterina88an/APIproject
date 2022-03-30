import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

//1.2. Unauthorized users should be restricted to get the response from the external API -

//1.3. Expected error: You have not supplied a valid API Access Key.

public class SecurityTest {
    public static final Logger logger = LogManager.getLogger(SecurityTest.class);
    @Test
    public void invalidTokenTest() {
        logger.info("invalidTokenTest is Running");
        Response response = given().contentType("application/json").get(Consts.URL + Consts.INVALID_TOKEN);
        System.out.println(response.asString());
        response.then().statusCode(200); //Expected Code 101
        response.then().body("error.info", equalTo("You have not supplied a valid API Access Key. [Technical Support: support@apilayer.com]"));

    }
}
