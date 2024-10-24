package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;
import static keycontacts.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static keycontacts.logic.parser.CliSyntax.PREFIX_GRADE_LEVEL;
import static keycontacts.logic.parser.CliSyntax.PREFIX_NAME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_PHONE;

import keycontacts.commons.util.ToStringBuilder;
import keycontacts.logic.Messages;
import keycontacts.model.Model;
import keycontacts.model.student.Student;
import keycontacts.model.student.StudentDescriptorMatchesPredicate;

/**
 * Finds and lists all students in the student directory whose name contains any
 * of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: \n"
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_GRADE_LEVEL + "GRADE_LEVEL]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Alice " + PREFIX_PHONE + "91234567";

    private final StudentDescriptorMatchesPredicate predicate;

    public FindCommand(StudentDescriptorMatchesPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.filterStudentList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getStudentList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

    /**
     * Stores the details to find the student with. Each non-empty field value will
     * be used to match against the
     * corresponding field of the student. The empty field value will not be used to
     * match against the corresponding
     * field of the student.
     */
    public static class FindStudentDescriptor {
        private String name;
        private String address;
        private String phone;
        private String gradeLevel;

        /**
         * Default constructor that initializes all value to emtpy string
         */
        public FindStudentDescriptor() {
            this.name = "";
            this.address = "";
            this.phone = "";
            this.gradeLevel = "";
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public FindStudentDescriptor(String name, String address, String phone, String gradeLevel) {
            setName(name);
            setAddress(address);
            setPhone(phone);
            setGradeLevel(gradeLevel);
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public FindStudentDescriptor(FindStudentDescriptor toCopy) {
            setName(toCopy.name);
            setAddress(toCopy.address);
            setPhone(toCopy.phone);
            setGradeLevel(toCopy.gradeLevel);
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setGradeLevel(String gradeLevel) {
            this.gradeLevel = gradeLevel;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getPhone() {
            return phone;
        }

        public String getGradeLevel() {
            return gradeLevel;
        }

        /**
         * Returns true if given student's field contains the non-empty
         * field value of this descriptor.
         * @param student
         * @return
         */
        public boolean matches(Student student) {
            boolean matches = true;
            if (!name.isEmpty()) {
                matches = matches && student.getName().fullName.toLowerCase().contains(name.toLowerCase());
            }
            if (!address.isEmpty()) {
                matches = matches && student.getAddress().value.toLowerCase().contains(address.toLowerCase());
            }
            if (!phone.isEmpty()) {
                matches = matches && student.getPhone().value.toLowerCase().contains(phone.toLowerCase());
            }
            if (!gradeLevel.isEmpty()) {
                matches = matches && student.getGradeLevel().value.toLowerCase().contains(gradeLevel.toLowerCase());
            }
            return matches;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true; // short circuit if same object
            }

            if (!(other instanceof FindStudentDescriptor)) {
                return false; // instanceof handles nulls
            }

            FindStudentDescriptor otherDescriptor = (FindStudentDescriptor) other;
            boolean result = name.equals(otherDescriptor.name)
                    && address.equals(otherDescriptor.address)
                    && phone.equals(otherDescriptor.phone)
                    && gradeLevel.equals(otherDescriptor.gradeLevel); // state check
            return result;
        }
    }
}
