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

/**
 * Adds a wedding to the Address Book.
 */
public class AddwCommand extends Command {
    public static final String COMMAND_WORD = "addw";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a wedding to the address book. \n"
            + "Parameters: "
            + PREFIX_NAME + "WEDDING'S NAME "
            + PREFIX_CLIENT + "CLIENT "
            + "[" + PREFIX_DATE + "DATE" + "] "
            + "[" + PREFIX_VENUE + "VENUE" + "], "
            + "where client can be INDEX (must be positive integer) or KEYWORD (the name of contact, "
            + "case-insensitive)\n"
            + "Example: \n"
            + COMMAND_WORD + " " + PREFIX_NAME + "Alice Wedding " + PREFIX_CLIENT + "1 " + PREFIX_VENUE + "Hotel\n"
            + COMMAND_WORD + " " + PREFIX_NAME + "Alice Wedding " + PREFIX_CLIENT + "Alice " + PREFIX_DATE
            + "2024-12-12";

    public static final String MESSAGE_SUCCESS = "New wedding added: %1$s";
    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists in the address book";
    public static final String MESSAGE_INVALID_PERSON = "This person does not exist in the address book";
    public static final String MESSAGE_ALREADY_A_CLIENT = "This person is already a client for another wedding";

    public static final String MESSAGE_DUPLICATE_HANDLING =
            "Please specify the index of the contact you want to set as client.\n"
                    + "Find the index from the list below and type edit INDEX ...\n"
                    + "Example: " + COMMAND_WORD + "n/WEDDING NAME c/1 ...";

    private final Index index;
    private final NameMatchesKeywordPredicate predicate;
    private Wedding toAdd;

    /**
     * Creates a new AddwCommand to add the specified wedding.
     * Either index or predicate must be provided to identify the client,
     * but not both.
     *
     * @param index The index of the person to set as client. Can be null if using name matching.
     * @param predicate The predicate to match person by name. Can be null if using index.
     * @param wedding The wedding to be added to the address book.
     * @throws NullPointerException if the wedding parameter is null
     */
    public AddwCommand(Index index, NameMatchesKeywordPredicate predicate, Wedding wedding) {
        requireNonNull(wedding);
        this.index = index;
        this.predicate = predicate;
        this.toAdd = wedding;
    }

    /**
     * Executes the command to add a new wedding to the address book.
     * This method will:
     * 1. Identify the client using either index or name matching
     * 2. Verify the client is not already associated with another wedding
     * 3. Create a new wedding with the identified client
     * 4. Add the wedding to the model if it's not a duplicate
     *
     * @param model The model which the command should operate on.
     * @return A CommandResult indicating the outcome of the command execution
     * @throws CommandException if:
     *         - The specified person does not exist
     *         - The person is already a client for another wedding
     *         - The wedding is a duplicate
     *         - Multiple persons match the provided name
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person client;

        if (this.index != null) {
            client = selectClientWithIndex(model);
        } else {
            client = selectClientWithKeyword(model);
        }

        if (client.hasOwnWedding()) {
            throw new CommandException(MESSAGE_ALREADY_A_CLIENT);
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
     * Selects the client by matching the name keyword.
     *
     * @param model The model containing the filtered list of persons.
     * @return The selected client.
     * @throws CommandException If no person matches the keyword or there are multiple matches.
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

    /**
     * Compares this AddwCommand with another object for equality.
     * Two AddwCommand objects are considered equal if they have:
     * - The same index (or both null)
     * - The same predicate (or both null)
     * - Equal wedding objects
     *
     * @param other The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddwCommand)) {
            return false;
        }

        AddwCommand otherAddwCommand = (AddwCommand) other;

        boolean indexEqual = (index == null && otherAddwCommand.index == null)
                || (index != null && index.equals(otherAddwCommand.index));

        boolean predicateEqual = (predicate == null && otherAddwCommand.predicate == null)
                || (predicate != null && predicate.equals(otherAddwCommand.predicate));

        return indexEqual && predicateEqual && toAdd.equals(otherAddwCommand.toAdd);

    }

    /**
     * Returns a string representation of the AddwCommand object.
     * Includes the index, predicate, and wedding to be added.
     *
     * @return A string representation of this command
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("predicate", predicate)
                .add("toAddw", toAdd)
                .toString();
    }
}
