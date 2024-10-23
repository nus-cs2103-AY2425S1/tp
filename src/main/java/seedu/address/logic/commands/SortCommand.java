package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.delivery.DeliveryList;
import seedu.address.model.person.Person;
import seedu.address.ui.InspectWindow;

/**
 * Sorts deliveries by a specified attribute.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD_ASCENDING = "asort";
    public static final String COMMAND_WORD_DESCENDING = "dsort";

    public static final String MESSAGE_FAILURE = "Sort only works in inspect window!";
    public static final String MESSAGE_SUCCESS = "Deliveries have been sorted by ";

    public static final String MESSAGE_USAGE_ASCENDING = COMMAND_WORD_ASCENDING
            + ": Sorts deliveries by the specified attribute in ascending order. "
            + "Parameters: "
            + PREFIX_SORT + " DELIVERY_ATTRIBUTE (e.g. address, cost, date, eta, id, status)";
    public static final String MESSAGE_USAGE_DESCENDING = COMMAND_WORD_DESCENDING
            + ": Sorts deliveries by the specified attribute in descending order. "
            + "Parameters: "
            + PREFIX_SORT + " DELIVERY_ATTRIBUTE (e.g. address, cost, date, eta, id, status)";

    public static final String MESSAGE_UNKNOWN_ATTRIBUTE = "The delivery attribute specified is unknown! "
            + "Current attributes supported are: address, cost, date, eta, id, status";

    private final String attribute;
    private final boolean isAscending;

    /**
     * Creates a SortCommand to sort deliveries by the specified attribute, in the specified order.
     */
    public SortCommand(String attribute, boolean isAscending) {
        requireNonNull(attribute);
        this.attribute = attribute;
        this.isAscending = isAscending;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!AddressBookParser.getInspect()) {
            return new CommandResult(MESSAGE_FAILURE);
        } else {
            Person inspectedPerson = InspectWindow.getInspectedPerson();
            DeliveryList deliveryList = inspectedPerson.getDeliveryList();
            switch (this.attribute) {
            case "address":
                deliveryList.sortByAddress();
                break;
            case "cost":
                deliveryList.sortByCost();
                break;
            case "date":
                deliveryList.sortByDate();
                break;
            case "eta":
                deliveryList.sortByEta();
                break;
            case "id":
                deliveryList.sortById();
                break;
            case "status":
                deliveryList.sortByStatus();
                break;
            default:
                // This should never happen since we have already parsed the attribute.
                throw new CommandException(MESSAGE_UNKNOWN_ATTRIBUTE);
            }

            if (!isAscending) {
                deliveryList.reverseDeliveryList();
            }

            return new CommandResult(MESSAGE_SUCCESS + (this.isAscending ? "ascending " : "descending ")
                    + this.attribute);
        }
    }
}
