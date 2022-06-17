import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

public class Main {
    public static void main(String[] args) {

        WebDriver webDriver = new ChromeDriver();
        ChromeDriver driver = (ChromeDriver) webDriver;
        DevTools devTool = webDriver.getDevTools();
        devTool.createSession();
    }
}
