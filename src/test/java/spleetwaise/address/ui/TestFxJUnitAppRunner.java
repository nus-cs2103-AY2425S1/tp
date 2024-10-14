package spleetwaise.address.ui;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.stage.Stage;
import spleetwaise.address.MainApp;

/**
 * Initializes main setup required for all GUI tests.
 */
public class TestFxJUnitAppRunner extends ApplicationTest {

    /**
     * Sets up the application for testing before each test method is executed. This method registers the primary stage
     * and initializes the application using the specified MainApp class. It also ensures that the stage is shown and
     * that any pending events are processed.
     *
     * @throws Exception if there is an error during application setup.
     */
    @BeforeEach
    public void runAppToTests() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainApp::new);
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(100);
    }

    /**
     * Cleans up after each test method is executed by closing all stages associated with the application. This ensures
     * that resources are released and any UI changes do not affect subsequent tests.
     *
     * @throws TimeoutException if there is an error during the cleanup process.
     */
    @AfterEach
    public void stopApp() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Starts the primary stage for the JavaFX application. This method is invoked by the TestFX framework to initialize
     * the stage for GUI testing. The stage is brought to the front to ensure visibility during testing.
     *
     * @param primaryStage the primary stage of the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.toFront();
    }
}
