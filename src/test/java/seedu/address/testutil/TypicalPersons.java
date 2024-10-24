package seedu.address.testutil;

import static seedu.address.commons.util.DateUtil.DATE_TIME_FORMATTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WARD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WARD_BOB;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withId("P12345").withWard("A1").withDiagnosis("Celiac Disease")
            .withMedication("gluten-free diet").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withId("P54321").withWard("A2").withDiagnosis("Celiac Disease")
            .withMedication("gluten-free diet").withNotes("some notes").withAppointment("Checkup",
                    LocalDateTime.parse("23-10-2024-16-00", DATE_TIME_FORMATTER),
                    LocalDateTime.parse("24-10-2024-16-00", DATE_TIME_FORMATTER)).build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withId("P73443").withWard("B3").withDiagnosis("Celiac Disease")
            .withMedication("gluten-free diet").withAppointment("Surgery",
                    LocalDateTime.parse("23-10-2024-12-00", DATE_TIME_FORMATTER),
                    LocalDateTime.parse("23-10-2024-14-00", DATE_TIME_FORMATTER)).build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withId("P54786").withWard("C8").withDiagnosis("Celiac Disease")
            .withMedication("gluten-free diet").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withId("P27346").withWard("I5").withDiagnosis("Celiac Disease")
            .withMedication("gluten-free diet").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withId("P07962").withWard("A1").withDiagnosis("Celiac Disease")
            .withMedication("gluten-free diet")
            .withAppointment("Consultation",
                    LocalDateTime.parse("23-10-2024-10-00", DATE_TIME_FORMATTER),
                    LocalDateTime.parse("23-10-2024-12-00", DATE_TIME_FORMATTER))
            .build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withId("P18277").withWard("B1").withDiagnosis("Celiac Disease")
            .withMedication("gluten-free diet")
            .withAppointment("Surgery",
                    LocalDateTime.parse("23-10-2024-12-00", DATE_TIME_FORMATTER),
                    LocalDateTime.parse("23-10-2024-14-00", DATE_TIME_FORMATTER))
            .build();

    //Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withId("P98450").withWard("A1")
            .withDiagnosis("Celiac Disease").withMedication("gluten-free diet")
            .build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withId("P28349").withWard("B1")
            .withDiagnosis("Celiac Disease").withMedication("gluten-free diet").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withId(VALID_ID_AMY)
            .withWard(VALID_WARD_AMY).withDiagnosis("Celiac Disease").withMedication("gluten-free diet").build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withId(VALID_ID_BOB)
            .withWard(VALID_WARD_BOB).withDiagnosis("Chronic Sinusitis").withMedication("gluten-free diet").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
