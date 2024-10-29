package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.rentalinformation.RentalInformation;

/**
 * A UI component that displays information of a {@code RentalInformation}.
 */
public class RentalInformationListCard extends UiPart<Region> {

    private static final String FXML = "RentalInformationListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final RentalInformation rentalInformation;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label address;
    @FXML
    private Label rentalStartDate;
    @FXML
    private Label rentalEndDate;
    @FXML
    private Label rentDueDate;
    @FXML
    private Label monthlyRent;
    @FXML
    private Label deposit;
    @FXML
    private Label customerList;
    @FXML
    private Label ownerName;

    /**
     * Creates a {@code RentalInformationListCard} with the given {@code RentalInformation} and index to display.
     */
    public RentalInformationListCard(RentalInformation rentalInformation, int displayedIndex) {
        super(FXML);
        this.rentalInformation = rentalInformation;
        id.setText(displayedIndex + ". ");
        address.setText(rentalInformation.getAddress().toString());
        rentalStartDate.setText("Rental Start Date: " + rentalInformation.getRentalStartDate().toString());
        rentalEndDate.setText("Rental End Date: " + rentalInformation.getRentalEndDate().toString());
        rentDueDate.setText("Rental Monthly Payment Date: " + rentalInformation.getRentDueDate().toString());
        monthlyRent.setText("Monthly Rent Amount: " + rentalInformation.getMonthlyRent().toString());
        deposit.setText("Deposit Amount: " + rentalInformation.getDeposit().toString());
        customerList.setText("Customers: " + rentalInformation.getCustomerList().toString());
    }
}
