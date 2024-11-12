package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
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
import seedu.address.model.person.Role;
import seedu.address.model.person.predicates.IsPersonInListPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all persons that match the specified attributes "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: [INDEX] " + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PHONE + "91234567 ";

    private Index targetIndex = null;
    private Phone phoneNumber = null;
    private NameContainsKeywordsPredicate predicate = null;
    private Role role = null;
    private Address address = null;
    private Email email = null;
    private Set<Tag> tags = null;

    private AttributeParser attributeParser = new AttributeParser();

    /**
     * Creates a FindCommand to find persons by name keywords.
     *
     * @param predicate The predicate to filter persons by name keywords.
     */
    public FindCommand(NameContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    /**
     * Creates a FindCommand to find a person by index.
     *
     * @param targetIndex The index of the person to find.
     */
    public FindCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Creates a FindCommand to find a person by phone number.
     *
     * @param phoneNumber The phone number of the person to find.
     */
    public FindCommand(Phone phoneNumber) {
        requireNonNull(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    /**
     * Creates a FindCommand to find a person by address.
     *
     * @param address The address of the person to find.
     */
    public FindCommand(Address address) {
        requireNonNull(address);
        this.address = address;
    }

    /**
     * Creates a FindCommand to find a person by email.
     *
     * @param email The email of the person to find.
     */
    public FindCommand(Email email) {
        requireNonNull(email);
        this.email = email;
    }

    /**
     * Creates a FindCommand to find a person by tags.
     *
     * @param tags The tags associated with the person to find.
     */
    public FindCommand(Set<Tag> tags) {
        requireNonNull(tags);
        this.tags = tags;
    }

    /**
     * Creates a FindCommand to find a person by role.
     *
     * @param role The role of the person to find.
     */
    public FindCommand(Role role) {
        requireNonNull(role);
        this.role = role;
    }

    /**
     * Executes the FindCommand, filtering and listing persons in the address book
     * based on specified attributes.
     *
     * @param model The model containing the data to search within.
     * @return A CommandResult with the results of the find operation.
     * @throws CommandException If the specified index is out of range.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        assert lastShownList != null : "Last shown list should not be null";
        List<Person> personsToFind = new ArrayList<>();
        if (predicate != null) {
            return executeByPredicate(model);
        } else if (targetIndex != null) {
            Person personToFind = executeByIndex(lastShownList);
            personsToFind.add(personToFind);
        } else if (phoneNumber != null) {
            personsToFind = attributeParser.findPersonByAttribute(lastShownList, phoneNumber);
        } else if (address != null) {
            personsToFind = attributeParser.findPersonByAttribute(lastShownList, address);
        } else if (email != null) {
            personsToFind = attributeParser.findPersonByAttribute(lastShownList, email);
        } else if (role != null) {
            personsToFind = attributeParser.findPersonByAttribute(lastShownList, role);
        } else {
            personsToFind = attributeParser.findPersonByAttribute(lastShownList, tags);
        }

        return updateModel(model, personsToFind);
    }

    /**
     * Executes the command by filtering persons based on the specified predicate.
     *
     * @param model The model to update.
     * @return The result of the command, showing filtered persons.
     */
    public CommandResult executeByPredicate(Model model) {
        model.updateFilteredPersonList(predicate);
        return filteredCommandResult(model);
    }

    /**
     * Finds and returns a person based on the specified index in the last shown list.
     *
     * @param lastShownList The current list of displayed persons.
     * @return The person at the specified index.
     * @throws CommandException If the index is invalid.
     */
    public Person executeByIndex(List<Person> lastShownList)
            throws CommandException {
        int zeroBasedIndex = targetIndex.getZeroBased();
        int lastShownListSize = lastShownList.size();
        if (zeroBasedIndex >= lastShownListSize) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return lastShownList.get(zeroBasedIndex);
    }

    /**
     * Updates the model's filtered person list to show only the specified persons.
     *
     * @param model The model to update.
     * @param personsToFind The list of persons to display.
     * @return The result of the command after filtering.
     */
    public CommandResult updateModel(Model model, List<Person> personsToFind) {
        IsPersonInListPredicate filter = new IsPersonInListPredicate(personsToFind);
        model.updateFilteredPersonList(filter);
        return filteredCommandResult(model);
    }

    /**
     * Creates and returns the command result based on the filtered list size.
     *
     * @param model The model containing the filtered list.
     * @return The result of the command, displaying the count of persons listed.
     */
    public CommandResult filteredCommandResult(Model model) {
        String listedPersons = Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
        List<Person> filteredList = model.getFilteredPersonList();
        int filteredListSize = filteredList.size();
        return new CommandResult(
                String.format(listedPersons, filteredListSize));
    }

    /**
     * Checks if this FindCommand is equal to another FindCommand by comparing
     * their attributes.
     *
     * @param other The other object to compare.
     * @return True if the attributes match, otherwise false.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return isSameTargetIndex(otherFindCommand)
                || isSamePhoneNumber(otherFindCommand)
                || isSameAddress(otherFindCommand)
                || isSameEmail(otherFindCommand)
                || isSameTags(otherFindCommand)
                || isSamePredicate(otherFindCommand)
                || isSameRole(otherFindCommand);
    }

    /**
     * Checks if the target index of this FindCommand matches that of another.
     *
     * @param other The other FindCommand to compare.
     * @return True if the target indexes match, otherwise false.
     */
    public boolean isSameTargetIndex(FindCommand other) {
        Index otherTargetIndex = other.targetIndex;
        return targetIndex != null && targetIndex.equals(otherTargetIndex);
    }

    /**
     * Checks if the phone number of this FindCommand matches that of another.
     *
     * @param other The other FindCommand to compare.
     * @return True if the phone numbers match, otherwise false.
     */
    public boolean isSamePhoneNumber(FindCommand other) {
        Phone otherPhone = other.phoneNumber;
        return phoneNumber != null && phoneNumber.equals(otherPhone);
    }

    /**
     * Checks if the address of this FindCommand matches that of another.
     *
     * @param other The other FindCommand to compare.
     * @return True if the addresses match, otherwise false.
     */
    public boolean isSameAddress(FindCommand other) {
        Address otherAddress = other.address;
        return address != null && address.equals(otherAddress);
    }

    /**
     * Checks if the email of this FindCommand matches that of another.
     *
     * @param other The other FindCommand to compare.
     * @return True if the emails match, otherwise false.
     */
    public boolean isSameEmail(FindCommand other) {
        Email otherEmail = other.email;
        return email != null && email.equals(otherEmail);
    }

    /**
     * Checks if the tags of this FindCommand match those of another.
     *
     * @param other The other FindCommand to compare.
     * @return True if the tag sets match, otherwise false.
     */
    public boolean isSameTags(FindCommand other) {
        Set<Tag> otherTags = other.tags;
        return tags != null && tags.equals(otherTags);
    }

    /**
     * Checks if predicate of this FindCommand matches that of the other
     *
     * @param other The other FindCommand to compare.
     * @return True if the tag sets match, otherwise false.
     */
    public boolean isSamePredicate(FindCommand other) {
        NameContainsKeywordsPredicate otherPredicate = other.predicate;
        return predicate != null && predicate.equals(otherPredicate);
    }

    /**
     * Returns a string representation of the FindCommand, showing the predicate if present.
     *
     * @param other The other FindCommand to compare.
     * @return True if the tag sets match, otherwise false.
     */
    public boolean isSameRole(FindCommand other) {
        Role otherRole = other.role;
        return role != null && role.equals(otherRole);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
