package seedu.address.model.person;

public class AddressBookStatistics {

    private int totalPersons;
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
    }

    public void incrementTotalPersonCount() {
        this.totalPersons += 1;
    }
}
