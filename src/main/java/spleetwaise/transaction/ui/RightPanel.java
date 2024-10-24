package spleetwaise.transaction.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import spleetwaise.address.ui.UiPart;
import spleetwaise.commons.model.CommonModel;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Transaction;

/**
 * A UI component that displays information of {@code Transaction} related information.
 */
public class RightPanel extends UiPart<Region> {
    private static final String FXML = "RightPanel.fxml";

    private static boolean amountFilter = false;

    private TransactionListPanel transactionListPanel;

    @FXML
    private StackPane transactionListPanelPlaceholder;
    @FXML
    private Label youOwnLabel;
    @FXML
    private Label ownYouLabel;
    @FXML
    private Label filterIcon;

    /**
     * Creates a {@code RightPanel} with the given {@code ObservableList<Transaction>} to display.
     */
    public RightPanel() {
        super(FXML);
        ObservableList<Transaction> txns = CommonModel.getInstance().getFilteredTransactionList();
        updateBalances();

        transactionListPanel = new TransactionListPanel(txns);
        transactionListPanelPlaceholder.getChildren().add(transactionListPanel.getRoot());

        filterIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showFilterMenu);
    }

    /**
     * Updates the balance labels based on the current transactions.
     */
    public void updateBalances() {
        ObservableList<Transaction> txns = CommonModel.getInstance().getFilteredTransactionList();
        youOwnLabel.setText("You owe $" + calculateBalance(txns, Amount::isNegative).toString());
        ownYouLabel.setText("You are owned $" + calculateBalance(txns, amt -> !amt.isNegative()).toString());
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

        MenuItem filterByAmount = new MenuItem("Filter by Positive or Negative Amount");
        filterByAmount.setOnAction(e -> filterTransactionsByAmount());

        MenuItem filterByDateRange = new MenuItem("Filter by last month");
        filterByDateRange.setOnAction(e -> filterTransactionsByLastMonth());

        MenuItem resetFilter = new MenuItem("Reset filter");
        resetFilter.setOnAction(e -> resetFilter());

        filterMenu.getItems().addAll(resetFilter, filterByAmount, filterByDateRange);
        filterMenu.show(filterIcon, event.getScreenX(), event.getScreenY());
    }

    private void filterTransactionsByAmount() {
        // show only negative or positive amounts
        CommonModel.getInstance().updateFilteredTransactionList(txn -> amountFilter != txn.getAmount().isNegative());
        ObservableList<Transaction> filtered = CommonModel.getInstance().getFilteredTransactionList();
        transactionListPanel.updateTransactionList(filtered);
        amountFilter = !amountFilter;
    }

    private void filterTransactionsByLastMonth() {
        // filter transactions by last month
        CommonModel.getInstance()
                .updateFilteredTransactionList(txn -> txn.getDate().getDate().isAfter(LocalDate.now().minusMonths(1)));
        ObservableList<Transaction> filtered = CommonModel.getInstance().getFilteredTransactionList();
        transactionListPanel.updateTransactionList(filtered);
    }

    private void resetFilter() {
        CommonModel.getInstance().updateFilteredTransactionList(TransactionBookModel.PREDICATE_SHOW_ALL_TXNS);
        ObservableList<Transaction> filtered = CommonModel.getInstance().getFilteredTransactionList();
        transactionListPanel.updateTransactionList(filtered);
    }
}
