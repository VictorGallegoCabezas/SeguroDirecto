package test.sd;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import test.sd.comun.BaseTest;
import test.sd.pages.AltaPolizaPage;
import test.sd.pages.InicioPage;
import test.sd.utils.MatriculaUtils;

public class SdTest extends BaseTest {
	
	private String dni  = "46291767N";
	private String nif  = "A07207244";
	private String user = "PIC2511";
	private String pass = "PPIC2511";
	
	@BeforeEach
	public void iniciarSesion() throws IOException {
		login();
	}
	
	@Test
	public void altaPolizaIndividual() throws IOException {
		AltaPolizaPage altaPolizaPage = new AltaPolizaPage(driver);		
		altaPolizaPage.navegateToAltaPoliza();
		
		altaPolizaPage.rellenarDatosGenerales(false);
		log.info("Datos generales rellenos");
		altaPolizaPage.rellenarDatosTomador(dni);
		log.info("Datos tomador rellenos. DNI: " + dni);		
		
		altaPolizaPage.rellenarDireccion();
		log.info("Direccion rellena");
		
		// La matricula la coge del fichero /SD/UltimaMatricula.txt y despues le suma 1
		altaPolizaPage.rellenarVehiculos(MatriculaUtils.incrementarYActualizarMatricula(), false);
		log.info("Vehiculo relleno");
		altaPolizaPage.botonAltaPoliza();
		log.info("Alta poliza realizada correctamente");	
	}
	
	@Test
	public void altaPolizaIndividualSinMatricula() throws IOException {
		AltaPolizaPage altaPolizaPage = new AltaPolizaPage(driver);		
		altaPolizaPage.navegateToAltaPoliza();
		
		altaPolizaPage.rellenarDatosGenerales(false);
		log.info("Datos generales rellenos");
		altaPolizaPage.rellenarDatosTomador(dni);
		log.info("Datos tomador rellenos. DNI: " + dni);		
		
		altaPolizaPage.rellenarDireccion();
		log.info("Direccion rellena");
		
		// La matricula la coge del fichero /SD/UltimaMatricula.txt y despues le suma 1
		altaPolizaPage.rellenarVehiculos(null, false);
		log.info("Vehiculo relleno");
		altaPolizaPage.botonAltaPoliza();
		log.info("Alta poliza realizada correctamente");	
	}
	
	//@Test
	public void altaPolizaOrganismosOficialesIndividual() throws IOException {
		AltaPolizaPage altaPolizaPage = new AltaPolizaPage(driver);		
		altaPolizaPage.navegateToAltaPolizaOO();
		
		altaPolizaPage.rellenarDatosGeneralesOO(false);
		log.info("Datos generales rellenos");
		altaPolizaPage.rellenarDatosTomadorOO(nif);
		log.info("Datos tomador rellenos. NIF: " + nif);		
		
		// La matricula la coge del fichero /SD/UltimaMatricula.txt y despues le suma 1
		altaPolizaPage.rellenarVehiculos(MatriculaUtils.incrementarYActualizarMatricula(), true);
		log.info("Vehiculo relleno");
		altaPolizaPage.botonAltaPoliza();
		log.info("Alta poliza realizada correctamente");	
	}
	
	//@Test
	public void altaPolizaFlota() throws IOException {
		AltaPolizaPage altaPolizaPage = new AltaPolizaPage(driver);		
		altaPolizaPage.navegateToAltaPoliza();		
		
		altaPolizaPage.rellenarDatosGenerales(true);
		log.info("Datos generales rellenos");
		altaPolizaPage.rellenarDatosTomador(dni);
		log.info("Datos tomador rellenos. DNI: " + dni);				
		
		altaPolizaPage.rellenarDireccion();
		log.info("Direccion rellena");		
		
		// La matricula la coge del fichero /SD/UltimaMatricula.txt y despues le suma 1
		altaPolizaPage.rellenarVehiculos(MatriculaUtils.incrementarYActualizarMatricula(), false);
		log.info("Vehiculo1 relleno");			
		
		altaPolizaPage.botonAltaPoliza();
		log.info("Alta poliza 1 realizada correctamente");		
		
		altaPolizaPage.añadirVehiculoAdicional(MatriculaUtils.incrementarYActualizarMatricula());
		altaPolizaPage.botonAltaPoliza();
		
		log.info("Alta poliza 2 realizada correctamente");		
	}
	
	//@Test
	public void altaPolizaFlotaSinMatricula() throws IOException {
		AltaPolizaPage altaPolizaPage = new AltaPolizaPage(driver);		
		altaPolizaPage.navegateToAltaPoliza();		
		
		altaPolizaPage.rellenarDatosGenerales(true);
		log.info("Datos generales rellenos");
		altaPolizaPage.rellenarDatosTomador(dni);
		log.info("Datos tomador rellenos. DNI: " + dni);
		
		altaPolizaPage.rellenarDireccion();
		log.info("Direccion rellena");
		
		// La matricula la coge del fichero /SD/UltimaMatricula.txt y despues le suma 1
		altaPolizaPage.rellenarVehiculos(null, true);
		log.info("Vehiculo1 relleno");			
		
		altaPolizaPage.botonAltaPoliza();
		log.info("Alta poliza 1 realizada correctamente");		
		
		altaPolizaPage.añadirVehiculoAdicional(null);
		altaPolizaPage.botonAltaPoliza();
		
		log.info("Alta poliza 2 realizada correctamente");		
	}
	
	//@Test	
	public void altaPolizaFlotaOrganismosOfiales() throws IOException {
		AltaPolizaPage altaPolizaPage = new AltaPolizaPage(driver);		
		altaPolizaPage.navegateToAltaPoliza();		
		
		altaPolizaPage.rellenarDatosGenerales(true);
		log.info("Datos generales rellenos");
		altaPolizaPage.rellenarDatosTomador(dni);
		log.info("Datos tomador rellenos. DNI: " + dni);				
		
		altaPolizaPage.rellenarDireccion();
		log.info("Direccion rellena");		
		
		// La matricula la coge del fichero /SD/UltimaMatricula.txt y despues le suma 1
		altaPolizaPage.rellenarVehiculos(MatriculaUtils.incrementarYActualizarMatricula(), false);
		log.info("Vehiculo1 relleno");			
		
		altaPolizaPage.botonAltaPoliza();
		log.info("Alta poliza 1 realizada correctamente");		
		
		altaPolizaPage.añadirVehiculoAdicional(MatriculaUtils.incrementarYActualizarMatricula());
		altaPolizaPage.botonAltaPoliza();
		
		log.info("Alta poliza 2 realizada correctamente");		
	}
	
	private void login() throws IOException {
        InicioPage inicioPage = new InicioPage(driver);        
        
        String url = "https://consorcio:CcsCast3llaNa!@apacheppro.intranet.consorseguros.es/SDProduccionTest/servlet/login.do";
        inicioPage.navegateTo(url);               
        
        inicioPage.login(user, pass);        
        inicioPage.selectRol();
    }
}