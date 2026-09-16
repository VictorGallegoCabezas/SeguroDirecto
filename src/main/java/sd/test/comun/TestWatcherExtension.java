package sd.test.comun;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestWatcherExtension implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Object testInstance = context.getTestInstance().orElse(null);

        if (testInstance instanceof BaseTest) {
            BaseTest baseTest = (BaseTest) testInstance;
            WebDriver driver = baseTest.getDriver();

            // Validar que el driver no sea nulo y que la sesión siga activa
            if (driver != null && ((RemoteWebDriver) driver).getSessionId() != null) {
                try {
                    // Reutilizamos la captura de página completa definida en BasePage
                    BasePage basePage = new BasePage(driver);
                    basePage.guardarCaptura("ERROR_FALLO_TEST - " + context.getDisplayName());
                } catch (Exception e) {
                    System.err.println("No se pudo tomar la captura tras el fallo: " + e.getMessage());
                }
            }
        }
        // Cerrar el navegador después de tomar la captura
        cerrarDriver(context);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        cerrarDriver(context);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        cerrarDriver(context);
    }

    @Override
    public void testDisabled(ExtensionContext context, java.util.Optional<String> reason) {
        cerrarDriver(context);
    }

    /**
     * Garantiza el cierre del navegador únicamente cuando la extensión ha terminado sus tareas.
     */
    private void cerrarDriver(ExtensionContext context) {
        context.getTestInstance().ifPresent(instance -> {
            if (instance instanceof BaseTest) {
                WebDriver driver = ((BaseTest) instance).getDriver();
                if (driver != null) {
                    try {
                        driver.quit();
                    } catch (Exception e) {
                        System.err.println("Error al cerrar el driver: " + e.getMessage());
                    }
                }
            }
        });
    }
}