package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SortCommandParser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts persons in SocialBook according to the specified field.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD

            + ": Sorts all persons in the address book according to the given parameter. "
            + "Only one parameter can be used.\n"
            + "Parameters: "
            + "[name] [address] [priority] [income] [updated]\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by parameter: %s";

    private final String parameter;

    /**
     * Creates a SortCommand to sort the person list using the given {@code parameter}
     */
    public SortCommand(String parameter) {
        assert parameter != null : "Parameter cannot be null";
        this.parameter = parameter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        switch (parameter) {

        case SortCommandParser.NAME:
            model.updateSortingOrder(Comparator.comparing(person -> person.getName().toString()));
            break;

        case SortCommandParser.ADDRESS:
            model.updateSortingOrder(Comparator.comparing(person -> person.getAddress().toString()));
            break;

        case SortCommandParser.PRIORITY:
            model.updateSortingOrder(Comparator.comparing(Person::getPriority)
                    .thenComparing(person -> person.getName().toString()));
            break;

        case SortCommandParser.INCOME:
            model.updateSortingOrder(Comparator.comparing(person -> person.getIncome().getValue()));
            break;

        case SortCommandParser.UPDATED:
            model.updateSortingOrder(Comparator.comparing(person -> person.getUpdatedAt().getValue()));
            break;

        default:
            assert false : "Program should not reach here since parser checks for invalid parameter.";
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, parameter));
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand otherSortCommand)) {
            return false;
        }

        return parameter.equals(otherSortCommand.parameter);
    }
}
