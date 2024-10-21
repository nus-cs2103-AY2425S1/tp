package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.List;

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_FAILURE = "Sort only works in inspect window!";
    public static final String MESSAGE_SUCCESS = "Deliveries have been sorted by ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts deliveries by the specified attribute. "
            + "Parameters: "
            + PREFIX_SORT + " DELIVERY_ATTRIBUTE (e.g. eta, cost, etc.)";

    private final String attribute;

    public SortCommand(String attribute) {
        requireNonNull(attribute);
        this.attribute = attribute;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!AddressBookParser.getInspect()) {
            return new CommandResult(MESSAGE_FAILURE);
        } else {
            return new CommandResult(MESSAGE_SUCCESS + this.attribute);
        }
    }
}
