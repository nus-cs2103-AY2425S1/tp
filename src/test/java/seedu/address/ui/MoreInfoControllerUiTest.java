package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import seedu.address.MainApp;

public class MoreInfoControllerUiTest extends ApplicationTest {
    private MainApp app;

    @BeforeEach
    public void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
        app = new MainApp();
        FxToolkit.setupApplication(() -> app);
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(20);
        FxRobot robot = new FxRobot();
        robot.clickOn("#commandBoxPlaceholder");
        robot.write("clear");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#commandBoxPlaceholder");
        robot.write("buyer n/Jackson p/98294924 e/jackson@gmail.com");
        robot.type(KeyCode.ENTER);
    }
    @AfterEach
    public void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }
    @Test
    public void openMoreInfoWindowUponCommandAndCloses_success() {
        FxRobot robot = new FxRobot();
        robot.clickOn("#commandBoxPlaceholder");
        robot.write("moreinfo n/Jackson");
        robot.type(KeyCode.ENTER);
        assertTrue(robot.lookup("#remarkInput").tryQuery().isPresent(),
                "Command opens more info window successfully");
        robot.type(KeyCode.ESCAPE);
        assertTrue(robot.lookup("#remarkInput").tryQuery().isEmpty(),
                "More info window closes successfully");
    }

    @Test
    public void handleRemarkInputCorrectly_success() {
        FxRobot robot = new FxRobot();
        robot.clickOn("#commandBoxPlaceholder");
        robot.write("moreinfo n/Jackson");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#remarkInput");
        robot.write("Test");
        robot.type(KeyCode.ENTER);
        Label remark = robot.lookup("#clientRemarksLabel").query();
        assertEquals("Test", remark.getText(), "The remark should be correctly set to 'Test'");
    }
}
