package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.person.AttendanceStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutorial;

/**
 * Sorts all persons in the address book by a specified field and order.
 */
public class SortCommand extends Command {

    /**
     * Enum representing the fields available for sorting.
     */
    public enum SortField {
        NAME("Name"),
        STUDENT_ID("Student ID"),
        TUTORIAL_ATTENDANCE("Tutorial Attendance");

        private final String displayName;

        SortField(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_WRONG_NUM_OF_FIELDS = "Exactly 1 field should be provided for sorting.";
    public static final String MESSAGE_NOT_SORTED = "The field to sort by must be provided.";
    public static final Integer ASCENDING = 1;
    public static final Integer DESCENDING = -1;

    public static final Comparator<Person> COMPARE_BY_NAME =
            Comparator.comparing(person -> person.getName().toString());
    public static final Comparator<Person> COMPARE_BY_ID =
            Comparator.comparing(person -> person.getStudentId().toString());

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts contacts according to 1 specified field and order. \n"
            + "Parameters: "
            + "ORDER (must be 1 (ascending) or -1 (descending)) "
            + "[" + PREFIX_NAME + "] "
            + "[" + PREFIX_STUDENT_ID + "] "
            + "[" + PREFIX_TUTORIAL + "TUTORIAL] \n"
            + "Examples: " + COMMAND_WORD + " 1 " + PREFIX_NAME + "\n"
            + COMMAND_WORD + " -1 " + PREFIX_TUTORIAL + "4";

    private final Comparator<Person> sortingComparator;
    private final boolean isAscendingOrder;
    private final SortField sortField;

    /**
     * Constructs a {@code SortCommand} with the given comparator and order.
     *
     * @param personComparator Comparator to sort persons.
     * @param isAscendingOrder {@code true} for ascending, {@code false} for descending.
     * @throws NullPointerException if {@code personComparator} is null.
     */
    private SortCommand(Comparator<Person> personComparator, boolean isAscendingOrder, SortField field) {
        requireNonNull(personComparator);
        this.sortingComparator = personComparator;
        this.isAscendingOrder = isAscendingOrder;
        this.sortField = field;
    }

    /**
     * Returns a {@code SortCommand} to sort by name.
     *
     * @param order 1 for ascending, -1 for descending.
     */
    public static SortCommand sortByName(Integer order) {
        requireNonNull(order);
        boolean isAscendingOrder = (order.equals(ASCENDING));
        Comparator<Person> nameComparator = isAscendingOrder ? COMPARE_BY_NAME : COMPARE_BY_NAME.reversed();
        return new SortCommand(nameComparator, isAscendingOrder, SortField.NAME);
    }

    /**
     * Returns a {@code SortCommand} to sort by student ID.
     *
     * @param order 1 for ascending, -1 for descending.
     */
    public static SortCommand sortByStudentId(Integer order) {
        requireNonNull(order);
        boolean isAscendingOrder = (order.equals(ASCENDING));
        Comparator<Person> studentIdComparator = isAscendingOrder ? COMPARE_BY_ID : COMPARE_BY_ID.reversed();
        return new SortCommand(studentIdComparator, isAscendingOrder, SortField.STUDENT_ID);
    }

    /**
     * Returns a {@code SortCommand} to sort by tutorial attendance.
     *
     * @param order 1 for ascending, -1 for descending.
     * @param tutorial The tutorial for which attendance is sorted.
     */
    public static SortCommand sortByTutorialAttendance(Integer order, Tutorial tutorial) {
        requireNonNull(order);
        boolean isAscendingOrder = (order.equals(ASCENDING));
        return new SortCommand(
                new TutorialComparator(tutorial, isAscendingOrder), isAscendingOrder, SortField.TUTORIAL_ATTENDANCE);
    }

    /**
     * Returns a success message indicating the sorting field and order.
     *
     * @return A message describing the sorting field and order, or an empty string if unknown.
     */
    public String getMessageSuccess() {
        String order = isAscendingOrder ? "Ascending" : "Descending";
        // Ensure sortField is not null
        assert sortField != null : "sortField should never be null";

        return String.format("Sorted by %s in %s order.", sortField, order);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateSortedPersonList(sortingComparator);
        return new CommandResult(getMessageSuccess());
    }

    @Override
    public String toString() {
        String order = isAscendingOrder ? "Ascending" : "Descending";
        return String.format("SortCommand[field=%s, order=%s]", sortField, order);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        // state check
        SortCommand e = (SortCommand) other;
        return this.sortingComparator.equals(e.sortingComparator);
    }

    /**
     * Comparator class for comparing persons based on their tutorial attendance.
     */
    public static class TutorialComparator implements Comparator<Person> {
        private final Tutorial tutorial;
        private final boolean isAscendingOrder;

        /**
         * Constructs a {@code TutorialComparator} for the specified tutorial and order.
         *
         * @param tutorial The tutorial to compare attendance for.
         * @param isAscendingOrder {@code true} for ascending order, {@code false} for descending.
         */
        public TutorialComparator(Tutorial tutorial, boolean isAscendingOrder) {
            this.tutorial = tutorial;
            this.isAscendingOrder = isAscendingOrder;
        }

        @Override
        public int compare(Person p1, Person p2) {
            return AttendanceStatus.getComparator(isAscendingOrder)
                    .compare(p1.getAttendanceStatus(tutorial), p2.getAttendanceStatus(tutorial));
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof TutorialComparator)) {
                return false;
            }
            TutorialComparator t = (TutorialComparator) other;
            return tutorial.equals(t.tutorial) && isAscendingOrder == t.isAscendingOrder;
        }
    }
}
