package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import seedu.address.MainApp;

public class StatusBarFooterUiTest extends ApplicationTest {
    private MainApp app;

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
    public void clickOnChatButton_success() {
        FxRobot robot = new FxRobot();
        robot.clickOn("#chatButton");
        robot.write("hello");
        robot.type(KeyCode.ENTER);
        TextArea chatArea = lookup("#chatArea").query();
        String expected = "You: hello\n"
                + "Assistant: Hi there! How can I assist you today?";
        waitFor(Duration.seconds(1));
        assertEquals(expected, chatArea.getText().trim());
    }

    private void waitFor(Duration duration) {
        try {
            Thread.sleep((long) duration.toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
