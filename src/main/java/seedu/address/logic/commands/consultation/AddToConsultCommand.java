package seedu.address.logic.commands.consultation;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CONSULTATION_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;

/**
 * Adds students to a specific consultation.
 */
public class AddToConsultCommand extends Command {

    public static final String COMMAND_WORD = "addtoconsult";
    public static final CommandType COMMAND_TYPE = CommandType.CONSULT;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds students to the consultation identified "
            + "by the index number used in the displayed consultation list. "
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME]… [" + PREFIX_INDEX + "INDEX]…\n"
            + "Example: " + COMMAND_WORD + " 1 n/John Doe n/Harry Ng";

    public static final String MESSAGE_ADD_TO_CONSULT_SUCCESS = "Added students to the Consultation: %1$s";

    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student(s) %s not found";
    public static final String MESSAGE_DUPLICATE_STUDENT_IN_CONSULTATION_BY_NAME =
            "%s is already added to the consultation!";
    public static final String MESSAGE_DUPLICATE_STUDENT_IN_CONSULTATION_BY_INDEX =
            "%s at index %d is already added to the consultation!";
    private final Logger logger = LogsCenter.getLogger(AddToConsultCommand.class);



    private final Index index;
    private final List<Name> studentNames;
    private final List<Index> indices;

    /**
     * @param index of the consultation in the filtered consultation list
     * @param studentNames list of student names to add to the consultation
     * @param indices list of indices of students in the current filtered list to add to the current consultation
     */
    public AddToConsultCommand(Index index, List<Name> studentNames, List<Index> indices) {
        requireNonNull(index);
        requireNonNull(studentNames);
        requireNonNull(indices);


        this.index = index;
        this.studentNames = studentNames;
        this.indices = indices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Consultation> lastShownList = model.getFilteredConsultationList();
        List<Student> lastShownStudentList = model.getFilteredStudentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_CONSULTATION_DISPLAYED_INDEX,
                index.getOneBased()));
        }

        Consultation targetConsultation = lastShownList.get(index.getZeroBased());
        Consultation editedConsultation = new Consultation(targetConsultation);

        Set<Student> studentsToAddSet = new HashSet<>();
        ArrayList<Student> studentsToAddArr = new ArrayList<>();

        for (Name studentName : studentNames) {
            Student student = model.findStudentByName(studentName)
                    .orElseThrow(() -> new CommandException(
                            String.format(MESSAGE_STUDENT_NOT_FOUND, studentName)));

            // if student was already in the lesson before this command, throw error
            if (editedConsultation.hasStudent(student)) {
                logger.warning("Students were not added to consult " + targetConsultation.toString()
                    + " because the student " + studentName + " was already in consult");
                throw new CommandException(
                        String.format(MESSAGE_DUPLICATE_STUDENT_IN_CONSULTATION_BY_NAME, studentName));
            }

            //if student was not previously in the lesson before this command, add it to the set
            if (!studentsToAddSet.contains(student)) {
                studentsToAddArr.add(student);
            }
            studentsToAddSet.add(student);
        }

        // check if any of the indices are out of bounds of the filtered student list
        boolean throwException = false;
        Set<Index> outOfBounds = new HashSet<>();

        for (Index item: indices) {
            if (item.getZeroBased() >= lastShownStudentList.size()) {
                throwException = true;
                outOfBounds.add(item);
            }
        }

        if (throwException) {

            String formattedOutOfBoundIndices = outOfBounds.stream()
                    .map(index -> String.valueOf(index.getOneBased()))
                    .collect(Collectors.joining(", "));
            logger.warning("Students were not added to consult " + targetConsultation.toString()
                    + " because indices " + formattedOutOfBoundIndices + " were out of bounds");
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN, formattedOutOfBoundIndices));
        }

        // Make sure that there are no duplicate students. Since logic handling duplicate students is done here for
        // adding students by name, logic for handling duplicate indices are also handled here
        for (Index studentIndex : indices) {
            Student student = lastShownStudentList.get(studentIndex.getZeroBased());

            if (editedConsultation.hasStudent(student)) {
                logger.warning("Students were not added to lesson " + targetConsultation.toString()
                    + " because student " + student.getName() + " was already in the consult");
                throw new CommandException(
                        String.format(MESSAGE_DUPLICATE_STUDENT_IN_CONSULTATION_BY_INDEX, student.getName(),
                                studentIndex.getOneBased()));
            }

            if (!studentsToAddSet.contains(student)) {
                studentsToAddArr.add(student);
            }
            studentsToAddSet.add(student);
        }

        // actually adding the students into the lesson
        for (Student student: studentsToAddArr) {
            editedConsultation.addStudent(student);
        }
        model.setConsult(targetConsultation, editedConsultation);

        return new CommandResult(
            String.format(MESSAGE_ADD_TO_CONSULT_SUCCESS, Messages.format(editedConsultation)),
            COMMAND_TYPE);
    }

    /**
     * Returns Command Type ADDTOCONSULT
     *
     * @return Command Type ADDTOCONSULT
     */
    @Override
    public CommandType getCommandType() {
        return COMMAND_TYPE;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddToConsultCommand)) {
            return false;
        }

        AddToConsultCommand otherCommand = (AddToConsultCommand) other;
        return index.equals(otherCommand.index)
                && studentNames.equals(otherCommand.studentNames);
    }
}
