package sd.test;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

public class MainRunner {

    public static void main(String[] args) {
        System.out.println("=== Iniciando ejecución de pruebas automatizadas ===");
        
        // Capturar usuario y contraseña desde los argumentos de la línea de comandos
        if (args.length >= 2) {
            System.setProperty("test.user", args[0]);
            System.setProperty("test.pass", args[1]);
            System.out.println("Usuario y contraseña configurados desde argumentos.");
        } else {
            System.out.println("No se pasaron credenciales por argumento. Se usarán los valores por defecto.");
        }

        // Crear el Launcher de JUnit Platform
        Launcher launcher = LauncherFactory.create();

        // Configurar listener para ver resumen en consola
        SummaryGeneratingListener summaryListener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(summaryListener);

        // Descubrir y seleccionar los tests dentro del paquete 'test.sd'
        // (puedes cambiar "test.sd" por el paquete específico donde estén tus clases de prueba)
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectPackage("sd.test"))
                .build();

        // Ejecutar las pruebas
        launcher.execute(request);

        // Imprimir resumen en consola
        System.out.println("=== Resultados de la Ejecución ===");
        System.out.println("Tests encontrados: " + summaryListener.getSummary().getTestsFoundCount());
        System.out.println("Tests exitosos: " + summaryListener.getSummary().getTestsSucceededCount());
        System.out.println("Tests fallidos: " + summaryListener.getSummary().getTestsFailedCount());
    }
}