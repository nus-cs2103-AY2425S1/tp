package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
public class HelpWindowTest {

    @Test
    public void helpText_containsExpectedContent() {
        assertTrue(HelpWindow.HELP_TEXT.contains("BA€ 1.0 Help"),
                "Help text should contain the application name");
        assertTrue(HelpWindow.HELP_TEXT.contains("Key Features and Commands"),
                "Help text should mention key features and commands");
    }

    @Test
    public void helpMessage_containsUserGuideUrl() {
        assertTrue(HelpWindow.HELP_MESSAGE.contains(HelpWindow.USERGUIDE_URL),
                "Help message should contain the user guide URL");
    }
}
