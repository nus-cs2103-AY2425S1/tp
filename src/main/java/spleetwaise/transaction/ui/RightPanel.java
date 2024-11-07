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
import javafx.scene.control.CheckBox;
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
    private Label youOwnLabel;
    @FXML
    private Label ownYouLabel;
    @FXML
    private Button filterBtn;
    @FXML
    private CheckBox trackUndoneBalanceOnlyCheckBox;

    /**
     * Creates a {@code RightPanel} with the given {@code ObservableList<Transaction>} to display.
     */
    public RightPanel(CommandBox cb) {
        super(FXML);

        // Initial balance track undone balance only
        trackUndoneBalanceOnlyCheckBox.setSelected(true);

        commandBox = cb;
        CommonModelManager commonModel = CommonModelManager.getInstance();

        ObservableList<Transaction> txns = commonModel.getFilteredTransactionList();
        // Add listener to automatically update balances when the list changes
        txns.addListener((ListChangeListener.Change<? extends Transaction> c) -> updateBalances());
        // Add listener to update balance whenever the checkbox changes
        trackUndoneBalanceOnlyCheckBox.selectedProperty()
                .addListener((observable, oldValue, newValue) -> updateBalances(newValue));

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
     * Updates the balance labels based on whether trackUndoneBalanceOnlyCheckBox is checked.
     */
    private void updateBalances() {
        updateBalances(trackUndoneBalanceOnlyCheckBox.isSelected());
    }

    /**
     * Updates the balance labels based on the current transactions.
     */
    private void updateBalances(boolean isTrackingUndoneBalanceOnly) {
        ObservableList<Transaction> txns = CommonModelManager.getInstance().getFilteredTransactionList();

        // Create predicates for each balance type (owe and owed)
        FilterCommandPredicate youOwnFilter = createBalanceFilter(isTrackingUndoneBalanceOnly, NEGATIVE_SIGN);
        FilterCommandPredicate ownYouFilter = createBalanceFilter(isTrackingUndoneBalanceOnly, POSITIVE_SIGN);

        youOwnLabel.setText(
                "You Owe $" + calculateBalance(txns, youOwnFilter).toString());
        ownYouLabel.setText(
                "You are Owed $" + calculateBalance(txns, ownYouFilter).toString());
    }

    /**
     * Creates a filter predicate based on the undone status and amount sign.
     *
     * @param isTrackingUndoneBalanceOnly Whether to include only "undone" balances.
     * @param sign                        The sign of the balance (positive for "owed" or negative for "owe").
     * @return A FilterCommandPredicate to apply the specified filters.
     */
    private FilterCommandPredicate createBalanceFilter(boolean isTrackingUndoneBalanceOnly, String sign) {
        ArrayList<Predicate<Transaction>> predicates = new ArrayList<>();

        // Add status predicate if tracking only undone balances
        if (isTrackingUndoneBalanceOnly) {
            predicates.add(new StatusFilterPredicate(new Status(NOT_DONE_STATUS)));
        }

        // Add amount sign predicate
        predicates.add(new AmountSignFilterPredicate(sign));

        return new FilterCommandPredicate(predicates);
    }

    /**
     * General method to calculate balance based on a filtering condition.
     *
     * @param txns   The list of transactions to filter and sum.
     * @param filter The combined predicate to filter transaction.
     * @return The sum of amounts that match the filter condition.
     */
    private BigDecimal calculateBalance(ObservableList<Transaction> txns, FilterCommandPredicate filter) {
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

        MenuItem filterByDone = new MenuItem("Filter by Done or Not Done Status");
        filterByDone.setOnAction(e -> filterTransactionsByDoneStatus());

        MenuItem filterByPositiveOrNegativeAmount = new MenuItem("Filter by Positive or Negative Amount");
        filterByPositiveOrNegativeAmount.setOnAction(e -> filterTransactionsByAmount());

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

        filterMenu.getItems().addAll(resetFilter, filterByDone, filterByPositiveOrNegativeAmount, divider,
                filterByContact, filterByAmount, filterByDescription, filterByDate
        );

        return filterMenu;
    }

    private void filterTransactionsByAmount() {
        // toggle between positive or negative amounts only
        resetFilter();
        CommonModelManager.getInstance()
                .updateFilteredTransactionList(new AmountSignFilterPredicate(amountSignForFilter));
        amountSignForFilter = amountSignForFilter.equals(POSITIVE_SIGN) ? NEGATIVE_SIGN : POSITIVE_SIGN;
    }

    private void filterTransactionsByDoneStatus() {
        // toggle between undone or done transactions only
        resetFilter();
        CommonModelManager.getInstance()
                .updateFilteredTransactionList(new StatusFilterPredicate(new Status(statusForFilter)));
        statusForFilter = statusForFilter.equals(DONE_STATUS) ? NOT_DONE_STATUS : DONE_STATUS;
    }

    /**
     * Resets the filter to show all transactions. public for testability.
     */
    public void resetFilter() {
        CommonModelManager.getInstance().updateFilteredTransactionList(TransactionBookModel.PREDICATE_SHOW_ALL_TXNS);
    }
}
