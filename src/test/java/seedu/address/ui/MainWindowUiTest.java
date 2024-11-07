package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.input.KeyCode;
import seedu.address.MainApp;

class MainWindowUiTest extends ApplicationTest {
    private MainApp app;
    @BeforeEach
    public void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
        app = new MainApp();
        FxToolkit.setupApplication(() -> app);
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(20);
    }

    @Test
    void handleHelp_handlesHelpCommandCorrectly() {
        FxRobot robot = new FxRobot();
        robot.clickOn("#commandTextField");
        robot.write("help");
        robot.type(KeyCode.ENTER);
        assertTrue(robot.lookup("#helpMessage").query().isVisible());
    }
}
