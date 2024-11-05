package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.application.Platform;
import javafx.scene.input.Clipboard;
import javafx.stage.Stage;

public class HelpWindowUiTest extends ApplicationTest {

    private HelpWindow helpWindow;

    @Override
    public void start(Stage stage) {
        helpWindow = new HelpWindow(stage);
    }

    @AfterEach
    void tearDown() throws Exception {
        FxToolkit.cleanupStages();
    }

    @Test
    void showMethod_helpWindowIsVisible_success() {
        interact(() -> helpWindow.show());
        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(helpWindow.isShowing(), "Help window should be visible after show() is called.");
    }

    @Test
    void hideMethod_helpWindowIsNotVisible_success() {
        interact(() -> {
            helpWindow.show();
            helpWindow.hide();
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertFalse(helpWindow.isShowing(), "Help window should not be visible after hide() is called.");
    }

    @Test
    void focusMethod_helpWindowIsFocused_success() {
        interact(() -> {
            helpWindow.show();
            helpWindow.focus();
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(helpWindow.getRoot().isFocused(), "Help window should be focused after focus() is called.");
    }

    @Test
    void initialize_helpMessageIsSetCorrectly_success() {
        interact(() -> helpWindow.show());
        WaitForAsyncUtils.waitForFxEvents();

        String expectedMessage = HelpWindow.HELP_MESSAGE;
        String actualMessage = lookup("#helpMessage").queryLabeled().getText();
        assertEquals(expectedMessage, actualMessage, "Help message should be set to the correct value.");
    }

    @Test
    void copyUrlMethod_urlIsCopiedToClipboard_success() {
        interact(() -> helpWindow.show());
        WaitForAsyncUtils.waitForFxEvents();

        interact(() -> clickOn("#copyButton"));
        WaitForAsyncUtils.waitForFxEvents();

        // Run Clipboard check on JavaFX Application Thread
        Platform.runLater(() -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            assertTrue(clipboard.hasString(), "Clipboard should contain text after copyUrl() is called.");
            assertEquals(HelpWindow.USERGUIDE_URL, clipboard.getString(),
                    "Clipboard should contain the user guide URL.");
        });
        WaitForAsyncUtils.waitForFxEvents();
    }
}

