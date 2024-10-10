package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;

/**
 * Tags an existing student in the address book with a subject, school level or both.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tags students with a given subject, level or both. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_SUBJECT + "SUBJECT]"
            + PREFIX_LEVEL + "LEVEL...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_SUBJECT + "MATH "
            + PREFIX_LEVEL + "JC1";

    public static final String MESSAGE_TAG_STUDENT_SUCCESS = "Tagged Student: %1$s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student does not exist in address book.";


    private final Name nameToTag;
    private final EditCommand.EditPersonDescriptor tagsToAdd;

    /**
     * @param nameToTag name of the person in the address book to tag
     * @param tagsToAdd details to tag the person with
     */
    public TagCommand(Name nameToTag, EditCommand.EditPersonDescriptor tagsToAdd) {
        this.nameToTag = nameToTag;
        this.tagsToAdd = tagsToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        Optional<Person> optionalPersonToTag = lastShownList.stream()
                .filter(x -> x.getName()
                        .equals(nameToTag)).findFirst();


        Person personToTag;
        if (optionalPersonToTag.isPresent()) {
            personToTag = optionalPersonToTag.get();
        } else {
            throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
        }

        Person personWithTags = createPersonWithTags(personToTag, tagsToAdd);

        model.setPerson(personToTag, personWithTags);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_TAG_STUDENT_SUCCESS, Messages.format(personWithTags)));

    }

    private static Person createPersonWithTags(Person personToTag, EditCommand.EditPersonDescriptor tagsToAdd) {
        assert personToTag != null;

        Name updatedName = tagsToAdd.getName().orElse(personToTag.getName());
        Phone updatedPhone = tagsToAdd.getPhone().orElse(personToTag.getPhone());
        EmergencyContact updatedEmergencyContact = tagsToAdd.getEmergencyContact()
                .orElse(personToTag.getEmergencyContact());
        Address updatedAddress = tagsToAdd.getAddress().orElse(personToTag.getAddress());
        Note updatedNote = tagsToAdd.getNote().orElse(personToTag.getNote());
        Set<Subject> updatedSubjects = tagsToAdd.getSubjects().orElse(personToTag.getSubjects());
        Level schoolLevel = tagsToAdd.getSchoolLevel().orElse(personToTag.getSchoolLevel());
        return new Person(updatedName, updatedPhone, updatedEmergencyContact,
                updatedAddress, updatedNote, updatedSubjects, schoolLevel);
    }


}
