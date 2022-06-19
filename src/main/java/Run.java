import DTO.SendData;
import org.apache.log4j.Logger;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static Utils.ParserTXTFile.read;
import static java.nio.charset.StandardCharsets.*;

public class Run {

    // Инициализация логера
    public static final Logger log = Logger.getLogger(Run.class);

    public static void main(String[] args) {
        System.setProperty("path", "src/main/resources/file_send.txt");
        String path = System.getProperty("path");
        log.info("----------------- Начало -----------------");
        log.info(String.format("Имя файла: '%s'", path));
        SendData sendData = read(path);

        log.info("Номер телефона: " + sendData.getTel_number());
        log.info("Текст сообщения: " + sendData.getTextSMS());

        Controller controller = new Controller();
        if (controller.sendSMS(sendData.getTel_number(), sendData.getTextSMS())) {
            log.info("Сообщение успешно отправлено!");
        } else log.info("Сообщение не отправлено!");

        File file = new File(path);
        if(file.delete()){
            log.info(String.format("Файл '%s' удален.", path));
        }else log.info(String.format("Файл '%s' не обнаружен.", path));

//      TODO здесь вызов метода удаления истории сообщений на сим карте
        log.info("История сообщений удалена!");

        log.info("----------------- Конец -----------------");


        String sms = EncodingToolkit.encode("1234567890");
        String sms2 = EncodingToolkit.decode("3132333435363738393000");
        System.out.println(sms);
        System.out.println(sms2);
    }
}
