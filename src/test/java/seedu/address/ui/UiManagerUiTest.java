package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.ModelManager;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonListingsStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

/**
 * Tests for the UiManager class, using TestFX for JavaFX-based testing.
 */
@ExtendWith(ApplicationExtension.class)
public class UiManagerUiTest extends ApplicationTest {
    @TempDir
    public Path temporaryFolder;
    private UiManager uiManager;

    @Override
    public void start(Stage stage) {
        Logic logic = new LogicManager(new ModelManager(), new StorageManager(
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json")),
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json")),
                new JsonListingsStorage(temporaryFolder.resolve("listings.json")))
        );
        uiManager = new UiManager(logic);
        uiManager.start(stage);
    }

    @Test
    public void showAlertDialogAndWait_validInput_alertDisplayed() {
        CountDownLatch latch = new CountDownLatch(1);

        assertDoesNotThrow(() ->
                Platform.runLater(() -> {
                    try {
                        uiManager.showAlertDialogAndWait(Alert.AlertType.INFORMATION,
                                "Test Title", "Test Header", "Test Content");
                    } finally {
                        latch.countDown();
                    }
                })
        );
        push(KeyCode.ENTER);
        assertDoesNotThrow(() -> latch.await(), "Dialog display did not complete as expected.");
    }
}
