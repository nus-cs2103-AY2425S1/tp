package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class ResetAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "reset-att";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Resets all attandence to 0.";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Attendance set to 0 for all students";



    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        for (Person p : lastShownList) {
            Person editedPerson = createNewPerson(p);
            model.setPerson(p, editedPerson);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createNewPerson(Person personToEdit) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Role role = personToEdit.getRole();
        Phone phone= personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Set<Tag> tags = personToEdit.getTags();
        return new Person(name, role, phone, email, address, tags);
    }


}