package mapper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public interface IElements {
    WebElement findBy(WebDriverWait webDriverWait, WebDriver driver);
    List<WebElement> findsBy(WebDriverWait webDriverWait, WebDriver driver);
}
