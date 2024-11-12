package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.wedding.Wedding;

/**
 * A UI component that displays information of a {@code Wedding}.
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
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label partner1;
    @FXML
    private Label partner2;
    @FXML
    private Label address;
    @FXML
    private Label date;

    /**
     * Creates a {@code WeddingCard} with the given {@code Wedding} and index to display.
     */
    public WeddingCard(Wedding wedding, int displayedIndex) {
        super(FXML);
        this.wedding = wedding;
        id.setText(displayedIndex + ". ");
        name.setText(wedding.getWeddingName().toString());
        partner1.setText("Partner 1: " + (wedding.getPartner1() != null
                ? wedding.getPartner1().getName().toString()
                : "TBC"));
        partner2.setText("Partner 2: " + (wedding.getPartner2() != null
                ? wedding.getPartner2().getName().toString()
                : "TBC"));
        address.setText("Address: " + (wedding.getAddress() != null
                ? wedding.getAddress().toString()
                : "TBC"));
        date.setText("Date: " + (wedding.getDate() != null
                ? wedding.getDate()
                : "TBC"));
    }
}
