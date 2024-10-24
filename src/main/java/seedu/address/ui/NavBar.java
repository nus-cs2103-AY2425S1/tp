package seedu.address.ui;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.types.common.DateTimeUtil;

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
    @FXML
    private Label dateTimeLabel;

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
        initializeDateTime();
    }

    private void initializeButtons() {
        contactsButton.setOnAction(event -> {
            navHandler.handleNav("Contacts");
            setActiveButton(contactsButton);
        });

        eventsButton.setOnAction(event -> {
            navHandler.handleNav("Events");
            setActiveButton(eventsButton);
        });
    }

    private void initializeDateTime() {
        Timeline timeline = DateTimeUtil.createTimeline(this::updateDateTime);
        timeline.play();
        updateDateTime();
    }

    private void updateDateTime() {
        String formattedDateTime = DateTimeUtil.getCurrentDateTimeString();
        dateTimeLabel.setText(formattedDateTime);
    }

    public void setActiveButton(Button activeButton) {
        if (activeButton != contactsButton) {
            contactsButton.getStyleClass().remove("active");
        } else {
            contactsButton.getStyleClass().add("active");
        }

        if (activeButton != eventsButton) {
            eventsButton.getStyleClass().remove("active");
        } else {
            eventsButton.getStyleClass().add("active");
        }
    }

    public Button getContactsButton() {
        return contactsButton;
    }

    public Button getEventsButton() {
        return eventsButton;
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
