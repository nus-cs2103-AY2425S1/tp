package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class HelpWindowTest {

    private HelpWindow helpWindow;

    @BeforeEach
    public void setUp() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            helpWindow = new HelpWindow();
            latch.countDown();
        });

        // Wait for the JavaFX initialization
        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("HelpWindow initialization timed out");
        }
    }

    @Test
    public void testHyperlinkTextProperties() throws InterruptedException {
        // Make sure the assertions run on the JavaFX Application Thread
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Color expectedColor = Color.rgb(173, 216, 230);
                assertEquals(expectedColor, helpWindow.getHyperlinkTextFill());
                assertTrue(helpWindow.isHyperlinkTextUnderlined());
            } finally {
                latch.countDown();
            }
        });

        // Wait for the assertions to complete
        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Assertions timed out");
        }
    }

    @Test
    public void testOpenUserGuide() throws Exception {
        MouseEvent event = mock(MouseEvent.class);

        // Ensure that openUserGuide runs on the JavaFX Application Thread
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                assertDoesNotThrow(() -> helpWindow.openUserGuide(event));
            } finally {
                latch.countDown();
            }
        });

        // Wait for the assertions to complete
        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Assertions timed out");
        }
    }
}
