package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.wedding.Wedding;

/**
 * An UI component that displays information of a {@code Wedding}.
 */
public class WeddingCard extends UiPart<Region> {

    private static final String FXML = "WeddingListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Wedding wedding;

    @FXML
    private HBox cardPane;
    @FXML
    private Label names;
    @FXML
    private Label id;
    @FXML
    private Label venue;
    @FXML
    private Label dateTime;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public WeddingCard(Wedding wedding, int displayedIndex) {
        super(FXML);
        this.wedding = wedding;
        id.setText(displayedIndex + ". ");
        names.setText(wedding.getWeddingName().toString());
        venue.setText(wedding.getVenue().value);
        dateTime.setText(wedding.getDatetime().value);
    }
}
