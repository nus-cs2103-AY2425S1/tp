package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmailPredicate;
import seedu.address.model.person.FullNameMatchesPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameEmailPredicate;
import seedu.address.model.person.NamePhonePredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PhonePredicate;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person with a positive integer index/name/email/phone/name & email/name & phone \n"
            + "Parameters:\n"
            + "n/NAME\n"
            + "n/EMAIL\n"
            + "n/PHONE\n"
            + "n/NAME e/EMAIL\n"
            + "n/NAME p/PHONE\n"
            + "Examples:\n" + COMMAND_WORD + " 1\n"
            + COMMAND_WORD + " n/John Doe\n"
            + COMMAND_WORD + " e/johndoe@gmail.com\n"
            + COMMAND_WORD + " p/88308830\n"
            + COMMAND_WORD + " n/John Doe e/johndoe@gmail.com\n"
            + COMMAND_WORD + " n/John Doe p/88308830\n";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Optional<Index> targetIndex;
    private final Optional<Name> name;
    private final Optional<Phone> phone;
    private final Optional<Email> email;

    /**
     * Initialises DeleteCommand using Index.
     * @param targetIndex Index object containing index to be deleted.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = Optional.of(targetIndex);
        this.name = Optional.empty();
        this.phone = Optional.empty();
        this.email = Optional.empty();
    }

    /**
     * Initialises DeleteCommand using Index.
     * @param name Name object containing full name.
     * @param phone Phone object containing phone number.
     */
    public DeleteCommand(Name name, Phone phone) {
        this.targetIndex = Optional.empty();
        this.name = Optional.of(name);
        this.phone = Optional.of(phone);
        this.email = Optional.empty();
    }

    /**
     * Initialises DeleteCommand using Index.
     * @param name Name object containing full name.
     * @param email Email object containing email.
     */
    public DeleteCommand(Name name, Email email) {
        this.targetIndex = Optional.empty();
        this.name = Optional.of(name);
        this.phone = Optional.empty();
        this.email = Optional.of(email);
    }

    /**
     * Initialises DeleteCommand using Index.
     * @param name Name object containing full name.
     */
    public DeleteCommand(Name name) {
        this.targetIndex = Optional.empty();
        this.name = Optional.of(name);
        this.phone = Optional.empty();
        this.email = Optional.empty();
    }

    /**
     * Initialises DeleteCommand using Email only
     * @param email Email object containing the contact's email.
     */
    public DeleteCommand(Email email) {
        this.targetIndex = Optional.empty();
        this.name = Optional.empty();
        this.phone = Optional.empty();
        this.email = Optional.of(email);
    }

    /**
     * Initialises DeleteCommand using Phone only
     * @param phone Phone object containing the contact's email.
     */
    public DeleteCommand(Phone phone) {
        this.targetIndex = Optional.empty();
        this.name = Optional.empty();
        this.phone = Optional.of(phone);
        this.email = Optional.empty();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Person> lastShownList = model.getFilteredPersonList();
        FilteredList<Person> listForFilter = new FilteredList<>(lastShownList);
        assert listForFilter != null : "FilteredList should not be null";
        // Check if index is not empty
        if (targetIndex.isPresent()) {
            return this.deleteByIndex(targetIndex, model, lastShownList);
        } else if (name.isPresent() && phone.isPresent()) {
            return deleteByNamePhone(name, phone, model, listForFilter);
        } else if (name.isPresent() && email.isPresent()) {
            return deleteByNameEmail(name, email, model, listForFilter);
        } else if (name.isPresent()) {
            return deleteByName(name, model, listForFilter);
        } else if (email.isPresent()) {
            return deleteByEmail(email, model, listForFilter);
        } else if (phone.isPresent()) {
            return deleteByPhone(phone, model, listForFilter);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

    /**
     * Deletes the person based on the index provided.
     * @param targetIndex Optional containing Index.
     * @param model ModelManager containing the address book.
     * @param lastShownList the extracted latest contact list.
     * @return CommandResult that shows that contact of a specified index is deleted.
     * @throws CommandException containing custom Exception messages.
     */
    private CommandResult deleteByIndex(Optional<Index> targetIndex, Model model,
                                      ObservableList<Person> lastShownList) throws CommandException {
        assert targetIndex.isPresent() : "Target index should be present";
        assert lastShownList != null : "Last shown list should not be null";
        if (targetIndex.get().getZeroBased() >= lastShownList.size()) { // Checks if the index is greater than list size
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToDelete = lastShownList.get(targetIndex.get().getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    /**
     * Deletes the Contact by finding using name.
     * @param name Optional containing Name object.
     * @param model ModelManager storing the addressbook.
     * @param listForFilter FilteredList supplied with name predicate.
     * @return CommandResult that shows that a certain contact has been deleted.
     * @throws CommandException containing custom Exception messages.
     */
    private CommandResult deleteByName(Optional<Name> name, Model model, FilteredList<Person> listForFilter)
            throws CommandException {
        String nameString = name.get().fullName;
        FullNameMatchesPredicate fullNameMatchesPredicate = new FullNameMatchesPredicate(nameString);
        listForFilter.setPredicate(fullNameMatchesPredicate);
        if (listForFilter.isEmpty()) {
            throw new CommandException("No matching contacts found.");
        }
        if (listForFilter.size() > 1) {
            throw new CommandException("Multiple contacts with the same full name found. Please specify more using "
                    + "this format:\n" + COMMAND_WORD + " n/NAME e/EMAIL OR "
                    + COMMAND_WORD + " n/NAME p/PHONE");
        }
        Person personToDelete = listForFilter.get(0);
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    /**
     * Deletes the Contact by finding using name.
     * @param name Optional containing Name object.
     * @param phone Optional containing Phone object.
     * @param model ModelManager storing the addressbook.
     * @param listForFilter FilteredList supplied with Name and Email predicate.
     * @return CommandResult that shows that a certain contact has been deleted.
     * @throws CommandException containing custom Exception messages.
     */
    private CommandResult deleteByNamePhone(Optional<Name> name, Optional<Phone> phone, Model model,
                                            FilteredList<Person> listForFilter) throws CommandException {
        String nameString = name.get().fullName;
        String phoneString = phone.get().value;
        NamePhonePredicate namePhonePredicate = new NamePhonePredicate(nameString, phoneString);
        listForFilter.setPredicate(namePhonePredicate);
        if (listForFilter.isEmpty()) {
            throw new CommandException("No matching contacts found.");
        }
        Person personToDelete = listForFilter.get(0);
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    /**
     * Deletes the Contact by finding using name.
     * @param name Optional containing Name object.
     * @param email Optional containing Emil object.
     * @param model ModelManager storing the addressbook.
     * @param listForFilter FilteredList supplied with Name and Email predicate.
     * @return CommandResult that shows that a certain contact has been deleted.
     * @throws CommandException containing custom Exception messages.
     */
    private CommandResult deleteByNameEmail(Optional<Name> name, Optional<Email> email, Model model,
                                            FilteredList<Person> listForFilter) throws CommandException {
        String nameString = name.get().fullName;
        String emailString = email.get().value;
        NameEmailPredicate nameEmailPredicate = new NameEmailPredicate(nameString, emailString);
        listForFilter.setPredicate(nameEmailPredicate);
        if (listForFilter.isEmpty()) { // if no matching contact
            throw new CommandException("No matching contacts found.");
        }
        Person personToDelete = listForFilter.get(0);
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    /**
     * Deletes a person by their phone number only, as phone number is unique
     * @param phone Optional of Phone
     * @param model Optional of Model
     * @param listForFilter FilteredList supplied with Name and Email predicate.
     * @return CommandResult that shows that the person with unique phone number has been deleted.
     * @throws CommandException containing custom Exception messages.
     */
    private CommandResult deleteByPhone(Optional<Phone> phone, Model model, FilteredList<Person> listForFilter)
            throws CommandException {
        String phoneString = phone.get().value;
        PhonePredicate phonePredicate = new PhonePredicate(phoneString);
        listForFilter.setPredicate(phonePredicate);
        if (listForFilter.isEmpty()) {
            throw new CommandException("No matching contacts found.");
        }
        Person personToDelete = listForFilter.get(0);
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    /**
     * Deletes a person by their phone number only, as email is unique
     * @param email Optional of Email
     * @param model Optional of Model
     * @param listForFilter FilteredList supplied with Name and Email predicate.
     * @return CommandResult that shows that the person with unique email has been deleted.
     * @throws CommandException containing custom Exception messages.
     */
    private CommandResult deleteByEmail(Optional<Email> email, Model model, FilteredList<Person> listForFilter)
            throws CommandException {
        String emailString = email.get().value;
        EmailPredicate emailPredicate = new EmailPredicate(emailString);
        listForFilter.setPredicate(emailPredicate);
        if (listForFilter.isEmpty()) {
            throw new CommandException("No matching contacts found");
        }
        Person personToDelete = listForFilter.get(0);
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }
}
