package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.AttendanceCount;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class UnmarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendence of person by index number";

    public static final String MESSAGE_MARK_PERSON_SUCCESS = "Marked attendence for: %1$s";
    private final Index targetIndex;

    public UnmarkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnmark = lastShownList.get(targetIndex.getZeroBased());
        Person unmarkedPerson = createNewPerson(personToUnmark);


        model.setPerson(personToUnmark, unmarkedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_MARK_PERSON_SUCCESS, Messages.format(unmarkedPerson)));
    }

    private Person createNewPerson (Person selectedPerson) {
        assert selectedPerson != null;
        Name name = selectedPerson.getName();
        Role role = selectedPerson.getRole();
        Phone phone = selectedPerson.getPhone();
        Email email = selectedPerson.getEmail();
        Address address = selectedPerson.getAddress();
        Set<Tag> tags = selectedPerson.getTags();
        AttendanceCount attendenceCount = selectedPerson.getAttendanceCount().decrement();
        return new Person(name, role, phone, email, address, tags, attendenceCount);

    }

}
