package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import java.util.List;

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
     * Processes the data of each {@code person} added to the addressBook and appends it to the corresponding statistic.
     *
     * @param personList List of {@code person} who is being added to the addressBook
     */
    public void processPersonListData(List<Person> personList) {
        requireNonNull(personList);
        this.totalPersons = this.getTotalPersonsFromPersonList(personList);
        this.uniqueSellingPropertyList = this.getUniqueSellingPropertyListFromPersonList(personList);
        this.uniqueBuyingPropertyList = this.getUniqueBuyingPropertyListFromPersonList(personList);
        this.totalPropertiesSold = this.getTotalPropertiesSoldFromPersonList(personList); //TODO Testing
        this.totalPropertiesBought = this.getTotalPropertiesBoughtFromPersonList(personList); //TODO Testing
        this.totalSalesRevenue = this.getTotalSalesRevenueFromPersonList(personList); //TODO Testing
        this.totalPurchaseExpense = this.getTotalPurchaseExpenseFromPersonList(personList); //TODO Testing
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

    /**
     * Returns the number of {@code persons} in the list of persons.
     */
    public int getTotalPersonsFromPersonList(List<Person> personList) {
        return personList.size();
    }

    /**
     * Returns the total number of {@code propertiesBought} from the list of persons.
     */
    public int getTotalPropertiesBoughtFromPersonList(List<Person> personList) {
        int count = 0;
        for (Person person : personList) {
            count += person.getNumberOfPropertiesBought();
        }
        return count;
    }

    /**
     * Returns the total number of {@code propertiesSold} from the list of persons.
     */
    public int getTotalPropertiesSoldFromPersonList(List<Person> personList) {
        int count = 0;
        for (Person person : personList) {
            count += person.getNumberOfPropertiesSold();
        }
        return count;
    }

    /**
     * Returns the total sales made by all the persons in the current address book.
     */
    public int getTotalSalesRevenueFromPersonList(List<Person> personList) {
        int totalRevenue = 0;
        for (Person person : personList) {
            totalRevenue += person.getSalesRevenue();
        }
        return totalRevenue;
    }

    /**
     * Returns the total expenses incurred by all the persons in the current address book.
     */
    public int getTotalPurchaseExpenseFromPersonList(List<Person> personList) {
        int totalExpense = 0;
        for (Person person : personList) {
            totalExpense += person.getSalesRevenue();
        }
        return totalExpense;
    }

    /**
     * Returns a {@code uniqueSellingPropertyList} of {@code uniqueSellingProperties} from the list of persons.
     */
    public UniqueSellingPropertyList getUniqueSellingPropertyListFromPersonList(List<Person> personList) {
        UniqueSellingPropertyList uniqueSellingPropertyList = new UniqueSellingPropertyList();
        for (Person person : personList) {
            uniqueSellingPropertyList.addUniqueSellingProperties(person.getListOfSellingProperties());
        }
        return uniqueSellingPropertyList;
    }

    /**
     * Returns a {@code uniqueSellingPropertyList} of {@code uniqueSellingProperties} from the list of persons.
     */
    public UniqueBuyingPropertyList getUniqueBuyingPropertyListFromPersonList(List<Person> personList) {
        UniqueBuyingPropertyList uniqueBuyingPropertyList = new UniqueBuyingPropertyList();
        for (Person person : personList) {
            uniqueBuyingPropertyList.addUniqueBuyingProperties(person.getListOfBuyingProperties());
        }
        return uniqueBuyingPropertyList;
    }
}
