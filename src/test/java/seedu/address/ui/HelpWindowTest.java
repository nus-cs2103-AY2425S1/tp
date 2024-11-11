package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HelpWindowTest {

    @Test
    public void helpMessage_containsUserGuideUrl() {
        assertTrue(HelpWindow.HELP_MESSAGE.contains(HelpWindow.USERGUIDE_URL),
                "Help message should contain the user guide URL");
    }

    @Test
    public void userGuideUrl_isCorrect() {
        assertEquals("https://ay2425s1-cs2103t-w14-2.github.io/tp/UserGuide.html",
                HelpWindow.USERGUIDE_URL,
                "User guide URL should be correct");
    }

    @Test
    public void helpMessage_isCorrect() {
        String expectedMessage = "Refer to the user guide for more details: " + HelpWindow.USERGUIDE_URL;
        assertEquals(expectedMessage, HelpWindow.HELP_MESSAGE,
                "Help message should be correctly formatted");
    }
}
