package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Groups students together in the application.
 */
public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new group with the specified students.\n"
            + "Groups the students by the group name and the list of students provided.\n"
            + "Existing groups with the same name will not be overwritten.\n"
            + "Parameters: GROUPNAME (alphanumeric) "
            + "STUDENT1 STUDENT2... (must be valid student names)\n"
            + "Example: " + COMMAND_WORD + " g/StudyGroup1 s/Benjamin s/Candice\n"
            + "Example: " + COMMAND_WORD + " g/TeamA s/Martin s/Candice";

    public static final String MESSAGE_NO_STUDENTS_FOUND = "No matching students found.";

    public static final String MESSAGE_SUCCESS = "Group %s created with %d students";

    public static final String MESSAGE_DUPLICATE_GROUP = "Group name already taken!!";
    private final String groupName;
    private final List<String> students;

    /**
     * Creates a GroupCommand to group the specified students under the given group
     * name.
     *
     * @param groupName The name of the group.
     * @param students  The list of students to be grouped.
     * @throws NullPointerException if {@code groupName} or {@code students} is
     *                              null.
     */
    public GroupCommand(String groupName, List<String> students) throws NullPointerException {
        requireAllNonNull(groupName, students);
        this.groupName = groupName;
        this.students = students;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (model.hasGroupName(new Group(groupName, List.of()))) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        List<Person> allPersons = model.getFilteredPersonList();
        List<Person> groupMembers = allPersons.stream()
                .filter(person -> students.contains(person
                        .getName().fullName))
                .toList();

        if (groupMembers.isEmpty()) {
            throw new CommandException(MESSAGE_NO_STUDENTS_FOUND);
        }
        Group group = new Group(groupName, groupMembers);

        model.addGroup(group);

        return new CommandResult(String.format(MESSAGE_SUCCESS, groupName, groupMembers.size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupCommand e)) {
            return false;
        }

        return groupName.equals(e.groupName)
                && students.equals(e.students);
    }
}
