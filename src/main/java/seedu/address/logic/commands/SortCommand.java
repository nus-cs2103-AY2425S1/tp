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
            + ": Sorts all persons in the address book according to the given parameter.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted all persons by %s";

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

        if (parameter.equals(SortCommandParser.NAME)) {
            model.updateSortingOrder(Comparator.comparing(person -> person.getName().toString()));
        }

        if (parameter.equals(SortCommandParser.ADDRESS)) {
            model.updateSortingOrder(Comparator.comparing(person -> person.getAddress().toString()));
        }

        if (parameter.equals(SortCommandParser.PRIORITY)) {
            model.updateSortingOrder(Comparator.comparing(Person::getPriority)
                    .thenComparing(person -> person.getName().toString()));
        }

        if (parameter.equals(SortCommandParser.INCOME)) {
            model.updateSortingOrder(Comparator.comparing(person -> person.getIncome().getValue()));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, parameter));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return this.parameter.equals(otherSortCommand.parameter);
    }
}
