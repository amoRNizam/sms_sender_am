import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

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
        Response response = given()
                .log().all()
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("Host", "192.168.0.1")
                .header("Origin", "http://192.168.0.1")
                .header("Referer", "http://192.168.0.1/index.html")
                .header("X-Requested-With", "XMLHttpRequest")
                .formParam("isTest", "false")
                .formParam("goformId", "SEND_SMS")
                .formParam("notCallback", "true")
                .formParam("Number", number)
                .formParam("MessageBody", messageBody)
//                .formParam("encode_type", "GSM7_default")
                .formParam("encode_type", "UNICODE")
                .formParam("ID", "-1")
                .formParam("sms_time", new SimpleDateFormat("yy;MM;dd;hh;mm;ss+03").format(new Date()))
                .when().post("http://192.168.0.1/goform/goform_set_cmd_process")
                .then()
                .log().all()
                .extract().response();
        if (response.asString().contains("failure") | response.statusCode() != 200) {
            return false;
//            fail("Ошибка при отправке сообщения через API: \n" + response.asString());
        } else return true;
    }

    public boolean deleteHistory() {
        Response response = given()
                .log().all()
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("Host", "192.168.0.1")
                .header("Origin", "http://192.168.0.1")
                .header("Referer", "http://192.168.0.1/index.html")
                .header("X-Requested-With", "XMLHttpRequest")
                .formParam("isTest", "false")
                .formParam("goformId", "DELETE_SMS")
                .formParam("notCallback", "true")
                .formParam("msg_id", getSMSList())
                .when().post("http://192.168.0.1/goform/goform_set_cmd_process")
                .then()
                .log().all()
                .extract().response();
        if (response.asString().contains("failure") | response.statusCode() != 200) {
            return false;
//            fail("Ошибка при отправке сообщения через API: \n" + response.asString());
        } else return true;
    }

    private String getSMSList() {
        Response response = given()
                .log().all()
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .header("Host", "192.168.0.1")
                .header("Origin", "http://192.168.0.1")
                .header("Referer", "http://192.168.0.1/index.html")
                .header("X-Requested-With", "XMLHttpRequest")
                .when().get("http://192.168.0.1/goform/goform_get_cmd_process?isTest=false&cmd=sms_data_total&page=0&data_per_page=500&mem_store=1&tags=10&order_by=order+by+id+desc&_=1655718810959")
                .then()
                .log().all()
                .extract().response();
        List<Map<String, String>> responseJSON = response.jsonPath().getList("messages");
        StringBuilder msg_id = new StringBuilder();
        for (Map<String, String> id : responseJSON) {
            msg_id.append(id.get("id")).append(";");
        }
        return msg_id.toString();
    }
}
