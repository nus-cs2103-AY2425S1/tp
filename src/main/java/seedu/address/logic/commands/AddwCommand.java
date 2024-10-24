package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameMatchesKeywordPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Client;
import seedu.address.model.wedding.Wedding;

<<<<<<< HEAD

=======
>>>>>>> master
/**
 * Adds a wedding to the Address Book.
 */
public class AddwCommand extends Command {
    public static final String COMMAND_WORD = "addw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a wedding to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "WEDDING'S NAME "
            + PREFIX_CLIENT + "CLIENT"
            + PREFIX_DATE + "DATE "
            + PREFIX_VENUE + "VENUE ";

    public static final String MESSAGE_SUCCESS = "New wedding added: %1$s";
    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists in the address book";
    public static final String MESSAGE_INVALID_PERSON = "This person does not exist in the address book";
    public static final String MESSAGE_DUPLICATE_HANDLING =
            "Please specify the index of the contact you want to edit.\n"
                    + "Find the index from the list below and type edit INDEX ...\n"
                    + "Example: " + COMMAND_WORD + " 1 ...";

    private final Index index;
    private final NameMatchesKeywordPredicate predicate;
    private Wedding toAdd;

    /**
     * Creates an AddwCommand to add the specified {@code Wedding}
     */
    public AddwCommand(Index index, NameMatchesKeywordPredicate predicate, Wedding wedding) {
        requireNonNull(wedding);
        this.index = index;
        this.predicate = predicate;
        this.toAdd = wedding;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person client;

        if (this.index != null) {
            client = selectClientWithIndex(model);
        } else {
            client = selectClientWithKeyword(model);
        }

        toAdd = new Wedding(toAdd.getName(), new Client(client), toAdd.getDate(), toAdd.getVenue());

        if (model.hasWedding(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_WEDDING);
        }

        client.setOwnWedding(toAdd);
        model.addWedding(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    /**
     * Selects the client based on the provided index.
     *
     * @param model The model containing the list of persons.
     * @return The selected client at the given index.
     * @throws CommandException If the list is empty or the index is invalid.
     */
    public Person selectClientWithIndex(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_PERSON);
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    lastShownList.size()));
        }

        return lastShownList.get(index.getZeroBased());
    }

    /**
<<<<<<< HEAD
     * Returns a {@code Person} from the model's filtered person list based on the given keyword.
     *
     * @param model {@code Model} which the command should operate on
     * @return {@code Person} that matches the keyword
     * @throws CommandException if the list is empty or contains multiple matches
=======
     * Selects the client by matching the name keyword.
     *
     * @param model The model containing the filtered list of persons.
     * @return The selected client.
     * @throws CommandException If no person matches the keyword or there are multiple matches.
>>>>>>> master
     */
    public Person selectClientWithKeyword(Model model) throws CommandException {
        model.updateFilteredPersonList(predicate);
        List<Person> filteredPersonList = model.getFilteredPersonList();

        if (filteredPersonList.isEmpty()) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(MESSAGE_INVALID_PERSON);
        } else if (filteredPersonList.size() == 1) {
            return filteredPersonList.get(0);
        } else {
            throw new CommandException(MESSAGE_DUPLICATE_HANDLING);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddwCommand)) {
            return false;
        }

        AddwCommand otherAddwCommand = (AddwCommand) other;
        return toAdd.equals(otherAddwCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAddw", toAdd)
                .toString();
    }
}
