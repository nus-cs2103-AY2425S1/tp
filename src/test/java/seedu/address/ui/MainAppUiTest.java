package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.Label;
import seedu.address.MainApp;
import seedu.address.model.UserPrefs;

@ExtendWith(ApplicationExtension.class)
public class MainAppUiTest {

    @BeforeEach
    public void runAppToTests() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(() -> new MainApp());
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(20);
    }

    @AfterEach
    public void stopApp() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @Test
    public void constructor_noCustomStoragePath_usesDefaultStoragePath(FxRobot robot) {
        var locationStatusNode = robot.lookup("#saveLocationStatus").tryQuery();
        assertTrue(locationStatusNode.isPresent());
        var locationStatusNodeLabel = (Label) locationStatusNode.get();
        var userPrefs = new UserPrefs();
        assertEquals(Paths.get(".").resolve(userPrefs.getAddressBookFilePath()).toString(),
                locationStatusNodeLabel.getText()
        );
    }

}
