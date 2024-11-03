package spleetwaise.transaction.ui;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import spleetwaise.address.ui.CommandBox;
import spleetwaise.commons.model.CommonModel;
import spleetwaise.transaction.model.ReadOnlyTransactionBook;
import spleetwaise.transaction.model.TransactionBookModel;
import spleetwaise.transaction.model.transaction.Amount;
import spleetwaise.transaction.model.transaction.Status;
import spleetwaise.transaction.model.transaction.Transaction;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class RightPanelTest {

    private RightPanel rightPanel;
    private CommonModel mockCommonModel;
    private CommandBox mockCommandBox;
    private ObservableList<Transaction> mockTransactionList;
    private ReadOnlyTransactionBook mockReadOnlyTransactionBook;
    private MockedStatic<CommonModel> mockedCommonModelStatic;

    @BeforeAll
    public static void initJfxRuntime() {
        Platform.startup(() -> {
        });
    }

    @BeforeEach
    public void setUp() {
        mockCommandBox = mock(CommandBox.class);
        mockTransactionList = TypicalTransactions.getTypicalTransactionBook().getTransactionList();

        mockedCommonModelStatic = mockStatic(CommonModel.class);
        mockCommonModel = mock(CommonModel.class);

        mockedCommonModelStatic.when(CommonModel::getInstance).thenReturn(mockCommonModel);
        when(mockCommonModel.getFilteredTransactionList()).thenReturn(mockTransactionList);
        mockReadOnlyTransactionBook = mock(ReadOnlyTransactionBook.class);
        when(mockCommonModel.getTransactionBook()).thenReturn(mockReadOnlyTransactionBook);

        ObjectProperty<Predicate<Transaction>> mockPredicateProperty = new SimpleObjectProperty<>();
        Predicate<Transaction> mockPredicate = mock(Predicate.class);
        mockPredicateProperty.set(mockPredicate);
        when(mockCommonModel.getCurrentPredicate()).thenReturn(mockPredicateProperty);

        rightPanel = spy(new RightPanel(mockCommandBox));
    }

    @AfterEach
    public void tearDown() {
        mockedCommonModelStatic.close(); // Close static mock after each test
    }

    @Test
    public void testResetFilter_updatesCommonModelPredicate()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        rightPanel.resetFilter();

        // Verify that updateFilteredTransactionList was called with the correct predicate
        verify(mockCommonModel, times(1)).updateFilteredTransactionList(TransactionBookModel.PREDICATE_SHOW_ALL_TXNS);
    }

    @Test
    public void testFilterTransactionsByAmount_callsResetFilterAndUpdatesPredicate()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        // Call the private method "filterTransactionsByAmount" using reflection
        Method filterTransactionsByAmount = RightPanel.class.getDeclaredMethod("filterTransactionsByAmount");
        filterTransactionsByAmount.setAccessible(true);
        filterTransactionsByAmount.invoke(rightPanel);

        verify(rightPanel, times(1)).resetFilter();

        // Verify the predicate passed to updateFilteredTransactionList
        verify(mockCommonModel, times(1)).updateFilteredTransactionList(argThat(predicate -> {
            Transaction mockTxnPositive = mock(Transaction.class);
            Amount mockAmountPositive = mock(Amount.class);
            when(mockTxnPositive.getAmount()).thenReturn(mockAmountPositive);
            when(mockTxnPositive.getAmount().isNegative()).thenReturn(false);

            Transaction mockTxnNegative = mock(Transaction.class);
            Amount mockAmountNegative = mock(Amount.class);
            when(mockTxnNegative.getAmount()).thenReturn(mockAmountNegative);
            when(mockTxnNegative.getAmount().isNegative()).thenReturn(true);

            return predicate.test(mockTxnPositive) && !predicate.test(mockTxnNegative);
        }));
    }

    @Test
    public void testFilterTransactionsByDoneStatus_callsResetFilterAndUpdatesPredicate()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Call the private method "filterTransactionsByDoneStatus" using reflection
        Method filterTransactionsByDoneStatus = RightPanel.class.getDeclaredMethod("filterTransactionsByDoneStatus");
        filterTransactionsByDoneStatus.setAccessible(true);
        filterTransactionsByDoneStatus.invoke(rightPanel);

        verify(rightPanel, times(1)).resetFilter();

        // Verify the predicate passed to updateFilteredTransactionList
        verify(mockCommonModel, times(1)).updateFilteredTransactionList(argThat(predicate -> {
            Transaction mockTxnDone = mock(Transaction.class);
            Status mockStatusDone = mock(Status.class);
            when(mockTxnDone.getStatus()).thenReturn(mockStatusDone);
            when(mockTxnDone.getStatus().isDone()).thenReturn(true);

            Transaction mockTxnUndone = mock(Transaction.class);
            Status mockStatusUndone = mock(Status.class);
            when(mockTxnUndone.getStatus()).thenReturn(mockStatusUndone);
            when(mockTxnUndone.getStatus().isDone()).thenReturn(false);

            return predicate.test(mockTxnUndone) && !predicate.test(mockTxnDone);
        }));
    }
}
