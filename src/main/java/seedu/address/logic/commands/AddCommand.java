package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRIAGE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.NricMatchesPredicate;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String COMMAND_WORD_SHORT = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient to the database. \n"
            + "Parameters:" + " "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_TRIAGE + "TRIAGE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_NRIC + "S1231231D "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TRIAGE + "1"
            + PREFIX_TAG + "Pacemaker "
            + PREFIX_TAG + "Diabetic";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_NRIC = "A person with that NRIC already exists";
    private final Person toAdd;
    private final NricMatchesPredicate predicate;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
        this.predicate = new NricMatchesPredicate(person.getNric().toString());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredPersonList(predicate);
        if (model.getFilteredPersonList().isEmpty()) {
            model.addPerson(toAdd);
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
        } else {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(MESSAGE_DUPLICATE_NRIC);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
