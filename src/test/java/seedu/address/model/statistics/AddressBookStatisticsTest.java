package seedu.address.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.UniqueBuyingPropertyList;
import seedu.address.model.person.UniqueSellingPropertyList;
import seedu.address.testutil.AddressBookStatisticsBuilder;

public class AddressBookStatisticsTest {

    @Test
    public void processPersonData_success() {
        UniqueSellingPropertyList uniqueSellingPropertyList = new UniqueSellingPropertyList();
        uniqueSellingPropertyList.addUniqueSellingProperties(ALICE.getListOfSellingProperties());

        UniqueBuyingPropertyList uniqueBuyingPropertyList = new UniqueBuyingPropertyList();
        uniqueBuyingPropertyList.addUniqueBuyingProperties(ALICE.getListOfBuyingProperties());

        AddressBookStatistics expectedStatistics = new AddressBookStatisticsBuilder()
                .withTotalPersons(1)
                .withUniqueSellingPropertyList(uniqueSellingPropertyList)
                .withUniqueBuyingPropertyList(uniqueBuyingPropertyList)
                .withTotalPropertiesSold(1)
                .withTotalPropertiesBought(1)
                .withTotalSalesRevenue(3000000)
                .withTotalPurchaseExpense(3000000).build();

        AddressBookStatistics statistics = new AddressBookStatisticsBuilder().build();
        statistics.processPersonData(ALICE);

        assertDeepEquals(expectedStatistics, statistics);

    }

    /**
     * Asserts that the {@code test} and {@code expected} are deeply equal in all their fields.
     */
    public static void assertDeepEquals(AddressBookStatistics expected,
                                        AddressBookStatistics actual) {
        try {
            assertEquals(expected.getTotalPersons(), actual.getTotalPersons());
            assertEquals(expected.getTotalPropertiesBought(), actual.getTotalPropertiesBought());
            assertEquals(expected.getTotalPropertiesSold(), actual.getTotalPropertiesSold());
            assertEquals(expected.getTotalSalesRevenue(), actual.getTotalSalesRevenue());
            assertEquals(expected.getTotalPurchaseExpense(), actual.getTotalPurchaseExpense());
            assertEquals(expected.getUniqueSellingPropertyList(), actual.getUniqueSellingPropertyList());
            assertEquals(expected.getUniqueBuyingPropertyList(), actual.getUniqueBuyingPropertyList());
        } catch (Exception e) {
            throw new IllegalArgumentException("Processing failure.", e);
        }
    }

}
