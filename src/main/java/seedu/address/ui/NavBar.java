package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


/**
 * Represents a navigation bar to toggle between different pages,
 * such as Contacts and Events.
 */
public class NavBar extends UiPart<HBox> {

    private static final String FXML = "NavBar.fxml";

    @FXML
    private Button contactsButton;

    @FXML
    private Button eventsButton;

    private final NavHandler navHandler;

    /**
     * Creates a NavBar with the specified navigation handler.
     *
     * @param navHandler The handler to manage navigation actions for the buttons.
     */
    public NavBar(NavHandler navHandler) {
        super(FXML);
        this.navHandler = navHandler;
        initializeButtons();
    }

    private void initializeButtons() {
        contactsButton.setOnAction(event -> navHandler.handleNav("Contacts"));
        eventsButton.setOnAction(event -> navHandler.handleNav("Events"));
    }

    public void pressContactsButton() {
        contactsButton.requestFocus();
    }

    /**
     * A functional interface to handle navigation actions when a button is pressed.
     * Implementing classes define how to switch views based on the selected page.
     */
    public interface NavHandler {
        /**
         * Handles the navigation action based on the specified page.
         *
         * @param page The name of the page to navigate to (e.g., "Contacts" or "Events").
         */
        void handleNav(String page);
    }
}
