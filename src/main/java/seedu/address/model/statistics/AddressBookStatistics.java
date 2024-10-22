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
    private UniqueBuyingPropertyList buyingPropertyList;
    private UniqueSellingPropertyList sellingPropertyList;


    private int totalPropertiesBought;

    private int totalPropertiesSold;
    private int totalSales;
    private int totalExpenses;
    private UniquePersonList persons;
    public AddressBookStatistics() {
        this.totalPersons = 0;
        this.totalPropertiesBought = 0;
        this.totalPropertiesSold = 0;
        this.totalSales = 0;
        this.totalExpenses = 0;
        this.buyingPropertyList = new UniqueBuyingPropertyList();
        this.sellingPropertyList = new UniqueSellingPropertyList();
    }

    public void incrementTotalPersonCount() {
        this.totalPersons += 1;
    }

    public void processPersonData(Person person) {
        requireNonNull(person);
        ObservableList<Property> listoOfSellingProperties = person.getListOfSellingProperties();
        ObservableList<Property> listoOfBuyingProperties = person.getListOfBuyingProperties();
        this.sellingPropertyList.addSellingProperties(listoOfSellingProperties);
        this.buyingPropertyList.addBuyingProperties(listoOfBuyingProperties);

    }
}
