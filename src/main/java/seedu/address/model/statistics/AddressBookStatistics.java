package seedu.address.model.statistics;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.UniqueBuyingPropertyList;
import seedu.address.model.person.UniqueSellingPropertyList;

import static java.util.Objects.requireNonNull;


public class AddressBookStatistics {

    private int totalPersons;
    private UniqueBuyingPropertyList uniqueBuyingPropertyList;
    private UniqueSellingPropertyList uniqueSellingPropertyList;


    private int totalPropertiesBought;

    private int totalPropertiesSold;
    private int totalSalesRevenue;
    private int totalPurchaseExpense;
    private UniquePersonList persons;
    public AddressBookStatistics() {
        this.totalPersons = 0;
        this.totalPropertiesBought = 0;
        this.totalPropertiesSold = 0;
        this.totalSalesRevenue = 0;
        this.totalPurchaseExpense = 0;
        this.uniqueSellingPropertyList = new UniqueSellingPropertyList();
        this.uniqueBuyingPropertyList = new UniqueBuyingPropertyList();
    }

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
}
