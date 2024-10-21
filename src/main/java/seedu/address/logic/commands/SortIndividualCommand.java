package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Property;

/**
 * Sorts the list of properties in the address book by price based on the order input by user.
 */
public class SortIndividualCommand extends Command {
    public static final String COMMAND_WORD = "sorti";
    private static String order;
    private static String field;
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list of properties based on input field.\n"
            + "Parameters: "
            + "INDEX (Must be a positive integer) "
            + PREFIX_FIELD + "[FIELD] "
            + PREFIX_ORDER + "[ORDER]"
            + " (either \"o/H for High to Low\" or \"o/L for Low to High\")\n"
            + "Example: " + COMMAND_WORD + " " + "1 " + PREFIX_FIELD + "Price " + PREFIX_ORDER + "L";
    private final Index personIndexToSort;
    /**
     * Creates an SortIndividualCommand to sort the list of properties by price.
     * @param index of the person in the filtered person list to edit
     * @param order of the sorting
     */
    public SortIndividualCommand(Index index, String field, String order) {
        this.personIndexToSort = index;
        this.order = order;
        this.field = field;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndexToSort.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToSort = lastShownList.get(personIndexToSort.getZeroBased());
        ObservableList<Property> buyingListOfPropertiesToSort = personToSort.getListOfBuyingProperties();
        ObservableList<Property> sellingListOfPropertiesToSort = personToSort.getListOfSellingProperties();
        sortBothListOfPropertiesBasedOnGivenOrder(buyingListOfPropertiesToSort, sellingListOfPropertiesToSort,
                this.order);
        String successMessage = "Sorted " + personToSort.getName() + " by Price from " + getOrderString(this.order);
        return new CommandResult(successMessage);
    }
    /**
     * Sorts the list of properties based on the order input by user.
     * @param listOfBuyingProperties
     * @param listOfSellingProperties
     * @param order
     */
    private static void sortBothListOfPropertiesBasedOnGivenOrder(ObservableList<Property> listOfBuyingProperties,
                                                                  ObservableList<Property> listOfSellingProperties,
                                                                  String order) {
        if (order.equals("L") && field.equals("Price")) {
            listOfBuyingProperties.sort((property1, property2) -> property1.getPrice().compareTo(property2.getPrice()));
            listOfSellingProperties.sort((property1, property2) ->
                    property1.getPrice().compareTo(property2.getPrice()));
        } else if (order.equals("H") && field.equals("Price")) {
            listOfBuyingProperties.sort((property1, property2) -> property2.getPrice().compareTo(property1.getPrice()));
            listOfSellingProperties.sort((property1, property2) ->
                    property2.getPrice().compareTo(property1.getPrice()));
        }
    }
    /**
     * Returns the order in string format.
     * @param order
     * @return order in string format
     */
    private static String getOrderString(String order) {
        if (order.equals("L")) {
            return "Low to High";
        } else {
            return "High to Low";
        }
    }
}
