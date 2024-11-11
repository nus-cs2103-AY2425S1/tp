package spleetwaise.transaction.ui;

import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DATE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static spleetwaise.transaction.model.filterpredicate.AmountSignFilterPredicate.NEGATIVE_SIGN;
import static spleetwaise.transaction.model.filterpredicate.AmountSignFilterPredicate.POSITIVE_SIGN;
import static spleetwaise.transaction.model.transaction.Status.DONE_STATUS;
import static spleetwaise.transaction.model.transaction.Status.NOT_DONE_STATUS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.ListChangeListener;
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
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.commons.ui.CommandBox;
import spleetwaise.commons.ui.UiPart;
import spleetwaise.transaction.logic.commands.FilterCommand;
import spleetwaise.transaction.model.FilterCommandPredicate;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.filterpredicate.AmountSignFilterPredicate;
import spleetwaise.transaction.model.filterpredicate.StatusFilterPredicate;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * A UI component that displays information of {@code Transaction} related information.
 */
public class RightPanel extends UiPart<Region> {
    private static final String FXML = "RightPanel.fxml";

    private static String amountSignForFilter = POSITIVE_SIGN;
    private static String statusForFilter = DONE_STATUS;

    private TransactionListPanel transactionListPanel;
    private CommandBox commandBox;
    private ContextMenu filterMenu;

    @FXML
    private StackPane transactionListPanelPlaceholder;
    @FXML
    private Label youOweLabel;
    @FXML
    private Label youAreOwedLabel;
    @FXML
    private Button filterBtn;

    /**
     * Creates a {@code RightPanel} with the given {@code ObservableList<Transaction>} to display.
     */
    public RightPanel(CommandBox cb) {
        super(FXML);

        commandBox = cb;
        CommonModelManager commonModel = CommonModelManager.getInstance();

        ObservableList<Transaction> txns = commonModel.getFilteredTransactionList();
        // Add listener to automatically update balances when the list changes
        txns.addListener((ListChangeListener.Change<? extends Transaction> c) -> updateBalances());

        // Initial balance update
        updateBalances();

        transactionListPanel = new TransactionListPanel(txns);
        transactionListPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());

        StringBinding iconColorBinding = Bindings.createStringBinding(() -> commonModel.getCurrentPredicate().get()
                == TransactionBookModel.PREDICATE_SHOW_ALL_TXNS ? "gray" : "blue", commonModel.getCurrentPredicate());

        filterBtn.styleProperty().bind(Bindings.concat("-icon-paint: ", iconColorBinding, ";"));
        filterBtn.setPickOnBounds(true);
        filterMenu = createFilterMenu();
        filterBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showFilterMenu);
    }

    /**
     * Updates the balance labels based on the current transactions.
     */
    private void updateBalances() {
        ObservableList<Transaction> txns = CommonModelManager.getInstance().getFilteredTransactionList();

        // Create predicates for each balance type (owe and owed)
        AmountSignFilterPredicate youOweFilter = new AmountSignFilterPredicate(NEGATIVE_SIGN);
        AmountSignFilterPredicate youAreOwedFilter = new AmountSignFilterPredicate(POSITIVE_SIGN);

        youOweLabel.setText(
                "You Owe $" + calculateBalance(txns, youOweFilter).toString());
        youAreOwedLabel.setText(
                "You are Owed $" + calculateBalance(txns, youAreOwedFilter).toString());
    }

    /**
     * General method to calculate balance based on a filtering condition.
     *
     * @param txns   The list of transactions to filter and sum.
     * @param filter The predicate to filter transaction.
     * @return The sum of amounts that match the filter condition.
     */
    private BigDecimal calculateBalance(ObservableList<Transaction> txns, Predicate<Transaction> filter) {
        return txns.stream()
                .filter(filter)
                .map(Transaction::getAmount)
                .map(Amount::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Displays the filter menu if it is not already showing.
     */
    private void showFilterMenu(MouseEvent event) {
        if (filterMenu.isShowing()) {
            filterMenu.hide();
        } else {
            filterMenu.show(filterBtn, event.getScreenX(), event.getScreenY());
        }
    }

    /**
     * Creates and configures the filter context menu.
     *
     * @return the configured ContextMenu
     */
    private ContextMenu createFilterMenu() {
        ContextMenu filterMenu = new ContextMenu();

        MenuItem resetFilter = new MenuItem("Reset filter");
        resetFilter.setOnAction(e -> resetFilter());

        MenuItem allTxnByDoneStatus = new MenuItem("Show All Done or Not Done Transactions");
        allTxnByDoneStatus.setOnAction(e -> showAllDoneOrNotDoneTransactions());

        MenuItem allTxnByAmountSign = new MenuItem("Show All Positive or Negative Transactions");
        allTxnByAmountSign.setOnAction(e -> showAllPositiveOrNegativeTransactions());

        SeparatorMenuItem divider = new SeparatorMenuItem();

        MenuItem filterByContact = new MenuItem("Filter by Contact");
        filterByContact.setOnAction(
                e -> commandBox.handleFilterCommandEntered(FilterCommand.COMMAND_WORD + " "));

        MenuItem filterByAmount = new MenuItem("Filter by Amount");
        filterByAmount.setOnAction(
                e -> commandBox.handleFilterCommandEntered(FilterCommand.COMMAND_WORD + " " + PREFIX_AMOUNT));

        MenuItem filterByDescription = new MenuItem("Filter by Description");
        filterByDescription.setOnAction(
                e -> commandBox.handleFilterCommandEntered(FilterCommand.COMMAND_WORD + " " + PREFIX_DESCRIPTION));

        MenuItem filterByDate = new MenuItem("Filter by Date");
        filterByDate.setOnAction(
                e -> commandBox.handleFilterCommandEntered(FilterCommand.COMMAND_WORD + " " + PREFIX_DATE));

        filterMenu.getItems().addAll(resetFilter, allTxnByDoneStatus, allTxnByAmountSign, divider,
                filterByContact, filterByAmount, filterByDescription, filterByDate
        );

        return filterMenu;
    }

    private void showAllPositiveOrNegativeTransactions() {
        // toggle between positive or negative amounts only
        applyFilterOnAllTxn(new AmountSignFilterPredicate(amountSignForFilter));
        amountSignForFilter = amountSignForFilter.equals(POSITIVE_SIGN) ? NEGATIVE_SIGN : POSITIVE_SIGN;
    }

    private void showAllDoneOrNotDoneTransactions() {
        // toggle between done or undone transactions only
        applyFilterOnAllTxn(new StatusFilterPredicate(new Status(statusForFilter)));
        statusForFilter = statusForFilter.equals(DONE_STATUS) ? NOT_DONE_STATUS : DONE_STATUS;
    }

    /**
     * Resets the filter to show all transactions, and reset the toggle info. public for testability.
     */
    public void resetFilter() {
        statusForFilter = DONE_STATUS;
        amountSignForFilter = POSITIVE_SIGN;
        CommonModelManager.getInstance().updateFilteredTransactionList(TransactionBookModel.PREDICATE_SHOW_ALL_TXNS);
    }

    /**
     * Apply a filter on all transactions.
     *
     * @param filter the filter to apply
     */
    private void applyFilterOnAllTxn(Predicate<Transaction> filter) {
        ArrayList<Predicate<Transaction>> predicates = new ArrayList<>();
        predicates.add(TransactionBookModel.PREDICATE_SHOW_ALL_TXNS);
        predicates.add(filter);
        CommonModelManager.getInstance().updateFilteredTransactionList(new FilterCommandPredicate(predicates));
    }
}
