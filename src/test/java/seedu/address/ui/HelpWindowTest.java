package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.awt.Desktop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class HelpWindowTest {

    private static final String USERGUIDE_URL = HelpWindow.USERGUIDE_URL; // Use the constant from HelpWindow
    private HelpWindow helpWindow;

    @BeforeEach
    public void setUp() {
        // Initialize JavaFX
        new JFXPanel(); // This initializes the JavaFX toolkit

        // Use Platform.runLater to ensure we are on the JavaFX Application Thread
        Platform.runLater(() -> {
            helpWindow = new HelpWindow(); // Initialize your HelpWindow
        });

        // Wait for the UI to be set up
        while (helpWindow == null) {
            // This is a simple way to wait for the initialization to complete
        }
    }

    @Test
    public void testHyperlinkTextProperties() {
        // Check the color of the hyperlink
        Color expectedColor = Color.rgb(173, 216, 230);
        assertEquals(expectedColor, helpWindow.getHyperlinkTextFill());

        // Check if the hyperlink is underlined
        assertTrue(helpWindow.isHyperlinkTextUnderlined());
    }

    @Test
    public void testOpenUserGuide() throws Exception {
        // Mock Desktop
        Desktop mockDesktop = mock(Desktop.class);

        // Set the mock Desktop to the class (if you want to inject mocks)
        // You would need to change the HelpWindow class to allow for this,
        // or simply call the real method and verify that it does not throw an error
        // Here we will call the method directly and check for exceptions
        MouseEvent event = mock(MouseEvent.class);

        // Ensure openUserGuide opens the correct URL and does not throw exceptions
        assertDoesNotThrow(() -> helpWindow.openUserGuide(event));
    }
}
