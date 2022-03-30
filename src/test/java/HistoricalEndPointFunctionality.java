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


public class HistoricalEndPointFunctionality {



    private static Response response;
    public static final Logger logger = LogManager.getLogger(HistoricalEndPointFunctionality.class);

    //2.3 historical conversion according to date


    @Test
    public void apiHistoricalEndpointsUsdCadTest() {
        logger.info("apiHistoricalEndpointsUsdCadTest is Running");
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + Consts.USD_CAD_ENDPOINT + Consts.HISTORICAL_ENDPOINT_DATE_2003);
        System.out.println(response.asString());
        response.then().body("success", equalTo(true));
        response.then().body("terms", equalTo("https://currencylayer.com/terms"));
        response.then().body("privacy", equalTo("https://currencylayer.com/privacy"));
        response.then().body("historical", equalTo(true));
        response.then().body("date", equalTo("2003-05-05"));
        response.then().body("source", equalTo("USD"));
        response.then().body("quotes.USDCAD", equalTo(1.41508f));
    }
    @Test
    public void apiHistoricalEndpointsUsdEurTest() {
        logger.info("apiHistoricalEndpointsUsdEurTest is Running");
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + Consts.USD_EUR_ENDPOINT+ Consts.HISTORICAL_ENDPOINT_DATE_2015);
        System.out.println(response.asString());
        response.then().body("success", equalTo(true));
        response.then().body("terms", equalTo("https://currencylayer.com/terms"));
        response.then().body("privacy", equalTo("https://currencylayer.com/privacy"));
        response.then().body("historical", equalTo(true));
        response.then().body("date", equalTo("2015-10-05"));
        response.then().body("source", equalTo("USD"));
        response.then().body("quotes.USDEUR", equalTo(0.893663f));
    }

    @Test
    public void apiHistoricalEndpointsUsdNisTest() {
        logger.info("apiHistoricalEndpointsUsdNisTest is Running");
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + Consts.USD_NIS_ENDPOINT+ Consts.HISTORICAL_ENDPOINT_DATE_2019);
        System.out.println(response.asString());
        response.then().body("success", equalTo(true));
        response.then().body("terms", equalTo("https://currencylayer.com/terms"));
        response.then().body("privacy", equalTo("https://currencylayer.com/privacy"));
        response.then().body("historical", equalTo(true));
        response.then().body("date", equalTo("2019-06-05"));
        response.then().body("source", equalTo("USD"));
        response.then().body("quotes.USDUSD", equalTo(1));
    }
    @Test
    public void apiHistoricalEndpointsUsdRubTest() {
        logger.info("apiHistoricalEndpointsUsdRubTest is Running");
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + Consts.USD_RUB_ENDPOINT+ Consts.HISTORICAL_ENDPOINT_DATE_2019);
        System.out.println(response.asString());
        response.then().body("success", equalTo(true));
        response.then().body("terms", equalTo("https://currencylayer.com/terms"));
        response.then().body("privacy", equalTo("https://currencylayer.com/privacy"));
        response.then().body("historical", equalTo(true));
        response.then().body("date", equalTo("2019-06-05"));
        response.then().body("source", equalTo("USD"));
        response.then().body("quotes.USDRUB", equalTo(65.313501f));
    }
    //historical endpoint is functional and requires the “Date” parameter
// (user-friendly error should be returned if the parameter is incorrect or missing).
// As well endpoint is able to receive (optional) currencies parameter (see 2.2).
    @Test
    public void apiHistoricalMissingDateTest() {
        logger.info("apiHistoricalMissingDateTest is Running");
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + Consts.USD_RUB_ENDPOINT);
        System.out.println(response.asString());
        response.then().body("success", equalTo(false));
        response.then().statusCode(200); //Expected Code 301
        response.then().body("error.info", equalTo("You have not specified a date. [Required format: date=YYYY-MM-DD]"));
    }
    //2.3.4 User-friendly errors are expected to be returned in case of wrong/missing parameters.
    @Test
    public void apiHistoricalInvalidDateTest() {
        logger.info("apiHistoricalInvalidDateTest is Running");
        response = given().get(Consts.URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + Consts.USD_RUB_ENDPOINT + Consts.HISTORICAL_ENDPOINT_INVALID_DATE);
        System.out.println(response.asString());
        response.then().body("success", equalTo(false));
        response.then().statusCode(200); //Expected Code 302
        response.then().body("error.info", equalTo("You have entered an invalid date. [Required format: date=YYYY-MM-DD]"));


    }
    @Test
    public void apiInvalidUrlHttpsTest() {
        logger.info("apiInvalidUrlHttpsTestis Running");
        response = given().get(Consts.HISTORICAL_INVALID_URL + Consts.HISTORICAL_ENDPOINT + Consts.TOKEN + Consts.USD_RUB_ENDPOINT + Consts.HISTORICAL_ENDPOINT_INVALID_DATE);
        System.out.println(response.asString());
        response.then().body("success", equalTo(false));
        response.then().statusCode(200); //Expected Code 105
        response.then().body("error.info", equalTo("Access Restricted - Your current Subscription Plan does not support HTTPS Encryption."));
    }









}
