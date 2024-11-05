package spleetwaise.transaction.ui;

import static spleetwaise.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DATE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.math.BigDecimal;
import java.util.function.Predicate;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import spleetwaise.commons.model.CommonModel;
import spleetwaise.commons.ui.CommandBox;
import spleetwaise.commons.ui.UiPart;
import spleetwaise.transaction.logic.commands.FilterCommand;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * A UI component that displays information of {@code Transaction} related information.
 */
public class RightPanel extends UiPart<Region> {
    private static final String FXML = "RightPanel.fxml";

    private static boolean amountFilter = false;
    private static boolean doneFilter = false;

    private TransactionListPanel transactionListPanel;
    private CommandBox commandBox;

    @FXML
    private StackPane transactionListPanelPlaceholder;
    @FXML
    private Label youOwnLabel;
    @FXML
    private Label ownYouLabel;
    @FXML
    private Button filterBtn;

    /**
     * Creates a {@code RightPanel} with the given {@code ObservableList<Transaction>} to display.
     */
    public RightPanel(CommandBox cb) {
        super(FXML);

        commandBox = cb;
        CommonModel commonModel = CommonModel.getInstance();

        ObservableList<Transaction> txns = commonModel.getFilteredTransactionList();
        updateBalances();

        transactionListPanel = new TransactionListPanel(txns);
        transactionListPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());

        StringBinding iconColorBinding = Bindings.createStringBinding(() -> {
            return commonModel.getCurrentPredicate().get() == TransactionBookModel.PREDICATE_SHOW_ALL_TXNS ? "gray"
                    : "blue";
        }, commonModel.getCurrentPredicate());

        filterBtn.styleProperty().bind(Bindings.concat("-icon-paint: ", iconColorBinding, ";"));
        filterBtn.setPickOnBounds(true);
        filterBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showFilterMenu);
    }

    /**
     * Updates the balance labels based on the current transactions.
     */
    public void updateBalances() {
        ObservableList<Transaction> txns = CommonModel.getInstance().getFilteredTransactionList();
        youOwnLabel.setText("You Owe $" + calculateBalance(txns, Amount::isNegative).toString());
        ownYouLabel.setText("You are Owed $" + calculateBalance(txns, amt -> !amt.isNegative()).toString());
    }

    /**
     * General method to calculate balance based on a filtering condition.
     *
     * @param txns   The list of transactions to filter and sum.
     * @param filter The predicate to filter the amounts (e.g., isNegative or !isNegative).
     * @return The sum of amounts that match the filter condition.
     */
    private BigDecimal calculateBalance(ObservableList<Transaction> txns, Predicate<Amount> filter) {
        return txns.stream()
                .map(Transaction::getAmount)
                .filter(filter)
                .map(Amount::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Displays a context menu when the filter icon is clicked.
     */
    private void showFilterMenu(MouseEvent event) {
        ContextMenu filterMenu = new ContextMenu();

        MenuItem resetFilter = new MenuItem("Reset filter");
        resetFilter.setOnAction(e -> resetFilter());

        MenuItem filterByDone = new MenuItem("Filter by Done or Not Done Status");
        filterByDone.setOnAction(e -> filterTransactionsByDoneStatus());

        MenuItem filterByPositiveOrNegativeAmount = new MenuItem("Filter by Positive or Negative Amount");
        filterByPositiveOrNegativeAmount.setOnAction(e -> filterTransactionsByAmount());

        SeparatorMenuItem divider = new SeparatorMenuItem();

        MenuItem filterByContact = new MenuItem("Filter by Contact");
        filterByContact.setOnAction(
                e -> commandBox.handleFilterCommandEntered(FilterCommand.COMMAND_WORD + " " + PREFIX_PHONE));

        MenuItem filterByAmount = new MenuItem("Filter by Amount");
        filterByAmount.setOnAction(
                e -> commandBox.handleFilterCommandEntered(FilterCommand.COMMAND_WORD + " " + PREFIX_AMOUNT));

        MenuItem filterByDescription = new MenuItem("Filter by Description");
        filterByDescription.setOnAction(
                e -> commandBox.handleFilterCommandEntered(FilterCommand.COMMAND_WORD + " " + PREFIX_DESCRIPTION));

        MenuItem filterByDate = new MenuItem("Filter by Date");
        filterByDate.setOnAction(
                e -> commandBox.handleFilterCommandEntered(FilterCommand.COMMAND_WORD + " " + PREFIX_DATE));

        filterMenu.getItems().addAll(resetFilter, filterByDone, filterByPositiveOrNegativeAmount, divider,
                filterByContact, filterByAmount, filterByDescription, filterByDate
        );
        filterMenu.show(filterBtn, event.getScreenX(), event.getScreenY());
    }

    private void filterTransactionsByAmount() {
        // toggle between positive or negative amounts only
        amountFilter = !amountFilter;
        resetFilter();
        CommonModel.getInstance().updateFilteredTransactionList(txn -> amountFilter != txn.getAmount().isNegative());
    }

    private void filterTransactionsByDoneStatus() {
        // toggle between undone or done transactions only
        doneFilter = !doneFilter;
        resetFilter();
        CommonModel.getInstance().updateFilteredTransactionList(txn -> doneFilter != txn.getStatus().isDone());
    }

    /**
     * Resets the filter to show all transactions. public for testability.
     */
    public void resetFilter() {
        CommonModel.getInstance().updateFilteredTransactionList(TransactionBookModel.PREDICATE_SHOW_ALL_TXNS);
    }
}
