package seedu.address.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.scene.input.Clipboard;
import javafx.stage.Stage;

public class HelpWindowTest {

    private HelpWindow helpWindow;

    @BeforeEach
    public void setUp() throws Exception {
        // Create JavaFX application thread manually
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Stage stage = new Stage();
            helpWindow = new HelpWindow(stage);
            latch.countDown();
        });
        latch.await(2, TimeUnit.SECONDS); // Wait for initialization
    }

    @Test
    public void show_helpWindowIsShown() throws Exception {
        // Use latch to synchronize with JavaFX thread
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Ensure the help window is not shown initially
            assertFalse(helpWindow.isShowing());

            // Show the help window
            helpWindow.show();

            // Verify that the help window is now shown
            assertTrue(helpWindow.isShowing());

            latch.countDown();
        });
        latch.await(2, TimeUnit.SECONDS); // Wait for test completion
    }

    @Test
    public void hide_helpWindowIsHidden() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Show the help window first
            helpWindow.show();

            // Verify that the help window is shown
            assertTrue(helpWindow.isShowing());

            // Hide the help window
            helpWindow.hide();

            // Verify that the help window is now hidden
            assertFalse(helpWindow.isShowing());

            latch.countDown();
        });
        latch.await(2, TimeUnit.SECONDS); // Wait for test completion
    }

    @Test
    public void copyUrl_urlIsCopiedToClipboard() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Call the copyUrl method
            helpWindow.copyUrl();

            // Get the current clipboard content
            Clipboard clipboard = Clipboard.getSystemClipboard();

            // Verify that the URL was copied to the clipboard
            assertEquals(HelpWindow.USERGUIDE_URL, clipboard.getString());

            latch.countDown();
        });
        latch.await(2, TimeUnit.SECONDS); // Wait for test completion
    }
}
