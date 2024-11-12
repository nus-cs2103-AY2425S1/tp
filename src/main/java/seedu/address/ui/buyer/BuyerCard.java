package seedu.address.ui.buyer;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.buyer.Buyer;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Buyer}.
 */
public class BuyerCard extends UiPart<Region> {

    private static final String FXML = "BuyerListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on BuyerList level 4</a>
     */

    public final Buyer buyer;

    @FXML
    private HBox buyerCardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label budget;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code BuyerCode} with the given {@code Buyer} and index to display.
     */
    public BuyerCard(Buyer buyer, int displayedIndex) {
        super(FXML);
        this.buyer = buyer;
        id.setText(displayedIndex + ". ");
        name.setText(buyer.getName().toString());
        phone.setText(buyer.getPhone().toString());
        budget.setText(buyer.getBudget().toPrettyString());
        email.setText(buyer.getEmail().toString());
        buyer.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
