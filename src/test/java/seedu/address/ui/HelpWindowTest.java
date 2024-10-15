package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class HelpWindowTest {

    private static final String USERGUIDE_URL = HelpWindow.USERGUIDE_URL;
    private HelpWindow helpWindow;

    @BeforeEach
    public void setUp() throws InterruptedException {
        new JFXPanel();

        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            helpWindow = new HelpWindow();
            latch.countDown();
        });

        latch.await(2, TimeUnit.SECONDS);
    }

    @Test
    public void testHyperlinkTextProperties() {
        assertNotNull(helpWindow);

        Platform.runLater(() -> {
            Color expectedColor = Color.rgb(173, 216, 230);
            assertEquals(expectedColor, helpWindow.getHyperlinkTextFill());

            assertTrue(helpWindow.isHyperlinkTextUnderlined());
        });
    }

    @Test
    public void testOpenUserGuide() throws Exception {
        MouseEvent event = mock(MouseEvent.class);

        assertDoesNotThrow(() -> helpWindow.openUserGuide(event));
    }
}
