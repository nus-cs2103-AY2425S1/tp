package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.input.Clipboard;
import javafx.stage.Stage;

public class HelpWindowTest {

    private HelpWindow helpWindow;

    @BeforeAll
    public static void initJfx() throws Exception {
        // Initialize JavaFX platform if it's not already started
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> latch.countDown());
        latch.await(2, TimeUnit.SECONDS); // Wait for JavaFX to initialize
    }

    @BeforeEach
    public void setUp() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Stage stage = new Stage();
            helpWindow = new HelpWindow(stage);
            latch.countDown();
        });
        latch.await(2, TimeUnit.SECONDS);
    }

    @Test
    public void show_helpWindowIsShown() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            assertFalse(helpWindow.isShowing());
            helpWindow.show();
            assertTrue(helpWindow.isShowing());
            latch.countDown();
        });
        latch.await(2, TimeUnit.SECONDS);
    }

    @Test
    public void hide_helpWindowIsHidden() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            helpWindow.show();
            assertTrue(helpWindow.isShowing());
            helpWindow.hide();
            assertFalse(helpWindow.isShowing());
            latch.countDown();
        });
        latch.await(2, TimeUnit.SECONDS);
    }

    @Test
    public void copyUrl_urlIsCopiedToClipboard() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            helpWindow.copyUrl();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            assertEquals(HelpWindow.USERGUIDE_URL, clipboard.getString());
            latch.countDown();
        });
        latch.await(2, TimeUnit.SECONDS);
    }
}
