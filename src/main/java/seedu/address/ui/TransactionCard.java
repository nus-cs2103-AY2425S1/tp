package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.person.Transaction;

/**
 * An UI component that displays information of a {@code Transaction}.
 */
public class TransactionCard extends UiPart<Region> {

    private static final String FXML = "TransactionListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Transaction transaction;

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label amount;
    @FXML
    private Label id;
    @FXML
    private Label otherParty;
    @FXML
    private Label date;

    /**
     * Creates a {@code TransactionCard} with the given {@code Transaction} and index to display.
     */
    public TransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        id.setText(displayedIndex + ". ");
        description.setText(transaction.getDescription());
        if (transaction.getAmount() < 0) {
            amount.setText("Amount: -$" + transaction.getAmount() * -1);
        } else {
            amount.setText("Amount: $" + transaction.getAmount());
        }
        otherParty.setText("Other Party: " + transaction.getOtherParty());
        date.setText("Date: " + transaction.getDate().format(DateTimeUtil.DEFAULT_DATE_FORMATTER));
    }
}
