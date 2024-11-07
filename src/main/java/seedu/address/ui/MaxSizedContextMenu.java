package seedu.address.ui;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

/**
 * Modified context menu component that has scrolling mechanism if max height is exceeded
 */
public class MaxSizedContextMenu extends ContextMenu {
    public static final int CONTEXT_MENU_MAX_HEIGHT = 150;
    public static final int CONTEXT_MENU_MAX_WIDTH = 400;

    /**
     * Constructor for max sized context menu
     */
    public MaxSizedContextMenu() {
        addEventHandler(Menu.ON_SHOWING, e -> {
            Node content = getSkin().getNode();
            if (content instanceof Region regionContent) {
                regionContent.setMaxHeight(CONTEXT_MENU_MAX_HEIGHT);
                regionContent.setMaxWidth(CONTEXT_MENU_MAX_WIDTH);

                // To solve weird JavaFX behavior where scrolling position is not updated after items have
                // changed.
                content.requestFocus();
                this.fireEvent(new KeyEvent(
                        KeyEvent.KEY_PRESSED, "", "",
                        KeyCode.UP, false, false, false, false));
            }
        });
    }
}
