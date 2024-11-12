package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_WEDDING + "WEDDING...]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_ROLE + "florist "
            + PREFIX_WEDDING + "1";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_WEDDING_DOES_NOT_EXIST = "Wedding %d is not in the list.";

    private final Person toAdd;
    private Set<Index> weddingIndices;

    /**
     * Creates an {@code AddCommand} object to add the specified {@code Person}
     */
    public AddCommand(Person person, Set<Index> weddingIndices) {
        requireNonNull(person);
        toAdd = person;
        this.weddingIndices = weddingIndices != null ? weddingIndices : new HashSet<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_CONTACT);
        }
        if (model.hasPhone(toAdd)) {
            throw new CommandException(Messages.MESSAGE_PHONE_EXIST);
        }
        if (model.hasEmail(toAdd)) {
            throw new CommandException(Messages.MESSAGE_EMAIL_EXIST);
        }

        generateWeddingJobs(model);

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    /**
     * Checks validity of all provided wedding indexes.
     *
     * @param model The model containing the list of weddings.
     */
    public void checkWeddingJobs(Model model) throws CommandException {
        List<Wedding> weddingList = model.getFilteredWeddingList();

        for (Index index : weddingIndices) {
            if (!(index.getZeroBased() >= 0 && index.getZeroBased() < weddingList.size())) {
                throw new CommandException(String.format(MESSAGE_WEDDING_DOES_NOT_EXIST, index.getOneBased()));
            }
        }
    }

    /**
     * Associates the person with wedding jobs based on the provided indices.
     *
     * @param model The model containing the list of weddings.
     */
    public void generateWeddingJobs(Model model) throws CommandException {
        List<Wedding> weddingList = model.getFilteredWeddingList();

        checkWeddingJobs(model);
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

    @Override
    public String toString() {
        return String.format("%s{toAdd=%s}", this.getClass().getCanonicalName(), toAdd);
    }
}
