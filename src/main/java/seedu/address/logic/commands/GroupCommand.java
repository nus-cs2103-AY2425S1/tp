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

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Not implemented";
    public static final String MESSAGE_ARGUMENTS = "Group Name: %s, Students: %s";
    private String groupName;
    private List<String> students;

    /**
     * Creates a GroupCommand to group the specified students under the given group
     * name.
     *
     * @param groupName The name of the group.
     * @param students  The list of student names to be grouped.
     * @throws NullPointerException if {@code groupName} or {@code students} is
     *                              null.
     */
    public GroupCommand(String groupName, List<String> students) {
        requireAllNonNull(groupName, students);
        this.groupName = groupName;
        this.students = students;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Get the persons that have those names
        List<Person> allPersons = model.getFilteredPersonList();
        List<Person> groupMembers = allPersons.stream()
                .filter(person -> students.contains(person.getName().fullName))
                .toList();

        if (groupMembers.isEmpty()) {
            throw new CommandException("No matching students found.");
        }
        // Append to a group object
        Group group = new Group(groupName, groupMembers);

        // Add the group object to the model
        model.addGroup(group);

        return new CommandResult(String.format("Group %s created with %d students", groupName, groupMembers.size()));
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
