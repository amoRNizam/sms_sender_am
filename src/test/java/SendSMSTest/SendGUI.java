package SendSMSTest;

import DTO.SendData;
import Utils.ParserTXTFile;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import static Utils.ParserTXTFile.read;

public class SendGUI {

    // Инициализация логера
    public static final Logger log = Logger.getLogger(SendGUI.class);

    @Test
    public void main() {

        System.out.println("Заказ оформлен!");
        log.info("----------------- Начало -----------------");
        SendData sendData = read();
        System.out.println("Telephone = " + sendData.getTel_number());
        System.out.println("Text sms = " + sendData.getTextSMS());

        log.info("Номер телефона: " + sendData.getTel_number());
        log.info("Текст сообщения: " + sendData.getTextSMS());
        log.info("Сообщение успешно отправлено!");
        log.info("----------------- Конец -----------------");
    }

}
