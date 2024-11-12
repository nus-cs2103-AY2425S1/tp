package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.util.DateTimeUtil.DEFAULT_DATE_PARSER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OTHER_PARTY;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TransactionDateComparatorTest {

    private LocalDate moreRecent = LocalDate.parse("2024-10-10", DEFAULT_DATE_PARSER);
    private LocalDate lessRecent = LocalDate.parse("2024-09-09", DEFAULT_DATE_PARSER);
    private TransactionDateComparator comparator = new TransactionDateComparator();


    private Transaction moreRecentTransaction = new Transaction(VALID_DESCRIPTION, VALID_AMOUNT, VALID_OTHER_PARTY,
            moreRecent);
    private Transaction lessRecentTransaction = new Transaction(VALID_DESCRIPTION, VALID_AMOUNT, VALID_OTHER_PARTY,
            lessRecent);

    @Test
    public void compare() {
        assertEquals(-1, comparator.compare(moreRecentTransaction, lessRecentTransaction));
        assertEquals(1, comparator.compare(lessRecentTransaction, moreRecentTransaction));
        assertEquals(0, comparator.compare(moreRecentTransaction, moreRecentTransaction));
    }

}
