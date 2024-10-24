package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;
import seedu.address.model.person.UniqueBuyingPropertyList;
import seedu.address.model.person.UniqueSellingPropertyList;

/**
 * Contains all statistics related to the AddressBook.
 */
public class AddressBookStatistics {

    private int totalPersons;
    private int totalPropertiesBought;
    private int totalPropertiesSold;
    private int totalSalesRevenue;
    private int totalPurchaseExpense;
    private UniqueSellingPropertyList uniqueSellingPropertyList;
    private UniqueBuyingPropertyList uniqueBuyingPropertyList;

    /**
     * Initialises an empty {@code AddressBookStatistics} that has all field set to 0 or a new empty list.
     */
    public AddressBookStatistics() {
        this.totalPersons = 0;
        this.totalPropertiesBought = 0;
        this.totalPropertiesSold = 0;
        this.totalSalesRevenue = 0;
        this.totalPurchaseExpense = 0;
        this.uniqueSellingPropertyList = new UniqueSellingPropertyList();
        this.uniqueBuyingPropertyList = new UniqueBuyingPropertyList();
    }

    /**
     * Inialises a  {@code AddressBookStatistics} based on the fields provided by the user.
     *
     * @param totalPersons Total number of persons in the address book
     * @param totalPropertiesBought Total number of properties bought by all persons in the address book
     * @param totalPropertiesSold Total number of properties sold by all persons in the address book
     * @param totalSalesRevenue Total money earned from properties sold by all persons in the address book
     * @param totalPurchaseExpense Total money spent from properties bought by all persons in the address book
     * @param uniqueSellingPropertyList A list of unique properties that all persons in the address book wants to buy.
     * @param uniqueBuyingPropertyList A list of unique properties that all persons in the address book wants to sell.
     */
    public AddressBookStatistics(int totalPersons,
                                 int totalPropertiesBought,
                                 int totalPropertiesSold,
                                 int totalSalesRevenue,
                                 int totalPurchaseExpense,
                                 UniqueSellingPropertyList uniqueSellingPropertyList,
                                 UniqueBuyingPropertyList uniqueBuyingPropertyList) {
        this.totalPersons = totalPersons;
        this.totalPropertiesBought = totalPropertiesBought;
        this.totalPropertiesSold = totalPropertiesSold;
        this.totalSalesRevenue = totalSalesRevenue;
        this.totalPurchaseExpense = totalPurchaseExpense;
        this.uniqueSellingPropertyList = uniqueSellingPropertyList;
        this.uniqueBuyingPropertyList = uniqueBuyingPropertyList;
    }

    /**
     * Processes the data of each person added to the addressBook and appends it to the corresponding statistic.
     *
     * @param person Person who is being added to the addressBook
     */
    public void processPersonData(Person person) {
        requireNonNull(person);
        ObservableList<Property> listoOfSellingProperties = person.getListOfSellingProperties();
        ObservableList<Property> listoOfBuyingProperties = person.getListOfBuyingProperties();
        this.totalPersons += 1;
        this.uniqueSellingPropertyList.addUniqueSellingProperties(listoOfSellingProperties);
        this.uniqueBuyingPropertyList.addUniqueBuyingProperties(listoOfBuyingProperties);
        this.totalPropertiesSold += person.getNumberOfPropertiesSold(); //TODO Testing
        this.totalPropertiesBought += person.getNumberOfPropertiesBought(); //TODO Testing
        this.totalSalesRevenue += person.getSalesRevenue(); //TODO Test once SoldCommand is implemented
        this.totalPurchaseExpense += person.getPurchaseExpense(); //TODO Test once BoughtCommand is implemented
    }

    /**
     * Resets all statistics.
     */
    public void reset() {
        this.totalPersons = 0;
        this.totalPropertiesBought = 0;
        this.totalPropertiesSold = 0;
        this.totalSalesRevenue = 0;
        this.totalPurchaseExpense = 0;
        this.uniqueSellingPropertyList = new UniqueSellingPropertyList();
        this.uniqueBuyingPropertyList = new UniqueBuyingPropertyList();
    }

    public int getTotalPersons() {
        return totalPersons;
    }

    public int getTotalPropertiesBought() {
        return totalPropertiesBought;
    }

    public int getTotalPropertiesSold() {
        return totalPropertiesSold;
    }

    public int getTotalSalesRevenue() {
        return totalSalesRevenue;
    }

    public int getTotalPurchaseExpense() {
        return totalPurchaseExpense;
    }

    public UniqueSellingPropertyList getUniqueSellingPropertyList() {
        return uniqueSellingPropertyList;
    }

    public UniqueBuyingPropertyList getUniqueBuyingPropertyList() {
        return uniqueBuyingPropertyList;
    }

}
