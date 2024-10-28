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

    public static final String MESSAGE_SUCCESS_MAIN = "Contacts have been sorted by ";
    public static final String MESSAGE_SUCCESS_INSPECT = "Deliveries have been sorted by ";

    public static final String MESSAGE_USAGE_ASCENDING_MAIN = COMMAND_WORD_ASCENDING
            + ": Sorts contacts by the specified attribute in ascending order. "
            + "Parameters: "
            + PREFIX_SORT + " CONTACT_ATTRIBUTE (e.g. date (default), email, name, phone, role, id)";

    public static final String MESSAGE_USAGE_DESCENDING_MAIN = COMMAND_WORD_DESCENDING
            + ": Sorts contacts by the specified attribute in descending order. "
            + "Parameters: "
            + PREFIX_SORT + " CONTACT_ATTRIBUTE (e.g. date (default), email, name, phone, role, id)";

    public static final String MESSAGE_USAGE_ASCENDING_INSPECT = COMMAND_WORD_ASCENDING
            + ": Sorts deliveries by the specified attribute in ascending order. "
            + "Parameters: "
            + PREFIX_SORT + " DELIVERY_ATTRIBUTE (e.g. address, cost, date (default), eta, id, status)";

    public static final String MESSAGE_USAGE_DESCENDING_INSPECT = COMMAND_WORD_DESCENDING
            + ": Sorts deliveries by the specified attribute in descending order. "
            + "Parameters: "
            + PREFIX_SORT + " DELIVERY_ATTRIBUTE (e.g. address, cost, date (default), eta, id, status)";

    public static final String MESSAGE_UNKNOWN_ATTRIBUTE_MAIN = "The contact attribute specified is unknown! "
            + "Current attributes supported are: date (default), email, name, phone, role";

    public static final String MESSAGE_UNKNOWN_ATTRIBUTE_INSPECT = "The delivery attribute specified is unknown! "
            + "Current attributes supported are: address, cost, date (default), eta, id, status";

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
            switch (this.attribute) {
            case "date":
                model.sortByDate();
                break;
            case "email":
                model.sortByEmail();
                break;
            case "name":
                model.sortByName();
                break;
            case "phone":
                model.sortByPhone();
                break;
            case "role":
                model.sortByRole();
                break;
            default:
                // This should never happen since we have already parsed the attribute.
                throw new CommandException(MESSAGE_UNKNOWN_ATTRIBUTE_MAIN);
            }

            if (!isAscending) {
                model.reversePersonList();
            }

            return new CommandResult(MESSAGE_SUCCESS_MAIN + (this.isAscending ? "ascending " : "descending ")
                    + this.attribute);
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
                throw new CommandException(MESSAGE_UNKNOWN_ATTRIBUTE_INSPECT);
            }

            if (!isAscending) {
                deliveryList.reverseDeliveryList();
            }

            return new CommandResult(MESSAGE_SUCCESS_INSPECT + (this.isAscending ? "ascending " : "descending ")
                    + this.attribute);
        }
    }
}
