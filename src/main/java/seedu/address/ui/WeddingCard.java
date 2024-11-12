package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.wedding.Wedding;

/**
 * An UI component that displays information of a {@code Person}.
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
    private Label weddingName;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label venue;
    @FXML
    private FlowPane ownWedding;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public WeddingCard(Wedding wedding, int displayedIndex) {
        super(FXML);
        this.wedding = wedding;
        id.setText(displayedIndex + ". ");
        weddingName.setText(wedding.getName().fullName);
        venue.setText(wedding.getVenue() == null ? null : wedding.getVenue().toString());

        if (wedding.getDate() == null) {
            date.visibleProperty().setValue(false);
        } else {
            date.setText(wedding.getDate().toString());
        }

        if (wedding.isOwnWedding()) {
            ownWedding.getChildren().add(new Label("Own Wedding"));
        } else {
            ownWedding.visibleProperty().setValue(false);
            ownWedding.setManaged(false);
        }
    }
}
