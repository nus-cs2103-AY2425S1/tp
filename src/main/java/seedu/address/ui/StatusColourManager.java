package seedu.address.ui;

import seedu.address.model.person.RsvpStatus;

/**
 * Manages the colour of RSVP statuses in the UI.
 */
public class StatusColourManager {
    private static final String PENDING_STYLE = "-fx-background-color: #eba250; -fx-background-radius: 5;"
            + "-fx-text-fill: black";
    private static final String COMING_STYLE = "-fx-background-color: #85bd80; -fx-background-radius: 5;"
            + "-fx-text-fill: black";
    private static final String NOT_COMING_STYLE = "-fx-background-color: #DD0000; -fx-background-radius: 5;"
            + "-fx-text-fill: white";

    public static String getStatusStyle(RsvpStatus status) {
        switch (status) {
        case COMING:
            return COMING_STYLE;

        case NOT_COMING:
            return NOT_COMING_STYLE;

        default:
            return PENDING_STYLE;

        }
    }
}
