package test.sd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import test.sd.comun.BasePage;

public class AltaPolizaPage extends BasePage {

	public AltaPolizaPage(WebDriver driver) {
		super(driver);		
	}
	
	public void navegateToAltaPoliza() {
        
        // 1. Desplegar el menú "Particulares" (id="menu0" según tu HTML)
        WebElement menuParticulares = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("menu0"))
        );
        menuParticulares.click();
            
        // 2. Hacer clic en la opción "Pólizas" dentro del desplegable abierto
        WebElement enlacePolizas = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/SDProduccionTest/servlet/particulares/polizas.do']"))
        );
        enlacePolizas.click();        
        
        // 3. Una vez en la pantalla de Pólizas, presionar el botón de "Alta"
        // Localizar el botón usando el atributo personalizado 'tipo'
        WebElement botonAlta = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[tipo='PNPARPLZ.ALT_POL']"))
        );

        // Si el contenedor padre tiene style="display: none;", forzamos su visibilidad antes de hacer clic
        ((JavascriptExecutor) driver).executeScript("arguments[0].parentElement.style.display = 'block';", botonAlta);

        // Hacer el clic nativo
        botonAlta.click();
        
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("ayudaEntidadAseguradoras1"))
            );
    }
	
	public void navegateToAltaPolizaOO() {
        
        // 1. Desplegar el menú "Particulares" (id="menu0" según tu HTML)
        WebElement menuParticulares = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("menu2"))
        );
        menuParticulares.click();
            
        // 2. Hacer clic en la opción "Pólizas" dentro del desplegable abierto
        WebElement enlacePolizas = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/header/div[2]/nav/div/ul/li[4]/ul/li[2]/a[2]"))
        );
        enlacePolizas.click();
        //-------------------------
        // 1. Esperar a que el botón esté visible e interactuable
        WebElement botonAltaOficiales = wait.until(
            ExpectedConditions.elementToBeClickable(By.cssSelector("button[tipo='PNOFIPLZ.ALT_POL']"))
        );

        // 2. Hacer scroll por seguridad para centrarlo en la pantalla
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", botonAltaOficiales);

        // 3. Hacer el clic
        botonAltaOficiales.click();
        
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("cbFlota"))
            );
    }
	
	public void rellenarDatosGenerales(boolean flota){
		
		if (flota) {			
			WebElement selectFlotaElement = wait.until(
			    ExpectedConditions.elementToBeClickable(By.id("cbFlota"))
			);

			// 2. Instanciar Select y seleccionar por el atributo value ("F")
			Select dropdownFlota = new Select(selectFlotaElement);
			dropdownFlota.selectByValue("F");
		}
		
		
		WebElement entidad1Ayuda = wait.until(
			    ExpectedConditions.elementToBeClickable(By.id("ayudaEntidadAseguradoras1"))
			);			
			
		entidad1Ayuda.click();		
		
		WebElement mapfre = wait.until(
			    ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/div[3]/div/div/div[6]/div/table/tbody/tr[5]/td/a"))
			);
			
		mapfre.click();
		//----------------------------		
		WebElement entidad2Ayuda = wait.until(
			    ExpectedConditions.elementToBeClickable(By.id("ayudaEntidadAseguradoras2"))
			);			
			
		entidad2Ayuda.click();		
		
		WebElement generali = wait.until(
			    ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/div[3]/div/div/div[6]/div/table/tbody/tr[6]/td/a"))
			);
			
		generali.click();		
		
	}
	
	public void rellenarDatosTomador(String dni) {
		WebElement dniCampo = wait.until(
			    ExpectedConditions.elementToBeClickable(By.id("tomadorClave1"))
			);			
			
		dniCampo.sendKeys(dni);
		
		WebElement validarBoton = wait.until(
			    ExpectedConditions.elementToBeClickable(By.id("btnValidarParticipes"))
			);

		guardarCaptura("Datos tomador");
		validarBoton.click();
		
		wait.until(
		        ExpectedConditions.visibilityOfElementLocated(By.id("listaDireccionesTomador"))
		    );		
	}
	
	public void rellenarDatosTomadorOO(String nif) {
		WebElement dniCampo = wait.until(
			    ExpectedConditions.elementToBeClickable(By.id("tomadorClave1"))
		);			
		dniCampo.sendKeys(nif);
		//---------------------
		WebElement ayudaSubOrganismoBoton = wait.until(
			    ExpectedConditions.elementToBeClickable(By.id("ayudaCodigoSuborganismoAPT"))
		);
		ayudaSubOrganismoBoton.click();		
		//---------------------
		// 1. Esperar a que el elemento localizado por el XPath sea completamente interactuable
		WebElement elementoFila = wait.until(
		    ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/div[3]/div/div/div[6]/div/table/tbody/tr/td[1]/a"))
		);

		// 2. Hacer scroll hasta el elemento por si queda fuera de la pantalla o tapado
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementoFila);

		// 3. Hacer clic
		elementoFila.click();
		//---------------------
		WebElement validarBoton = wait.until(
			    ExpectedConditions.elementToBeClickable(By.id("btnValidarParticipes"))
		);
		guardarCaptura("Datos tomador");
		validarBoton.click();
		//---------------------
		wait.until(
	        ExpectedConditions.visibilityOfElementLocated(By.id("txMarca"))
	    );		
	}
	
	public void rellenarDireccion() {
	    WebElement selectElement = wait.until(
	        ExpectedConditions.presenceOfElementLocated(By.id("listaDireccionesTomador"))
	    );

	    Select selectDireccion = new Select(selectElement);
	    selectDireccion.selectByIndex(1);
	    
	    WebElement validarBoton = wait.until(
	        ExpectedConditions.elementToBeClickable(By.id("btnValidarParticipes"))
	    );
	    
	    guardarCaptura("Datos dirección");

	    validarBoton.click();
	    
	    wait.until(ExpectedConditions.elementToBeClickable(By.id("ayudaMarcas")));
	}
	
	public void rellenarVehiculos(String matricula, boolean organismoOficial) {
	    
	    WebElement marcaAyuda = wait.until(
	            ExpectedConditions.elementToBeClickable(By.id("ayudaMarcas"))
	        );          
	        
	    marcaAyuda.click();     
	    
	    WebElement abarth = wait.until(
	            ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/div[3]/div/div/div[6]/div/table/tbody/tr[7]/td/a"))
	        );
	        
	    abarth.click();
	    //-------------------------     
	    WebElement marcaModelo = wait.until(
	            ExpectedConditions.elementToBeClickable(By.id("ayudaModelos"))
	        );          
	        
	    marcaModelo.click();        
	    
	    WebElement puntoModelo = wait.until(
	            ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/div[3]/div/div/div[6]/div/table/tbody/tr[3]/td[1]/a"))
	        );
	        
	    puntoModelo.click();
	    //-------------------------         
	    
	    // 1. Esperar explícitamente a que el option con valor 'SU' esté presente dentro de cbTipoUso
	    wait.until(ExpectedConditions.presenceOfElementLocated(
	        By.xpath("//select[@id='cbTipoUso']/option[@value='SU']")
	    ));

	    // 2. Re-localizar el select justo antes de instanciar la clase Select
	    WebElement selectTipoUso = wait.until(
	        ExpectedConditions.elementToBeClickable(By.id("cbTipoUso"))
	    );

	    Select dropdownUso = new Select(selectTipoUso);
	    dropdownUso.selectByValue("SU");
	    
	    //-------------------------
	    if (matricula != null) {	    
		    rellenarConMatricula(matricula);
	    } else {
	    	rellenarSinMatricula();
	    }
	    
	    //-------------------------
	    WebElement fechaPermisoCampo = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("txFechaPermiso"))
        );          
	        
	    fechaPermisoCampo.sendKeys("07-09-2026");
	    //-------------------------
	    if (!organismoOficial) {
		    WebElement permisoConducirCampo = wait.until(
	            ExpectedConditions.elementToBeClickable(By.id("permisoConducir"))
	        );          
	        
		    permisoConducirCampo.sendKeys("B");
	    }
	    //-------------------------
	    WebElement validarVehiculoBoton = wait.until(
	            ExpectedConditions.presenceOfElementLocated(By.id("btnValidarDatosVehiculo"))
	        );      
	    guardarCaptura("Datos vehículo");
	    
	    validarVehiculoBoton.click();       
	}	
	
	public void botonAltaPoliza() {
	    // 1. Esperar a que el elemento sea clickeable e interactuar inmediatamente
	    WebElement boton = wait.until(
	        ExpectedConditions.elementToBeClickable(By.id("buttonAltaPoliza"))
	    );		
	    
	    // Scroll por seguridad para asegurar que el viewport no lo tape
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", boton);
	    
	    boton.click();
	    
	    //---------------------------------------
	    //localizamos el elemento que confirma que esta bien y guardamos pantalla
    	By locatorParcial = By.cssSelector("button[tipo='PNPARPLZ.ALT_POL']");
        By locatorOficial = By.cssSelector("button[tipo='PNOFIPLZ.ALT_POL']");

        // Espera a que cualquiera de los dos esté listo
        wait.until(
            ExpectedConditions.or(
                ExpectedConditions.elementToBeClickable(locatorParcial),
                ExpectedConditions.elementToBeClickable(locatorOficial),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alerta-exito"))
            )
        );

        guardarCaptura("Alta ok");
	}
	
	public void añadirVehiculoAdicional(String matricula) {
	    // 1. Esperar a que el acordeón/desplegable sea interactuable y hacer clic
	    WebElement datosVehiculoDesplegable = wait.until(
	        ExpectedConditions.elementToBeClickable(By.id("divDesplegableVehiculo"))
	    );		
	    datosVehiculoDesplegable.click();
	    
	    if (matricula != null) {
		    rellenarConMatricula(matricula);
	    } else {
	    	rellenarSinMatricula();	    	
	    }
	    
	    // 5. Esperar a que el botón de validar sea interactuable
	    WebElement validarVehiculoBoton = wait.until(
	        ExpectedConditions.elementToBeClickable(By.id("btnValidarDatosVehiculo"))
	    );      
	    
	    validarVehiculoBoton.click();
	    guardarCaptura("Guardar vehículo adicional");
	}
	
	public void rellenarDatosGeneralesOO(boolean flotas) {
		if(flotas) {
			// 1. Esperar a que el selector de flota esté interactuable
			WebElement selectFlotaElement = wait.until(
			    ExpectedConditions.elementToBeClickable(By.id("cbFlota"))
			);

			// 2. Instanciar Select y seleccionar la opción "Flota" mediante su value ("F")
			Select dropdownFlota = new Select(selectFlotaElement);
			dropdownFlota.selectByValue("F");
		}
	}

	private void rellenarSinMatricula() {
		WebElement selectConMatriculaElement = wait.until(
		    ExpectedConditions.elementToBeClickable(By.id("cbConMatricula"))
		);
		
		// Seleccionar la opción
		Select dropdownConMatricula = new Select(selectConMatriculaElement);
		dropdownConMatricula.selectByValue("N");

		// Forzar la ejecución del evento onchange si la página no reacciona
		((JavascriptExecutor) driver).executeScript("gestionConMatricula();");
		
		// 2. Esperar a que el campo de matrícula sea VISIBLE (no solo que esté en el DOM)	    
		WebElement numeroSerieCampo = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.id("txtNumeroSerie"))
		);
		// 3. Hacer scroll hasta el elemento por si queda oculto bajo algún banner o fuera del viewport
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", numeroSerieCampo);	

		// 4. Limpiar el campo e ingresar la nueva matrícula
		numeroSerieCampo.clear();
		numeroSerieCampo.sendKeys("123456789ABC");
	}
	
	private void rellenarConMatricula(String matricula) {
		By txtMatriculaLocator = By.id("txtMatricula");

		// 1. Esperar a que sea totalmente interactuable
		WebElement txtMatricula = wait.until(ExpectedConditions.elementToBeClickable(txtMatriculaLocator));

		// 2. Hacer scroll si es necesario y hacer click para ganar el foco
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", txtMatricula);
		txtMatricula.click();
		txtMatricula.clear();

		// 3. Enviar el texto
		txtMatricula.sendKeys(matricula);
	}
	
}
