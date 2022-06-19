package Utils;

import DTO.SendData;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.fail;

public class ParserTXTFile {

    // Инициализация логера
    public static final Logger log = Logger.getLogger(ParserTXTFile.class);

    public static SendData read(String filePath) {

        try {
            File devFile = new File(filePath);
            Scanner devScanner = new Scanner(devFile, "UTF-8");
            SendData sendData = new SendData();
            while (devScanner.hasNext()) {
                String nextLine = devScanner.nextLine();
                String[] fileData = nextLine.split(";");
//                sendData.setTel_number((Long.parseLong(fileData[0])));
                sendData.setTel_number(fileData[0]);
                sendData.setTextSMS(fileData[1]);
                return sendData;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
            log.error(e);
            log.info("----------------- Конец -----------------");
            fail();
        }
        return null;
    }
}
