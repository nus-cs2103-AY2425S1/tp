package seedu.address.ui.meetup;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.meetup.MeetUp;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Buyer}.
 */
public class MeetUpCard extends UiPart<Region> {

    private static final String FXML = "MeetUpListCard.fxml";
    public final MeetUp meetUp;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on BuyerList level 4</a>
     */
    @FXML
    private HBox meetUpCardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label info;
    @FXML
    private Label from;
    @FXML
    private Label to;

    /**
     * Creates a {@code BuyerCode} with the given {@code Buyer} and index to display.
     */
    public MeetUpCard(MeetUp meetUp, int displayedIndex) {
        super(FXML);
        this.meetUp = meetUp;
        id.setText(displayedIndex + ". ");
        name.setText(meetUp.getName().toString());
        info.setText(meetUp.getInfo().toString());
        from.setText(meetUp.getFrom().toString());
        to.setText(meetUp.getTo().toString());
    }
}
