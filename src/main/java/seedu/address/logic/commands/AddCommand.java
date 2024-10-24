package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]\n"
            + "[" + PREFIX_WEDDING + "WEDDING]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_WEDDING + "1"
            + PREFIX_ADDRESS + "123 Main St "
            + PREFIX_TAG + "Groom "
            + PREFIX_TAG + "Wedding1";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book";
    public static final String MESSAGE_PHONE_EXIST = "This number already exists in the address book";
    public static final String MESSAGE_EMAIL_EXIST = "This email already exists in the address book";

    private final Person toAdd;
    private final Set<Index> weddingIndices = new HashSet<>();

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person, Set<Index> weddingIndices) {
        requireNonNull(person);
        toAdd = person;
        this.weddingIndices.addAll(weddingIndices);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }
        if (model.hasPhone(toAdd)) {
            throw new CommandException(MESSAGE_PHONE_EXIST);
        }
        if (model.hasEmail(toAdd)) {
            throw new CommandException(MESSAGE_EMAIL_EXIST);
        }

        generateWeddingJobs(model);

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    /**
     * Generates and adds the wedding jobs {@code toAdd} is involved in.
     *
     * @param model {@code Model} which the command should operate on
     */
    public void generateWeddingJobs(Model model) {
        List<Wedding> weddingList = model.getFilteredWeddingList();

        for (Index index : weddingIndices) {
            toAdd.addWeddingJob(weddingList.get(index.getZeroBased()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }
}
