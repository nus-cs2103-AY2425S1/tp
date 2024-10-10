package seedu.address.logic.commands;

// import static java.util.Objects.requireNonNull;
// import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUSING_TYPE;
// import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
// import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLING_PRICE;
// import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
// import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
// import seedu.address.model.person.Property;

/**
 * Adds a property to the list of properties to buy for a specific contact.
 */
public class AddPropertyToBuyCommand extends Command {
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("Hello from AddPropertyToBuyCommand");
    }
}
