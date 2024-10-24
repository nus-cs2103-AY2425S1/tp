package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;

import java.util.Comparator;
import java.util.logging.Logger;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts the list of contacts by specified field and order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the list of contacts by specified field and order.\n"
            + "Parameters: "
            + "[" + PREFIX_FIELD + "FIELD] "
            + "[" + PREFIX_ORDER + "ORDER]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FIELD + "Name "
            + PREFIX_ORDER + "L";

    public static final String MESSAGE_AVAILABLE_FIELDS = "Available fields to sort by: Name, NumProp";

    public static final String MESSAGE_INVALID_ORDER =
            "Sort contacts using L for low to high order and H for high to low order";

    public static final String MESSAGE_SORT_SUCCESS = "Contacts sorted by %1$s in %2$s order";

    private static final Logger logger = Logger.getLogger(SortCommand.class.getName());

    private final String field;
    private final String order;

    /**
     * Creates a SortCommand to sort the list of contacts by specified field and order.
     */
    public SortCommand(String field, String order) {
        requireNonNull(field);
        requireNonNull(order);

        this.field = field;
        this.order = order;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.info("Executing SortCommand with field: " + field + " and order: " + order);

        Comparator<Person> comparator;
        switch (field.toLowerCase()) {
        case "name":
            comparator = Comparator.comparing(person -> person.getName().getLowerCaseName());
            break;
        case "numprop":
            comparator = Comparator.comparingInt(Person::getTotalNumProps);
            break;
        default:
            logger.warning("Invalid field specified: " + field);
            throw new CommandException(MESSAGE_AVAILABLE_FIELDS);
        }

        if (order.equalsIgnoreCase("H")) {
            comparator = comparator.reversed();
        } else if (!order.equalsIgnoreCase("L")) {
            logger.warning("Invalid order specified: " + order);
            throw new CommandException(MESSAGE_INVALID_ORDER);
        }

        model.sortPersonList(comparator);

        logger.info("SortCommand executed successfully");

        return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, field, order));
    }
}
