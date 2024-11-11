package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.concert.Concert;

/**
 * A UI component that displays information of a {@code Concert}.
 */
public class ConcertCard extends UiPart<Region> {

    private static final String FXML = "ConcertListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Concert concert;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label address;
    @FXML
    private Label date;

    /**
     * Creates a {@code ConcertCard} with the given {@code Concert} and index to display.
     */
    public ConcertCard(Concert concert, int displayedIndex) {
        super(FXML);
        this.concert = concert;
        id.setText(displayedIndex + ". ");
        name.setText(concert.getName().fullName);
        address.setText(concert.getAddress().value);
        date.setText(concert.getDate().concertDate);
    }
}
