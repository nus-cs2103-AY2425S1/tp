package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.MouseEvent;

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

        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("HelpWindow initialization timed out");
        }
    }

    @Test
    public void testOpenUserGuide() throws Exception {
        MouseEvent event = mock(MouseEvent.class);

        assertDoesNotThrow(() -> helpWindow.openUserGuide(event));
    }
}
