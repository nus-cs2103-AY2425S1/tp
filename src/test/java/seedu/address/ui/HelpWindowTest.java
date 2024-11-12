
package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HelpWindowTest {

    public static final String USER_GUIDE = "https://ay2425s1-cs2103t-w13-1.github.io/tp/UserGuide.html";
    public static final String HELLP_MESSAGE = "Refer to the user guide:"
            + " https://ay2425s1-cs2103t-w13-1.github.io/tp/UserGuide.html";

    @Test
    public void checkUserGuideUrl() {
        assertEquals(USER_GUIDE, HelpWindow.USERGUIDE_URL);
    }

    @Test
    public void checkHelpMessage() {
        assertEquals(HELLP_MESSAGE, HelpWindow.HELP_MESSAGE);
    }
}
