package test.sd.comun;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static Logger log = Logger.getLogger("EvidenciasLogger");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public String getTitle() {
        return this.driver.getTitle();
    }

    @Step("Navegar a la URL: {url}")
    public void navegateTo(String url) {
        log.info("Navegando a: " + url);
        driver.get(url);
    }

    public void guardarCaptura() {
        guardarCaptura("Captura_" + System.currentTimeMillis());
    }

    @Step("Captura: {descripcionPaso}")
    public void guardarCaptura(String descripcionPaso) {
        if (driver == null) {
            log.warning("Driver nulo, no se puede tomar la captura: " + descripcionPaso);
            return;
        }

        try {
            byte[] screenshotBytes = tomarCapturaPaginaCompleta();

            // 1. Incrustar en Allure
            Allure.addAttachment(descripcionPaso, "image/png", new ByteArrayInputStream(screenshotBytes), ".png");

            // 2. Guardar en carpeta local
            String rutaCarpetaTest = System.getProperty("rutaCarpetaTest");
            if (rutaCarpetaTest != null && !rutaCarpetaTest.isEmpty()) {
                String nombreLimpio = descripcionPaso.replaceAll("[^a-zA-Z0-9._-]", "_");
                File destino = new File(rutaCarpetaTest + File.separator + System.currentTimeMillis() + "_" + nombreLimpio + ".png");
                FileUtils.writeByteArrayToFile(destino, screenshotBytes);
            }

            log.info("Captura de página completa adjuntada: " + descripcionPaso);

        } catch (Exception e) {
            log.warning("Error al tomar captura de página completa, usando captura de vista actual: " + e.getMessage());
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment(descripcionPaso, "image/png", new ByteArrayInputStream(screenshot), ".png");
            } catch (Exception ex) {
                log.warning("Error crítico en captura: " + ex.getMessage());
            }
        }
    }

    /**
     * Captura la página completa haciendo un barrido por coordenadas de pantalla,
     * ocultando elementos fijos (fixed/sticky) y cosiendo las imágenes manualmente en Java.
     */
    private byte[] tomarCapturaPaginaCompleta() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Script para ocultar elementos con posición fija que ensucian la captura al hacer scroll
        String ocultarFijosScript = 
            "var elems = document.querySelectorAll('*');" +
            "window._fixedElems = [];" +
            "for (var i = 0; i < elems.length; i++) {" +
            "  var pos = window.getComputedStyle(elems[i]).getPropertyValue('position');" +
            "  if (pos === 'fixed' || pos === 'sticky') {" +
            "    window._fixedElems.push({elem: elems[i], orig: elems[i].style.visibility});" +
            "    elems[i].style.visibility = 'hidden';" +
            "  }" +
            "}";

        String restaurarFijosScript = 
            "if (window._fixedElems) {" +
            "  for (var i = 0; i < window._fixedElems.length; i++) {" +
            "    window._fixedElems[i].elem.style.visibility = window._fixedElems[i].orig;" +
            "  }" +
            "}";

        try {
            // 1. Obtener dimensiones
            Long totalHeight = (Long) js.executeScript(
                "return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight, " +
                "document.body.offsetHeight, document.documentElement.offsetHeight);"
            );
            Long viewportHeight = (Long) js.executeScript("return window.innerHeight;");

            if (totalHeight <= viewportHeight) {
                // Si la página cabe entera en pantalla, tomar captura estándar
                return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            }

            List<BufferedImage> capturas = new ArrayList<>();
            int currentScroll = 0;

            // Guardar posición inicial del scroll
            js.executeScript("window.scrollTo(0, 0);");

            // 2. Iterar por la pantalla tomando fotos
            while (currentScroll < totalHeight) {
                js.executeScript("window.scrollTo(0, " + currentScroll + ");");
                
                // Ocultar barras/cabeceras fijas a partir de la segunda captura
                if (currentScroll > 0) {
                    js.executeScript(ocultarFijosScript);
                }

                Thread.sleep(300); // Espera breve para asegurar renderizado

                byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
                capturas.add(image);

                currentScroll += viewportHeight;
            }

            // Restaurar visibilidad de elementos fijos y scroll original
            js.executeScript(restaurarFijosScript);
            js.executeScript("window.scrollTo(0, 0);");

            // 3. Unir (coser) las imágenes horizontal/verticalmente
            int width = capturas.get(0).getWidth();
            int heightPorCaptura = capturas.get(0).getHeight();
            int totalImagenHeight = heightPorCaptura * capturas.size();

            BufferedImage imagenFinal = new BufferedImage(width, totalImagenHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = imagenFinal.createGraphics();

            int yActual = 0;
            for (BufferedImage img : capturas) {
                g2d.drawImage(img, 0, yActual, null);
                yActual += heightPorCaptura;
            }
            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imagenFinal, "png", baos);
            return baos.toByteArray();

        } catch (Exception e) {
            log.warning("Fallo en la captura manual por bloques: " + e.getMessage());
            // Restaurar visibilidad por si acaso falló a mitad de proceso
            try { js.executeScript(restaurarFijosScript); } catch (Exception ignored) {}
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
    }

    public void registrarInfo(String mensaje) {
        log.info(mensaje);
        Allure.addAttachment("Log", "text/plain", mensaje);
    }

    @Step("Adjuntar archivo: {nombreAdjunto}")
    public void adjuntarArchivo(File archivo, String nombreAdjunto) {
        if (archivo != null && archivo.exists()) {
            try (FileInputStream fis = new FileInputStream(archivo)) {
                Allure.addAttachment(nombreAdjunto, fis);
                log.info("Archivo adjuntado a Allure: " + archivo.getAbsolutePath());
            } catch (IOException e) {
                log.warning("Error al adjuntar archivo a Allure: " + e.getMessage());
            }
        }
    }

    protected WebElement esperarVisibilidad(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}