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
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.FilterListPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Deletes a person identified either by their index number in the displayed person list,
 * their phone number, or a name matching the given predicate.
 * This command supports deletion by index, phone number, email, address, or name (partial match).
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by one of the specified attributes.\n"
            + "Parameters: [" + "INDEX" + "] "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private Index targetIndex = null;
    private Phone phoneNumber = null;
    private NameContainsKeywordsPredicate predicate = null;
    private Address address = null;
    private Email email = null;
    private Set<Tag> tags = null;

    /**
     * Constructs a {@code DeleteCommand} that deletes a person identified by a name predicate (partial match).
     *
     * @param predicate The predicate used to find the person by name.
     */
    public DeleteCommand(NameContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    /**
     * Constructs a {@code DeleteCommand} that deletes a person identified by their displayed index.
     *
     * @param targetIndex The index of the person to be deleted.
     */
    public DeleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Constructs a {@code DeleteCommand} that deletes a person identified by their phone number.
     *
     * @param phoneNumber The phone number of the person to be deleted.
     */
    public DeleteCommand(Phone phoneNumber) {
        requireNonNull(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructs a {@code DeleteCommand} that deletes a person identified by their address.
     *
     * @param address The address of the person to be deleted.
     */
    public DeleteCommand(Address address) {
        requireNonNull(address);
        this.address = address;
    }

    /**
     * Constructs a {@code DeleteCommand} that deletes a person identified by a set of tags.
     *
     * @param tags The set of tags used to find the person to be deleted.
     */
    public DeleteCommand(Set<Tag> tags) {
        requireNonNull(tags);
        this.tags = tags;
    }

    /**
     * Constructs a {@code DeleteCommand} that deletes a person identified by their email.
     *
     * @param email The email of the person to be deleted.
     */
    public DeleteCommand(Email email) {
        requireNonNull(email);
        this.email = email;
    }

    /**
     * Executes the delete command.
     * Deletes the person from the model based on the provided criteria (index, phone number, name, address, email, or tags).
     *
     * @param model The model that holds the list of persons.
     * @return The result of the delete command execution.
     * @throws CommandException If no valid person is found by index, phone number, name, address, email, or tags.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToDelete;

        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToDelete = lastShownList.get(targetIndex.getZeroBased());
        } else if (phoneNumber != null) {
            List<Person> personsToDelete = findPersonToDeleteByAttribute(lastShownList, phoneNumber);
            if (personsToDelete.isEmpty()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PHONE_NUMBER);
            } else {
                personToDelete = personsToDelete.get(0);
            }
        } else if (predicate != null) {
            model.updateFilteredPersonList(predicate);
            if (model.getFilteredPersonList().size() == 1) {
                personToDelete = model.getFilteredPersonList().get(0);
            } else {
                return new CommandResult(
                        String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                                model.getFilteredPersonList().size()));
            }
        } else if (address != null) {
            return deletePersonByAttribute(model, address, Messages.MESSAGE_INVALID_ADDRESS);
        } else if (email != null) {
            return deletePersonByAttribute(model, email, Messages.MESSAGE_INVALID_EMAIL);
        } else {
            return deletePersonByAttribute(model, tags, Messages.MESSAGE_INVALID_TAGS);
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
        List<Person> personsToDelete = findPersonToDeleteByAttribute(lastShownList, attribute);
        if (personsToDelete.isEmpty()) {
            throw new CommandException(errorMessage);
        } else if (personsToDelete.size() == 1) {
            Person personToDelete = personsToDelete.get(0);
            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                    Messages.format(personToDelete)));
        } else {
            FilterListPredicate filter = new FilterListPredicate(personsToDelete);
            model.updateFilteredPersonList(filter);
            return new CommandResult(
                    String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                            model.getFilteredPersonList().size()));
        }
    }

    /**
     * Finds persons that match a specified attribute.
     *
     * @param lastShownList The list of all persons.
     * @param attribute The attribute to match against.
     * @return A list of persons that match the attribute.
     */
    private List<Person> findPersonToDeleteByAttribute(List<Person> lastShownList, Object attribute) {
        String type = getTypeOfAttribute(attribute);
        List<Person> matchingPersons = new ArrayList<>();
        for (Person person : lastShownList) {
            switch(type) {
            case "PHONE":
                Phone phoneOfPerson = person.getPhone();
                Phone phoneNumber = (Phone) attribute;
                if (phoneOfPerson.equals(phoneNumber)) {
                    matchingPersons.add(person);
                }
                break;

            case "EMAIL":
                Email emailOfPerson = person.getEmail();
                Email email = (Email) attribute;
                if (emailOfPerson.equals(email)) {
                    matchingPersons.add(person);
                }
                break;

            case "ADDRESS":
                Address addressOfPerson = person.getAddress();
                Address address = (Address) attribute;
                if (addressOfPerson.equals(address)) {
                    matchingPersons.add(person);
                }
                break;

            case "TAG":
                Set<Tag> tagsOfPerson = person.getTags();
                @SuppressWarnings("unchecked")
                Set<Tag> tagsSet = (Set<Tag>) attribute;
                if (tagsOfPerson.containsAll(tagsSet)) {
                    matchingPersons.add(person);
                }
                break;

            default:
                break;
            }
        }
        return matchingPersons;
    }

    /**
     * Determines the type of the attribute.
     *
     * @param attribute The attribute object.
     * @return The type of the attribute as a string.
     */
    private String getTypeOfAttribute(Object attribute) {
        if (attribute instanceof Phone) {
            return "PHONE";
        } else if (attribute instanceof Email) {
            return "EMAIL";
        } else if (attribute instanceof Address) {
            return "ADDRESS";
        } else {
            return "TAG";
        }
    }

    /**
     * Checks if this {@code DeleteCommand} is equal to another object.
     *
     * @param other The object to compare against.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteCommand)) {
            return false;
        }
        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        if (targetIndex != null) {
            return targetIndex.equals(otherDeleteCommand.targetIndex);
        } else {
            return phoneNumber.equals(otherDeleteCommand.phoneNumber);
        }
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
