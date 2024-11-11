package keycontacts.model.student;

import static keycontacts.model.student.StudentComparator.MESSAGE_NAME_ASCENDING;
import static keycontacts.model.student.StudentComparator.MESSAGE_PHONE_DESCENDING;
import static keycontacts.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentComparatorTest {

    private Student student1;
    private Student student2;
    private Student student3;

    @BeforeEach
    public void setUp() {
        Name student1Name = new Name("Alice");
        Name student2Name = new Name("Bob");
        Name student3Name = new Name("Alice");

        Phone student1Phone = new Phone("12345678");
        Phone student2Phone = new Phone("87654321");
        Phone student3Phone = new Phone("12345678");

        Address student1Address = new Address("123 Main St");
        Address student2Address = new Address("456 Elm St");
        Address student3Address = new Address("123 Main St");

        GradeLevel student1GradeLevel = new GradeLevel("Trinity 1");
        GradeLevel student2GradeLevel = new GradeLevel("ABRSM 2");
        GradeLevel student3GradeLevel = new GradeLevel("Trinity 1");

        Group student1Group = new Group("Group1");
        Group student2Group = new Group("Group2");
        Group student3Group = new Group("Group1");

        student1 = new Student(student1Name, student1Phone, student1Address, student1GradeLevel, student1Group);
        student2 = new Student(student2Name, student2Phone, student2Address, student2GradeLevel, student2Group);
        student3 = new Student(student3Name, student3Phone, student3Address, student3GradeLevel, student3Group);
    }

    @Test
    public void testCompareByNameAscending() {
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(StudentComparator.getComparatorForName(new StudentComparator.SortOrder("ASC")));

        assertTrue(comparator.compare(student1, student2) < 0);
        assertTrue(comparator.compare(student2, student1) > 0);
        assertEquals(0, comparator.compare(student1, student3));
    }

    @Test
    public void testCompareByNameDescending() {
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(StudentComparator.getComparatorForName(new StudentComparator.SortOrder("DESC")));

        assertTrue(comparator.compare(student1, student2) > 0);
        assertTrue(comparator.compare(student2, student1) < 0);
        assertEquals(0, comparator.compare(student1, student3));
    }

    @Test
    public void testCompareByPhoneAscending() {
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(StudentComparator.getComparatorForPhone(new StudentComparator.SortOrder("ASC")));

        assertTrue(comparator.compare(student1, student2) < 0);
        assertTrue(comparator.compare(student2, student1) > 0);
        assertEquals(0, comparator.compare(student1, student3));
    }

    @Test
    public void testCompareByPhoneDescending() {
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(StudentComparator.getComparatorForPhone(new StudentComparator.SortOrder("DESC")));

        assertTrue(comparator.compare(student1, student2) > 0);
        assertTrue(comparator.compare(student2, student1) < 0);
        assertEquals(0, comparator.compare(student1, student3));
    }

    @Test
    public void testCompareByAddressAscending() {
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(StudentComparator.getComparatorForAddress(new StudentComparator.SortOrder("ASC")));

        assertTrue(comparator.compare(student1, student2) < 0);
        assertTrue(comparator.compare(student2, student1) > 0);
        assertEquals(0, comparator.compare(student1, student3));
    }

    @Test
    public void testCompareByAddressDescending() {
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(StudentComparator.getComparatorForAddress(new StudentComparator.SortOrder("DESC")));

        assertTrue(comparator.compare(student1, student2) > 0);
        assertTrue(comparator.compare(student2, student1) < 0);
        assertEquals(0, comparator.compare(student1, student3));
    }

    @Test
    public void testCompareByGradeLevelAscending() {
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(StudentComparator.getComparatorForGradeLevel(new StudentComparator.SortOrder("ASC")));

        assertTrue(comparator.compare(student1, student2) > 0);
        assertTrue(comparator.compare(student2, student1) < 0);
        assertEquals(0, comparator.compare(student1, student3));
    }

    @Test
    public void testCompareByGradeLevelDescending() {
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(StudentComparator.getComparatorForGradeLevel(new StudentComparator.SortOrder("DESC")));

        assertTrue(comparator.compare(student1, student2) < 0);
        assertTrue(comparator.compare(student2, student1) > 0);
        assertEquals(0, comparator.compare(student1, student3));
    }

    @Test
    public void testCompareByGroupAscending() {
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(StudentComparator.getComparatorForGroup(new StudentComparator.SortOrder("ASC")));

        assertTrue(comparator.compare(student1, student2) < 0);
        assertTrue(comparator.compare(student2, student1) > 0);
        assertEquals(0, comparator.compare(student1, student3));
    }

    @Test
    public void testCompareByGroupDescending() {
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(StudentComparator.getComparatorForGroup(new StudentComparator.SortOrder("DESC")));

        assertTrue(comparator.compare(student1, student2) > 0);
        assertTrue(comparator.compare(student2, student1) < 0);
        assertEquals(0, comparator.compare(student1, student3));
    }



    @Test
    public void testGetSortDescription() {
        StudentComparator comparator = new StudentComparator();
        comparator.addComparator(StudentComparator.getComparatorForName(new StudentComparator.SortOrder("ASC")));
        comparator.addComparator(StudentComparator.getComparatorForPhone(new StudentComparator.SortOrder("DESC")));

        assertEquals(String.join(", ", MESSAGE_NAME_ASCENDING, MESSAGE_PHONE_DESCENDING),
                comparator.getSortDescription());
    }

    @Test
    public void sortOrder_invalidSortOrder_throwsException() {
        assertThrows(IllegalArgumentException.class, StudentComparator.SortOrder.MESSAGE_INVALID_SORT_ORDER, ()
                -> new StudentComparator.SortOrder("invalid sort order"));
    }
}
