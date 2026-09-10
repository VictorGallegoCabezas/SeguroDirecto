package test.sd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import test.sd.comun.BasePage;

public class InicioPage extends BasePage {

	public InicioPage(WebDriver driver) {
		super(driver);		
	}
	
	public void login(String user, String pass) {
		
		WebElement userCampo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("usuario"))
        );
		
		WebElement passCampo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("password"))
        );
		
		WebElement botonEntrar = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("buttonSubmit"))
        );
		        
        userCampo.sendKeys(user);
        passCampo.sendKeys(pass);

        botonEntrar.click();	
		
	}
	
	public void selectRol() {
		// Localizar el elemento por XPath y esperar a que sea interactuable
		WebElement enlace = wait.until(
		    ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/div/form/ul/li[1]/a"))
		);

		// Hacer clic
		enlace.click();
	}

}
