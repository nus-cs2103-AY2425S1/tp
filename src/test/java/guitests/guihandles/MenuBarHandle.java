package guitests.guihandles;

import java.util.Arrays;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;

/**
 * A handle for interacting with the application's menu bar during GUI tests.
 */
public class MenuBarHandle extends NodeHandle<Node> {
    public static final String MENU_BAR_ID = "#menuBar";

    /**
     * Constructs a {@code MenuBarHandle} for the specified menu bar node.
     *
     * @param menuBarNode The {@code Node} representing the menu bar.
     */
    public MenuBarHandle(Node menuBarNode) {
        super(menuBarNode);
    }

    /**
     * Opens the help window by clicking on the "Help" menu and selecting "F1" sequentially.
     */
    public void openHelpWindowUsingMenu() {
        clickOnMenuItemsSequentially("Help", "F1");
    }

    /**
     * Opens the help window by triggering the F1 key accelerator.
     */
    public void openHelpWindowUsingAccelerator() {
        guiRobot.push(KeyCode.F1);
    }

    /**
     * Clicks on the specified menu items in sequence to perform a series of actions.
     *
     * @param menuItems The sequence of menu item names to click on.
     */
    private void clickOnMenuItemsSequentially(String... menuItems) {
        Arrays.stream(menuItems).forEach(guiRobot::clickOn);
    }
}
