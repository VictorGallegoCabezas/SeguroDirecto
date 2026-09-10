package test.sd.comun;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.util.ResultsUtils;

@ExtendWith(TestWatcherExtension.class)
public class BaseTest {

    protected WebDriver driver;
    protected static Logger log = Logger.getLogger("EvidenciasLogger");

    protected String rutaCarpetaTest;
    protected String downloadPath;
    protected String nombreTest;
    protected String fechaActual;
    private FileHandler fileHandler;

    @BeforeEach
    public void setUp(TestInfo testInfo) throws IOException {

        // 1. Obtener nombre del método de test actual dinámicamente
        nombreTest = testInfo.getTestMethod().isPresent() 
                ? testInfo.getTestMethod().get().getName() 
                : "test_desconocido";

        // Fecha aaaammdd
        fechaActual = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

        // Carpeta específica para ESTE test: ./evidencias/SD_YYYYMMDD/nombreDelTest
        rutaCarpetaTest = System.getProperty("user.dir")
                + File.separator + "evidencias"
                + File.separator + "SD_" + fechaActual
                + File.separator + nombreTest;

        File carpetaTest = new File(rutaCarpetaTest);
        if (!carpetaTest.exists()) {
            carpetaTest.mkdirs();
        }

        // Carpeta de descargas dentro de la carpeta del test
        downloadPath = rutaCarpetaTest + File.separator + "descargas";
        File dirDescargas = new File(downloadPath);
        if (!dirDescargas.exists()) {
            dirDescargas.mkdirs();
        } else {
            FileUtils.cleanDirectory(dirDescargas);
        }

        // Logger específico para la carpeta de este test
        inicializarLogger(rutaCarpetaTest, fechaActual);
        log.info("Iniciando test: " + nombreTest);
        log.info("Carpeta del test creada en: " + rutaCarpetaTest);

        // Configuración de metadatos en Allure para agrupar en el reporte por SD_YYYYMMDD -> nombreTest
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.getLabels().add(ResultsUtils.createParentSuiteLabel("SD_" + fechaActual));
            testResult.getLabels().add(ResultsUtils.createSuiteLabel(nombreTest));
        });

        // Configurar Chrome
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadPath);
        prefs.put("download.prompt_for_download", false);
        prefs.put("plugins.always_open_pdf_externally", true);

        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

        // Limpiar los handlers del logger para que el siguiente test empiece limpio
        if (fileHandler != null) {
            fileHandler.close();
            log.removeHandler(fileHandler);
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getRutaCarpetaTest() {
        return rutaCarpetaTest;
    }

    protected void inicializarLogger(String rutaCarpeta, String fecha) throws IOException {
        String nombreLog = "log_" + nombreTest + "_" + fecha + ".log";
        String rutaLog = rutaCarpeta + File.separator + nombreLog;

        fileHandler = new FileHandler(rutaLog, true);
        fileHandler.setFormatter(new MiFormatoLog());

        // Eliminar handlers antiguos que hayan podido quedar
        for (Handler h : log.getHandlers()) {
            log.removeHandler(h);
        }

        log.addHandler(fileHandler);
        log.setUseParentHandlers(false);
    }

    public static class MiFormatoLog extends java.util.logging.Formatter {
        @Override
        public String format(java.util.logging.LogRecord record) {
            String fecha = java.time.LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            return fecha + "  "
                    + record.getLevel() + "  "
                    + record.getMessage() + System.lineSeparator();
        }
    }
}