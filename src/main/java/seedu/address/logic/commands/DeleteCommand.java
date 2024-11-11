package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AttributeParser;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.predicates.IsPersonInListPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Deletes a person identified either by their index number in the displayed person list,
 * their phone number, or a name matching the given predicate.
 * This command supports deletion by index, phone number, email, address, or name (partial match).
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the specified attributes.\n"
            + "Parameters: [" + "INDEX" + "] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private Index targetIndex = null;
    private Phone phoneNumber = null;
    private NameContainsKeywordsPredicate predicate = null;
    private Address address = null;
    private Email email = null;
    private Set<Tag> tags = null;
    private AttributeParser attributeParser = new AttributeParser();

    /**
     * Constructs a {@code DeleteCommand} for deleting a person by partial name match.
     *
     * @param predicate The predicate used to match the person by name.
     */
    public DeleteCommand(NameContainsKeywordsPredicate predicate) {
        //defensive programming
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    /**
     * Constructs a {@code DeleteCommand} that deletes a person identified by their displayed index.
     *
     * @param targetIndex The index of the person to be deleted.
     */
    public DeleteCommand(Index targetIndex) {
        //defensive programming
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Constructs a {@code DeleteCommand} that deletes a person identified by their phone number.
     *
     * @param phoneNumber The phone number of the person to be deleted.
     */
    public DeleteCommand(Phone phoneNumber) {
        //defensive programming
        requireNonNull(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructs a {@code DeleteCommand} that deletes a person identified by their address.
     *
     * @param address The address of the person to be deleted.
     */
    public DeleteCommand(Address address) {
        //defensive programming
        requireNonNull(address);
        this.address = address;
    }

    /**
     * Constructs a {@code DeleteCommand} that deletes a person identified by a set of tags.
     *
     * @param tags The set of tags used to find the person to be deleted.
     */
    public DeleteCommand(Set<Tag> tags) {
        //defensive programming
        requireNonNull(tags);
        this.tags = tags;
    }

    /**
     * Constructs a {@code DeleteCommand} that deletes a person identified by their email.
     *
     * @param email The email of the person to be deleted.
     */
    public DeleteCommand(Email email) {
        //defensive programming
        requireNonNull(email);
        this.email = email;
    }

    /**
     * Executes the delete command.
     * Deletes the person from the model based on the provided criteria.
     *
     * @param model The model that holds the list of persons.
     * @return The result of the delete command execution.
     * @throws CommandException If no valid person is found.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        //defensive programming
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        //ensure last shown list is not null, for subsequent code
        assert lastShownList != null : "Last shown list should not be null";

        if (targetIndex != null) {
            return executeDeleteByIndex(model, lastShownList);
        } else if (phoneNumber != null) {
            return deletePersonByAttribute(model, phoneNumber, Messages.MESSAGE_INVALID_PHONE_NUMBER);
        } else if (predicate != null) {
            return executeDeleteByPredicate(model);
        } else if (address != null) {
            return deletePersonByAttribute(model, address, Messages.MESSAGE_INVALID_ADDRESS);
        } else if (email != null) {
            return deletePersonByAttribute(model, email, Messages.MESSAGE_INVALID_EMAIL);
        } else {
            return deletePersonByAttribute(model, tags, Messages.MESSAGE_INVALID_TAGS);
        }
    }

    /**
     * Executes the delete operation by index.
     *
     * @param model The model that holds the list of persons.
     * @param lastShownList The list of persons currently displayed.
     * @return The result of the delete command execution.
     * @throws CommandException If the index is out of bounds.
     */
    public CommandResult executeDeleteByIndex(Model model, List<Person> lastShownList)
            throws CommandException {
        int zeroBasedIndex = targetIndex.getZeroBased();
        int lastShownListSize = lastShownList.size();
        if (zeroBasedIndex >= lastShownListSize) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete)));

    }

    /**
     * Executes the delete operation using a name predicate.
     *
     * @param model The model that holds the list of persons.
     * @return The result of the delete command execution.
     */
    public CommandResult executeDeleteByPredicate(Model model) {
        model.updateFilteredPersonList(predicate);
        List<Person> filteredList = model.getFilteredPersonList();
        Person personToDelete;
        int filteredListSize = filteredList.size();
        if (filteredListSize == 1) {
            personToDelete = model.getFilteredPersonList().get(0);
        } else {
            return new CommandResult(
                    String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                            filteredListSize));
        }
        model.deletePerson(personToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete)));
    }

    /**
     * Deletes a person based on a specific attribute (address, email, or tags).
     *
     * @param model The model containing the list of persons.
     * @param attribute The attribute to be matched for deletion.
     * @param errorMessage The error message if no persons match the attribute.
     * @return The result of the delete operation.
     * @throws CommandException If no persons match the attribute.
     */
    private CommandResult deletePersonByAttribute(Model model, Object attribute,
                                                  String errorMessage) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> personsToDelete = attributeParser.findPersonByAttribute(lastShownList, attribute);
        if (personsToDelete.isEmpty()) {
            throw new CommandException(errorMessage);
        } else if (personsToDelete.size() == 1) {
            return deleteSingleMatchPerson(personsToDelete, model);
        } else {
            return deleteMultipleMatchPersons(personsToDelete, model);
        }
    }

    /**
     * Deletes a single matching person from the provided list of persons to delete.
     *
     * @param personsToDelete The list containing a single matching person to delete.
     * @param model The model that holds the list of persons.
     * @return The result of the delete command execution, indicating successful deletion.
     */
    private CommandResult deleteSingleMatchPerson(List<Person> personsToDelete, Model model) {
        Person personToDelete = personsToDelete.get(0);
        model.deletePerson(personToDelete);
        String deletedPersons = Messages.format(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                deletedPersons));
    }

    /**
     * Updates the filtered list with multiple matching persons and returns a command result
     * showing the number of matches.
     *
     * @param personsToDelete The list of persons matching the delete criteria.
     * @param model The model that holds the list of persons.
     * @return The result of the delete command, displaying the number of matches.
     */
    private CommandResult deleteMultipleMatchPersons(List<Person> personsToDelete, Model model) {
        IsPersonInListPredicate filter = new IsPersonInListPredicate(personsToDelete);
        model.updateFilteredPersonList(filter);
        List<Person> filteredList = model.getFilteredPersonList();
        int filteredListSize = filteredList.size();
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                        filteredListSize));

    }

    /**
     * Checks if this {@code DeleteCommand} is equal to another object.
     *
     * @param other The object to compare against.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeleteCommand)) {
            return false;
        }
        DeleteCommand otherDeleteCommand = (DeleteCommand) other;

        return isSameTargetIndex(otherDeleteCommand)
                || isSamePhoneNumber(otherDeleteCommand)
                || isSameAddress(otherDeleteCommand)
                || isSameEmail(otherDeleteCommand)
                || isSameTags(otherDeleteCommand)
                || isSamePredicate(otherDeleteCommand);
    }

    /**
     * Checks if the target index of this {@code DeleteCommand} matches that of another.
     *
     * @param other The other DeleteCommand to compare.
     * @return True if the target indexes match, otherwise false.
     */
    public boolean isSameTargetIndex(DeleteCommand other) {
        Index otherTargetIndex = other.targetIndex;
        return targetIndex != null && targetIndex.equals(otherTargetIndex);
    }

    /**
     * Checks if the phone number of this {@code DeleteCommand} matches that of another.
     *
     * @param other The other DeleteCommand to compare.
     * @return True if the phone numbers match, otherwise false.
     */
    public boolean isSamePhoneNumber(DeleteCommand other) {
        Phone otherPhone = other.phoneNumber;
        return phoneNumber != null && phoneNumber.equals(otherPhone);
    }

    /**
     * Checks if the address of this {@code DeleteCommand} matches that of another.
     *
     * @param other The other DeleteCommand to compare.
     * @return True if the addresses match, otherwise false.
     */
    public boolean isSameAddress(DeleteCommand other) {
        Address otherAddress = other.address;
        return address != null && address.equals(otherAddress);
    }

    /**
     * Checks if the email of this {@code DeleteCommand} matches that of another.
     *
     * @param other The other DeleteCommand to compare.
     * @return True if the emails match, otherwise false.
     */
    public boolean isSameEmail(DeleteCommand other) {
        Email otherEmail = other.email;
        return email != null && email.equals(otherEmail);
    }

    /**
     * Checks if the tags of this {@code DeleteCommand} match those of another.
     *
     * @param other The other DeleteCommand to compare.
     * @return True if the tag sets match, otherwise false.
     */
    public boolean isSameTags(DeleteCommand other) {
        Set<Tag> otherTags = other.tags;
        return tags != null && tags.equals(otherTags);
    }

    /**
     * Checks if the predicate of this {@code DeleteCommand} matches that of another.
     *
     * @param other The other DeleteCommand to compare.
     * @return True if the predicates match, otherwise false.
     */
    public boolean isSamePredicate(DeleteCommand other) {
        NameContainsKeywordsPredicate otherPredicate = other.predicate;
        return predicate != null && predicate.equals(otherPredicate);
    }

    /**
     * Returns a string representation of the command.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
