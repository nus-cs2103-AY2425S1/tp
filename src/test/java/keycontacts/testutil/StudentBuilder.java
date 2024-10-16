package keycontacts.testutil;

import java.util.HashSet;
import java.util.Set;

import keycontacts.model.pianopiece.PianoPiece;
import keycontacts.model.student.Address;
import keycontacts.model.student.GradeLevel;
import keycontacts.model.student.Name;
import keycontacts.model.student.Phone;
import keycontacts.model.student.Student;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_GRADE_LEVEL = "ABRSM 3";

    private Name name;
    private Phone phone;
    private Address address;
    private GradeLevel gradeLevel;
    private Set<PianoPiece> pianoPieces;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        address = new Address(DEFAULT_ADDRESS);
        gradeLevel = new GradeLevel(DEFAULT_GRADE_LEVEL);
        pianoPieces = new HashSet<>();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        address = studentToCopy.getAddress();
        gradeLevel = studentToCopy.getGradeLevel();
        pianoPieces = new HashSet<>(studentToCopy.getPianoPieces());
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     */
    public StudentBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code GradeLevel} of the {@code Student} that we are building.
     * @param gradeLevel
     */
    public StudentBuilder withGradeLevel(String gradeLevel) {
        this.gradeLevel = new GradeLevel(gradeLevel);
        return this;
    }

    /**
     * Parses the {@code pianoPieces} into a {@code Set<PianoPiece>}
     * and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withPianoPieces(String ... pianoPieces) {
        this.pianoPieces = PianoPiece.getPianoPieceSet(pianoPieces);
        return this;
    }

    public Student build() {
        return new Student(name, phone, address, gradeLevel, pianoPieces, null);
    }

}
