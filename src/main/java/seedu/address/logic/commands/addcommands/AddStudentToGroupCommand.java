package seedu.address.logic.commands.addcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ListMarkers.LIST_GROUP_MARKER;
import static seedu.address.logic.Messages.MESSAGE_GROUP_NAME_NOT_FOUND;
import static seedu.address.logic.Messages.MESSAGE_STUDENT_NO_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.ExceedGroupSizeException;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.student.exceptions.StudentNotFoundException;

/**
 * Adds a student to a group.
 */
public class AddStudentToGroupCommand extends Command {

    public static final String COMMAND_WORD = "add_s_g";
    public static final String COMMAND_WORD_ALIAS = "asg";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Adds a student to a group identified by the group name used.\n"
        + "Parameters: "
        + "[" + PREFIX_STUDENT_NUMBER + "STUDENT_NUMBER]... "
        + PREFIX_GROUP_NAME + "GROUP_NAME\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_STUDENT_NUMBER + "A02345678J "
        + PREFIX_STUDENT_NUMBER + "A0456789K "
        + PREFIX_GROUP_NAME + "CS2103T-T14-1";

    public static final String MESSAGE_SUCCESS = "Added the following students to %1$s:\n%2$s";

    public static final String MESSAGE_DUPLICATE_STUDENT_IN_GROUP =
            "Duplicate student(s) entered, only 1 will be added:";
    public static final String MESSAGE_ALL_STUDENTS_EXIST_IN_GROUPS = "All student(s) entered belong in a group";
    public static final String MESSAGE_STUDENTS_IN_GROUP =
            "The following group(s) already exist and will not be added:";
    public static final String MESSAGE_EXCEED_GROUP_SIZE = "Group exceeded the maximum size!";

    private final List<StudentNumber> toAdd;

    private final GroupName toAddInto;

    /**
     * Creates an AddStudentToGroupCommand to add the specified {@code Student} to the specified {@code Group}.
     */
    public AddStudentToGroupCommand(List<StudentNumber> studentNumbers, GroupName groupName) {
        requireNonNull(studentNumbers);
        requireNonNull(groupName);
        toAdd = studentNumbers;
        toAddInto = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // gets group to add students into
        Group group = model.getGroupByName(toAddInto);
        if (group == null) {
            throw new CommandException(MESSAGE_GROUP_NAME_NOT_FOUND);
        }

        String duplicateMessage = "";
        String studentsInGroupMessage = MESSAGE_STUDENTS_IN_GROUP;

        StringBuilder studentsAdded = new StringBuilder();

        int studentsInGroupCount = 0;

        try {
            // Create a frequency map to track occurrences of each StudentNumber
            Map<StudentNumber, Long> frequencyMap = toAdd.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            // Create a stream of StudentNumbers that are duplicated and not in the model
            Stream<StudentNumber> checkForDuplicates = frequencyMap.entrySet().stream()
                    .filter(entry -> entry.getValue() > 1) // Keep only duplicates
                    .map(Map.Entry::getKey) // Extract StudentNumber
                    .filter(x -> model.hasPerson(model.getPersonByNumber(x))) // Check if not in model
                    .distinct();

            // Count the number of duplicated students entered
            long numDuplicates = frequencyMap.entrySet().stream()
                    .filter(entry -> entry.getValue() > 1) // Keep only duplicates
                    .map(Map.Entry::getKey) // Extract StudentNumber
                    .filter(x -> model.hasPerson(model.getPersonByNumber(x))) // Check if not in model
                    .distinct()
                    .count();

            if (numDuplicates > 0) {
                // create duplicate message for duplicate students, if none will just return an empty string
                duplicateMessage = checkForDuplicates.map(a -> a.getStudentNumber()).reduce(
                        MESSAGE_DUPLICATE_STUDENT_IN_GROUP, (x, y) -> x + "\n" + y);
            }

            // create a list of all the unique student numbers
            List<StudentNumber> uniqueStudentList = toAdd.stream().distinct().toList();

            for (StudentNumber studentNumber : uniqueStudentList) {
                Student student = model.getPersonByNumber(studentNumber);
                if (student.getGroupName().isPresent()) {
                    studentsInGroupCount++;
                    studentsInGroupMessage += "\n" + studentNumber.getStudentNumber();
                } else {
                    model.addPersonToGroup(student, group);
                    studentsAdded.append(studentNumber).append("\n");
                }
            }

            if (studentsInGroupCount == uniqueStudentList.size()) {
                throw new CommandException(MESSAGE_ALL_STUDENTS_EXIST_IN_GROUPS);
            }

            if (0 < studentsInGroupCount && studentsInGroupCount < uniqueStudentList.size()) {
                duplicateMessage = studentsInGroupMessage + "\n" + duplicateMessage;
            }

            if (numDuplicates > 0 || studentsInGroupCount > 0) {
                return new CommandResult(duplicateMessage
                        + "\n"
                        + String.format(MESSAGE_SUCCESS, Messages.format(toAddInto), studentsAdded), LIST_GROUP_MARKER);
            }

        } catch (StudentNotFoundException e) {
            throw new CommandException(MESSAGE_STUDENT_NO_NOT_FOUND);
        } catch (ExceedGroupSizeException e) {
            throw new CommandException(MESSAGE_EXCEED_GROUP_SIZE);
        }

        model.updateFilteredGroupList(x -> x.getGroupName().equals(toAddInto));
        model.setStateGroups();
        model.setMostRecentGroupDisplay(toAddInto.getGroupName());
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAddInto), studentsAdded),
            LIST_GROUP_MARKER);
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        versionHistory.addVersion(model);
        return versionHistory;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddStudentToGroupCommand)) {
            return false;
        }

        AddStudentToGroupCommand otherAddStudentToGroupCommand = (AddStudentToGroupCommand) other;
        return toAdd.equals(otherAddStudentToGroupCommand.toAdd)
            && toAddInto.equals(otherAddStudentToGroupCommand.toAddInto);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("toAdd", toAdd)
            .add("toAddInto", toAddInto)
            .toString();
    }
}
