package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.concert.ConcertContact;

/**
 * A UI component that displays information of a {@code ConcertContact}.
 */
public class ConcertContactCard extends UiPart<Region> {

    private static final String FXML = "ConcertContactListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ConcertContact concertContact;

    @FXML
    private HBox cardPane;
    @FXML
    private Label personName;

    @FXML
    private Label concertName;
    @FXML
    private Label id;

    /**
     * Creates a {@code ConcertContactCard} with the given {@code ConcertContact} and index to display.
     */
    public ConcertContactCard(ConcertContact concertContact, int displayedIndex) {
        super(FXML);

        this.concertContact = concertContact;
        id.setText(displayedIndex + ". ");
        personName.setText(concertContact.getPerson().getName().fullName);
        concertName.setText(concertContact.getConcert().getName().fullName);
    }
}
