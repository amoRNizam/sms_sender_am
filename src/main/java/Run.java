import DTO.SendData;
import org.apache.log4j.Logger;

import java.io.File;

import static Utils.ParserTXTFile.read;

public class Run {

    // Инициализация логера
    public static final Logger log = Logger.getLogger(Run.class);

    public static void main(String[] args) {
        System.setProperty("path", "src/main/resources/file_send.txt");
        // Получение путь к файлу из переменной переданной в команде запуска
        String path = System.getProperty("path");

        log.info("----------------- Начало -----------------");
        log.info(String.format("Имя файла: '%s'", path));
        // Чтение файла и сериализация данных
        SendData sendData = read(path);

        log.info("Номер телефона: " + sendData.getTel_number());
        log.info("Текст сообщения: " + sendData.getTextSMS());

        // Выполнение запроса отправки сообщения
        // текст сообщения должен быть преобразован в формат 7битный GSM либо UTF-16BE, это реализовано в классе EncodingToolkit
        Controller controller = new Controller();
        if (controller.sendSMS(sendData.getTel_number(), EncodingToolkit.encodeAsUcs2(sendData.getTextSMS()))) {
            log.info("Сообщение успешно отправлено!");
        } else log.info("Сообщение не отправлено!");

        // Удаление файла с номером телефона и текстом сообщения
        File file = new File(path);
        if(file.delete()){
            log.info(String.format("Файл '%s' удален.", path));
        }else log.info(String.format("Файл '%s' не обнаружен.", path));

//         Выполнение запроса на удаление истории сообщений
        if (controller.deleteHistory()) {
            log.info("История сообщений удалена!");
        } else log.info("Не удалось удалить историю сообщений!");

        log.info("----------------- Конец -----------------");
    }
}
