package seedu.address.testutil;

import seedu.address.model.person.UniqueBuyingPropertyList;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.UniqueSellingPropertyList;
import seedu.address.model.statistics.AddressBookStatistics;

/**
 * Builds an instance of the {@code AddressBookStatistic}.
 */
public class AddressBookStatisticsBuilder {
    private int totalPersons;
    private int totalPropertiesBought;
    private int totalPropertiesSold;
    private int totalSalesRevenue;
    private int totalPurchaseExpense;
    private UniqueSellingPropertyList uniqueSellingPropertyList;
    private UniqueBuyingPropertyList uniqueBuyingPropertyList;

    private UniquePersonList persons;

    /**
     * Initialises an instance of the {@code AddressBookStatisticsBuilder}.
     */
    public AddressBookStatisticsBuilder() {
        this.totalPersons = 0;
        this.totalPropertiesBought = 0;
        this.totalPropertiesSold = 0;
        this.totalSalesRevenue = 0;
        this.totalPurchaseExpense = 0;
        this.uniqueSellingPropertyList = new UniqueSellingPropertyList();
        this.uniqueBuyingPropertyList = new UniqueBuyingPropertyList();
    }

    /**
     * Sets the totalPersons field with (@code totalPersons)
     */
    public AddressBookStatisticsBuilder withTotalPersons(int totalPersons) {
        this.totalPersons = totalPersons;
        return this;
    }

    /**
     * Sets the totalPropertiesBought field with (@code totalPropertiesBought)
     */
    public AddressBookStatisticsBuilder withTotalPropertiesBought(int totalPropertiesBought) {
        this.totalPropertiesBought = totalPropertiesBought;
        return this;
    }

    /**
     * Sets the totalPropertiesSold field with (@code totalPropertiesSold)
     */
    public AddressBookStatisticsBuilder withTotalPropertiesSold(int totalPropertiesSold) {
        this.totalPropertiesSold = totalPropertiesSold;
        return this;
    }

    /**
     * Sets the totalSalesRevenue field with (@code totalSalesRevenue)
     */
    public AddressBookStatisticsBuilder withTotalSalesRevenue(int totalSalesRevenue) {
        this.totalSalesRevenue = totalSalesRevenue;
        return this;
    }

    /**
     * Sets the totalPurchaseExpense field with (@code totalPurchaseExpense)
     */
    public AddressBookStatisticsBuilder withTotalPurchaseExpense(int totalPurchaseExpense) {
        this.totalPurchaseExpense = totalPurchaseExpense;
        return this;
    }

    /**
     * Sets the uniqueSellingPropertyList field with (@code uniqueSellingPropertyList)
     */
    public AddressBookStatisticsBuilder withUniqueSellingPropertyList(UniqueSellingPropertyList propertyList) {
        this.uniqueSellingPropertyList = propertyList;
        return this;
    }

    /**
     * Sets the uniqueBuyingPropertyList field with (@code uniqueBuyingPropertyList)
     */
    public AddressBookStatisticsBuilder withUniqueBuyingPropertyList(UniqueBuyingPropertyList propertyList) {
        this.uniqueBuyingPropertyList = propertyList;
        return this;
    }

    /**
     * Returns an instance of the {@code AddressBookStatistics}.
     */
    public AddressBookStatistics build() {
        return new AddressBookStatistics(totalPersons, totalPropertiesBought, totalPropertiesSold,
                totalSalesRevenue, totalPurchaseExpense, uniqueSellingPropertyList, uniqueBuyingPropertyList);
    }
}
