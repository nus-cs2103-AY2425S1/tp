package seedu.address.ui;

import java.util.Comparator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.person.Person;

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
    private Label id;
    //=========== Person Field Labels ======================================================================
    @FXML
    private Label personName;
    @FXML
    private Label phone;
    @FXML
    private Label personAddress;
    @FXML
    private Label email;
    @FXML
    private Label role;
    @FXML
    private FlowPane tags;

    //=========== Concert Field Labels =====================================================================
    @FXML
    private Label concertName;
    @FXML
    private Label concertAddress;
    @FXML
    private Label date;

    /**
     * Creates a {@code ConcertContactCard} with the given {@code ConcertContact} and index to display.
     */
    public ConcertContactCard(ConcertContact concertContact, int displayedIndex) {
        super(FXML);

        this.concertContact = concertContact;

        Person person = concertContact.getPerson();
        Concert concert = concertContact.getConcert();

        id.setText(displayedIndex + ". ");

        personName.setText(concertContact.getPerson().getName().fullName);
        phone.setText(person.getPhone().value);
        personAddress.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        role.setText(person.getRole().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        concertName.setText(concertContact.getConcert().getName().fullName);
        concertAddress.setText(concert.getAddress().value);
        date.setText(concert.getDate().concertDate);
    }
}
