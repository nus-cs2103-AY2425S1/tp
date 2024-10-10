package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static enum Key {
        BUYERS,
        SELLERS,
        CLIENTS,
        PROPERTIES;
    }

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all existing properties, sellers, buyers, or clients in the database.\n"
            + "Command format: " + COMMAND_WORD + " k/KEY\n"
            + "Example commands:\n"
            + "1. List all the buyers: " + COMMAND_WORD + " k/buyers\n"
            + "2. List all the properties: " + COMMAND_WORD + " k/properties\n"
            + "\n"
            + "Parameter considerations:\n"
            + "The key must be one of the following: \"buyers\", \"sellers\", \"clients\", or \"properties\".\n"
            + "Only these four types of records are stored in the database.\n";

    //TODO modify this to actually show the correct thing
    public static final String MESSAGE_SUCCESS = "Listed all %1$s";

    private final Key key;

    // TODO:
    // 1. Store the String key to keep track of the thing (actually can use enum as well honestly)
    // 2. So your ListCommandParser will take the arguments and produce
    // 3. Have a boolean isInvalidKey or something as true, because if that's the case, then return nothing
    // 4. Otherwise, all you need to do it to fetch the correct String and then print the string out as pernormal
    //  (just call their toString honestly)
    // 2. Create a bunch of helper methods that aim to resolve this issue
    // 3. If there's an error, that is if the key doesn't exist or something, then gg throw error
    // 4. Otherwise, parse as per usual!

    public ListCommand(Key key) {
        this.key = key;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        switch (key) {
            case BUYERS:
                // Logic to list buyers
                System.out.println("Listing all buyers");
                break;
            case SELLERS:
                // Logic to list sellers
                System.out.println("Listing all sellers");
                break;
            case CLIENTS:
                // Logic to list clients
                System.out.println("Listing all clients");
                break;
            case PROPERTIES:
                // Logic to list properties
                System.out.println("Listing all properties");
                break;
            default:
                throw new AssertionError("Unexpected key: " + key);
        }
        return new CommandResult(String.format(ListCommand.MESSAGE_SUCCESS, this.key.toString().toLowerCase()));
    }
}
