import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


public class LiveEndpointFunctionality {
    private static Response response;
    private static LocalDate localDate;
    private static DateFormat dateFormat;
    private static SimpleDateFormat simpleDateFormat;
    private static Date date;
    public static final Logger logger = LogManager.getLogger(LiveEndpointFunctionality.class);

    @Test
    public void timeStampTest() {
        Response response = given().get(Consts.URL + Consts.REAL_TIME_ENDPOINT + Consts.TOKEN);
        System.out.println(response.asString());
        //get yesterday date as String
        String expected = LocalDate.now().minusDays(1).toString();

        //get timestamp from response
        Integer actualMs = response.path("timestamp");

        //create format to match expected String date
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        //get Date from the timestamp in request - a hard part, we need to set it to long and multiply by 1000
        //as in this case API returns UNIX time and not epoch time
        Date date2 = new Date((long) actualMs * 1000);

        //format date from response to match expected String date
        String actual = format.format(date2.getTime());

        //compare
        Assertions.assertEquals(expected, actual);
    }



//2.1  Get current currency conversion rates
//
///live endpoint is expected to return JSON response

    @Test
    public void apiLiveEndpointsUsdCadTest() {
        logger.info("apiLiveEndpointsUsdCad is Running");
        response = given().get(Consts.URL + Consts.REAL_TIME_ENDPOINT + Consts.TOKEN + Consts.USD_CAD_ENDPOINT);
        System.out.println(response.asString());
        response.then().body("success", equalTo(true));
        response.then().body("terms", equalTo("https://currencylayer.com/terms"));
        response.then().body("privacy", equalTo("https://currencylayer.com/privacy"));
        response.then().body("source", equalTo("USD"));
        response.then().body("quotes.USDCAD", equalTo(1.24985f));

    }
    @Test
    public void getSuccessfulResponseCodeTest() {
        response.then().statusCode(200);
    }

    @Test
    public void apiLiveEndpointsUsdEurTest() {
        logger.info("apiLiveEndpointsUsdEurTest is Running");
        response = given().get(Consts.URL + Consts.REAL_TIME_ENDPOINT + Consts.TOKEN + Consts.USD_EUR_ENDPOINT);
        System.out.println(response.asString());
        response.then().body("success", equalTo(true));
        response.then().body("terms", equalTo("https://currencylayer.com/terms"));
        response.then().body("privacy", equalTo("https://currencylayer.com/privacy"));
        response.then().body("source", equalTo("USD"));
        response.then().body("quotes.USDEUR", equalTo(0.90884f));

    }
    @Test
    public void apiLiveEndpointsUsdNisTest() {
        logger.info("apiLiveEndpointsUsdNisTest is Running");
        response = given().get(Consts.URL + Consts.REAL_TIME_ENDPOINT + Consts.TOKEN + Consts.USD_NIS_ENDPOINT);
        System.out.println(response.asString());
        response.then().body("success", equalTo(true));
        response.then().body("terms", equalTo("https://currencylayer.com/terms"));
        response.then().body("privacy", equalTo("https://currencylayer.com/privacy"));
        response.then().body("source", equalTo("USD"));
        response.then().body("quotes.USDUSD", equalTo(1));

    }
    @Test
    public void apiLiveEndpointsUsdRubTest() {
        logger.info("apiLiveEndpointsUsdRubTest is Running");
        response = given().get(Consts.URL + Consts.REAL_TIME_ENDPOINT + Consts.TOKEN + Consts.USD_RUB_ENDPOINT);
        System.out.println(response.asString());
        response.then().body("success", equalTo(true));
        response.then().body("terms", equalTo("https://currencylayer.com/terms"));
        response.then().body("privacy", equalTo("https://currencylayer.com/privacy"));
        response.then().body("quotes.USDRUB", equalTo(90.749728f));
    }
    //2.2.1 Every currency could be retrieved to the client using the “currencies" parameter, In the request, we should be able to send one or several currencies divided by comma.

    @Test
    public void apiLiveEndpointsSendCurrenciesTest() {
        logger.info("apiLiveEndpointsSendCurrenciesTest is Running");
        response = given().get(Consts.URL + Consts.REAL_TIME_ENDPOINT + Consts.TOKEN + Consts.NIS_ENDPOINT + "," + Consts.RUB_ENDPOINT);
        System.out.println(response.asString());
        response.then().body("success", equalTo(true));
        response.then().body("terms", equalTo("https://currencylayer.com/terms"));
        response.then().body("privacy", equalTo("https://currencylayer.com/privacy"));
        response.then().body("quotes.USDRUB", equalTo(90.749728f));
    }

    //2.2.3 Incorrect currency code should trigger a user-friendly error.
    @Test
    public void incorrectCurrencyTest(){
            logger.info("incorrectCurrencyTest is Running");
            response = given().get(Consts.URL + Consts.TOKEN + Consts.INCORRECT_ENDPOINT);
            System.out.println(response.asString());
            response.then().body("success", equalTo(false));
            response.then().statusCode(200); //Expected Code 202
            response.then().body("error.info", equalTo("You have provided one or more invalid Currency Codes. [Required format: currencies=EUR,USD,GBP,...]"));

        }


    }










