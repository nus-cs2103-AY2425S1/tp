package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.ui.CommandBox.ERROR_STYLE_CLASS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import seedu.address.MainApp;

@ExtendWith(ApplicationExtension.class)
public class CommandBoxUiTest extends ApplicationTest {
    private MainApp app;
    private final List<String> testCommandHistory = new ArrayList<>(Arrays.asList(
            "showclients",
            "buyer n/SOMEONE THAT WILL NEVER EXIST p/99999999 e/seanisthebest@gmail.com",
            "deleteclient SOMEONE THAT WILL NEVER EXIST",
            "showlistings"
    ));

    @BeforeEach
    public void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
        app = new MainApp();
        FxToolkit.setupApplication(() -> app);
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(20);
    }

    @AfterEach
    public void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @Test
    public void commandNavigationWorks_success() {
        FxRobot robot = new FxRobot();

        assertNotNull(robot.lookup("#commandTextField"), "Command box should be present.");

        for (String command : testCommandHistory) {
            robot.clickOn("#commandTextField");
            robot.write(command);
            robot.type(KeyCode.ENTER);
            TextField commandTextField = robot.lookup("#commandTextField").query();
            commandTextField.setText("");
        }
        Collections.reverse(testCommandHistory);
        for (String command : testCommandHistory) {
            robot.type(KeyCode.UP);
            TextField actualCommand = robot.lookup("#commandTextField").query();
            assertEquals(command, actualCommand.getText());
        }
        Collections.reverse(testCommandHistory);
        for (String command : testCommandHistory) {
            TextField actualCommand = robot.lookup("#commandTextField").query();
            assertEquals(command, actualCommand.getText());
            robot.type(KeyCode.DOWN);
        }
    }
    @Test
    public void setStyleToIndicateCommandFailure_addsErrorStyleClass() {
        FxRobot robot = new FxRobot();

        assertNotNull(robot.lookup("#commandTextField"), "Command box should be present.");
        robot.clickOn("#commandTextField");
        robot.write("UNKNOWN COMMMAND");
        robot.type(KeyCode.ENTER);
        assertTrue(robot.lookup("#commandTextField").query().getStyleClass().contains(ERROR_STYLE_CLASS));
    }

}
