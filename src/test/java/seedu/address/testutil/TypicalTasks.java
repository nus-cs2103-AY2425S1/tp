package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.task.Task;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task BUY_MEDICATION = new TaskBuilder().withDescription("Buy medication")
            .withPatient(TypicalPersons.ALICE).build();
    public static final Task VISIT_DOCTOR = new TaskBuilder().withDescription("Visit doctor")
            .withPatient(TypicalPersons.ALICE).build();
    public static final Task FILL_PRESCRIPTION = new TaskBuilder().withDescription("Fill prescription")
            .withPatient(TypicalPersons.CARL).build();
    public static final Task CALL_FAMILY = new TaskBuilder().withDescription("Call family")
            .withPatient(TypicalPersons.DANIEL).build();
    public static final Task CHECK_VITALS = new TaskBuilder().withDescription("Check vitals")
            .withPatient(TypicalPersons.ELLE).build();
    public static final Task FOLLOW_UP_APPOINTMENT = new TaskBuilder().withDescription("Follow-up appointment")
            .withPatient(TypicalPersons.FIONA).build();
    public static final Task REMIND_PATIENT = new TaskBuilder().withDescription("Remind patient of medication")
            .withPatient(TypicalPersons.GEORGE).build();

    // Manually added tasks
    public static final Task CONSULTATION = new TaskBuilder().withDescription("Consultation with specialist")
            .withPatient(TypicalPersons.HOON).build();
    public static final Task MEDICAL_REPORT = new TaskBuilder().withDescription("Submit medical report")
            .withPatient(TypicalPersons.IDA).build();

    private TypicalTasks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical tasks.
     */
    public static AddressBook getTypicalAddressBookWithTasks() {
        AddressBook ab = TypicalPersons.getTypicalAddressBook();
        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }
        return ab;
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(BUY_MEDICATION, VISIT_DOCTOR, FILL_PRESCRIPTION, CALL_FAMILY,
                CHECK_VITALS, FOLLOW_UP_APPOINTMENT, REMIND_PATIENT));
    }
}
