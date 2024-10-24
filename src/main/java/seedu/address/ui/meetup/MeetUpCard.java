package seedu.address.ui.meetup;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
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
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on MeetUpList level 4</a>
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
    @FXML
    private FlowPane addedBuyers;

    /**
     * Creates a {@code MeetUpCode} with the given {@code MeetUp} and index to display.
     */
    public MeetUpCard(MeetUp meetUp, int displayedIndex, boolean overlap) {
        super(FXML);
        this.meetUp = meetUp;
        id.setText(displayedIndex + ". ");
        name.setText(meetUp.getName().toString());
        info.setText(meetUp.getInfo().toString());
        from.setText(meetUp.getFrom().toPrettyString());
        to.setText(meetUp.getTo().toPrettyString());
        meetUp.getAddedBuyers().stream()
                .sorted(Comparator.comparing(buyer -> buyer.addedBuyerName))
                .forEach(addedBuyer -> addedBuyers.getChildren().add(new Label(addedBuyer.addedBuyerName)));

        if (overlap) {
            from.setStyle("-fx-text-fill: red;");
            to.setStyle("-fx-text-fill: red;");
        } else {
            from.setStyle("-fx-text-fill: #2DFF54;");
            to.setStyle("-fx-text-fill: #2DFF54;");
        }
    }
}
