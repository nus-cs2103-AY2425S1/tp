package systemtests;

import java.nio.file.Path;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import org.testfx.api.FxToolkit;

import guitests.guihandles.MainWindowHandle;
import javafx.stage.Stage;
import spleetwaise.address.model.ReadOnlyAddressBook;

/**
 * TestFxAppSetup sets up and manages the TestApp for GUI testing using the TestFX framework. It provides methods for
 * initializing the application, setting up the main window handle, and cleaning up stages after testing.
 */
public class TestFxAppSetup {

    /** Instance of the TestApp used for testing. */
    private TestApp testApp;
    /** Handle for interacting with the main window of the TestApp. */
    private MainWindowHandle mainWindowHandle;

    /**
     * Initializes the primary stage for the TestFX toolkit. It registers the primary stage and hides it to prevent
     * immediate display.
     *
     * @throws AssertionError   if the setup takes too long.
     * @throws RuntimeException if any other exception occurs during setup.
     */
    public static void initialize() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            throw new AssertionError("Application takes too long to setup.", e);
        }
    }

    /**
     * Sets up the TestApp with the provided address book, storage path, and user preferences path.
     *
     * @param addressBook  A supplier providing the initial address book data.
     * @param storagePath  Path to the storage file.
     * @param userPrefPath Path to the user preferences file.
     * @return The initialized {@code TestApp} instance.
     * @throws AssertionError if the application setup takes too long.
     */
    public TestApp setupApp(Supplier<ReadOnlyAddressBook> addressBook, Path storagePath, Path userPrefPath) {
        try {
            FxToolkit.registerStage(Stage::new);
            FxToolkit.setupApplication(() -> testApp = new TestApp(addressBook, storagePath, userPrefPath));
        } catch (TimeoutException e) {
            throw new AssertionError("Application takes too long to setup.", e);
        }
        return testApp;
    }

    /**
     * Sets up and returns a {@code MainWindowHandle} for interacting with the main window of the TestApp. It ensures
     * that the stage is focused and displayed during testing.
     *
     * @return The {@code MainWindowHandle} for the main window.
     * @throws AssertionError if the stage setup takes too long.
     */
    public MainWindowHandle setupMainWindowHandle() {
        try {
            FxToolkit.setupStage((stage) -> {
                mainWindowHandle = new MainWindowHandle(stage);
                mainWindowHandle.focus();
            });
            FxToolkit.showStage();
        } catch (TimeoutException te) {
            throw new AssertionError("Stage takes too long to set up.", te);
        }

        return mainWindowHandle;
    }

    /**
     * Cleans up all stages used by the TestFX toolkit after testing.
     *
     * @throws AssertionError if the cleanup process takes too long.
     */
    public void stopApp() {
        try {
            FxToolkit.cleanupStages();
        } catch (TimeoutException e) {
            throw new AssertionError("Stage takes too long to clean up.", e);
        }
    }
}
