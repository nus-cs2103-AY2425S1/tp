package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.note.Note;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Sex;
import seedu.address.model.person.StarredStatus;
import seedu.address.model.tag.Tag;

/**
 * Stars contact to add it into favourites list.
 */
public class StarCommand extends Command {
    public static final String COMMAND_WORD = "star";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Stars the person identified by the name or index in the address book.\n"
            + "Parameters: NAME or INDEX (must match exactly one person or be a valid index)\n"
            + "Example: " + COMMAND_WORD + " John Doe\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_STAR_PERSON_SUCCESS = "Starred Person: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "The person's name provided is invalid";
    public static final String MESSAGE_INVALID_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_ALREADY_STARRED = "%1$s has already been starred as favourite";

    private final Name targetName;
    private final Index targetIndex;

    /**
     * @param targetName of the person to be starred in the list
     */
    public StarCommand(Name targetName) {
        this.targetName = targetName;
        this.targetIndex = null;
    }

    /**
     * @param targetIndex of the index of the person to be starred in the list
     */
    public StarCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToStar;

        if (targetName != null) {
            Optional<Person> personOptional = lastShownList.stream()
                    .filter(person -> person.getName().equals(targetName))
                    .findFirst();

            if (personOptional.isEmpty()) {
                throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
            }
            personToStar = personOptional.get();
        } else {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEX);
            }
            personToStar = lastShownList.get(targetIndex.getZeroBased());
        }

        if (personToStar.getStarredStatus().equals(new StarredStatus("true"))) {
            throw new CommandException(String.format(MESSAGE_ALREADY_STARRED, personToStar.getName()));
        } else {
            Person editedPerson = createEditedPerson(personToStar);
            model.setPerson(personToStar, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }

        return new CommandResult(String.format(MESSAGE_STAR_PERSON_SUCCESS, personToStar.getName()));
    }

    /**
     * Creates and returns a {@code Person} with the same details but starredStatus as true.
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Age age = personToEdit.getAge();
        Sex sex = personToEdit.getSex();
        Set<Appointment> appointment = new HashSet<>(personToEdit.getAppointment());
        Set<Tag> tags = new HashSet<>(personToEdit.getTags());
        Note note = personToEdit.getNote();
        StarredStatus starredStatus = new StarredStatus("true");

        return new Person(name, phone, email, address,
                age, sex, appointment, tags, note, starredStatus);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StarCommand)) {
            return false;
        }

        StarCommand otherStarCommand = (StarCommand) other;


        return (targetName != null && targetName.equals(otherStarCommand.targetName))
                || (targetIndex != null && targetIndex.equals(otherStarCommand.targetIndex));
    }

    @Override
    public String toString() {
        if (targetName != null) {
            return String.format("StarCommand[targetName=%s]", targetName);
        } else {
            return String.format("StarCommand[targetIndex=%d]", targetIndex.getOneBased());
        }
    }
}
