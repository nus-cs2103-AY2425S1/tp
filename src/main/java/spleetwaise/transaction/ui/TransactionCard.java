package spleetwaise.transaction.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import spleetwaise.address.ui.UiPart;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * A UI component that displays information of a {@code Transaction}.
 */
public class TransactionCard extends UiPart<Region> {
    private static final String FXML = "TransactionListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a consequence, UI
     * elements' variable names cannot be set to such keywords or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Transaction transaction;

    @FXML
    private Label name;
    @FXML
    private Label status;
    @FXML
    private Label amount;
    @FXML
    private Label description;
    @FXML
    private Label month;
    @FXML
    private Label day;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public TransactionCard(Transaction transaction, int displayedIndex) {
        super(FXML);
        this.transaction = transaction;
        month.setText(transaction.getDate().getDate().format(DateTimeFormatter.ofPattern("MMM")));
        day.setText(transaction.getDate().getDate().format(DateTimeFormatter.ofPattern("d")));
        name.setText(displayedIndex + ". " + transaction.getPerson().getName().fullName);
        description.setText(transaction.getDescription().toString());
        if (transaction.getAmount().isNegative()) {
            status.setText("you owe");
            status.setStyle("-fx-text-fill: red;");
            amount.setStyle("-fx-text-fill: red;");
        } else {
            status.setText("owes you");
            status.setStyle("-fx-text-fill: green;");
            amount.setStyle("-fx-text-fill: green;");
        }
        amount.setText("$" + transaction.getAmount().toString());
    }
}
