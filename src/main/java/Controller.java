import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.fail;

public class Controller {
    public RequestSpecification getBaseRSpec() {
        return new RequestSpecBuilder()
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Host", "192.168.0.1")
                .addHeader("Origin", "http://192.168.0.1")
                .addHeader("Referer", "http://192.168.0.1/index.html")
                .addHeader("X-Requested-With", "XMLHttpRequest")
//                .setConfig(RestAssured.config().sslConfig(new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()))
                .build();
    }

    public boolean sendSMS(String number, String messageBody) {
        Response response = given(getBaseRSpec())
                .log().all()
                .formParam("isTest", "false")
                .formParam("goformId", "SEND_SMS")
                .formParam("notCallback", "true")
                .formParam("Number", number)
                .formParam("MessageBody", messageBody)
                .formParam("encode_type", "GSM7_default")
                .formParam("ID", "-1")
                .formParam("sms_time", new SimpleDateFormat("yy;MM;dd;hh;mm;ss+03").format(new Date()))
                .when().post("http://192.168.0.1/goform/goform_set_cmd_process")
                .then()
                .log().all()
                .extract().response();
        if (response.asString().contains("error") | response.statusCode() != 200) {
            return false;
//            fail("Ошибка при отправке сообщения через API: \n" + response.asString());
        } else return true;
    }

    public void deleteHistory() {

    }
}
