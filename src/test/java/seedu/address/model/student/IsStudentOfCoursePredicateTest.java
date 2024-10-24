package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StudentBuilder;

public class IsStudentOfCoursePredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        IsStudentOfCoursePredicate firstPredicate = new IsStudentOfCoursePredicate(firstPredicateKeywordList);
        IsStudentOfCoursePredicate secondPredicate = new IsStudentOfCoursePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        IsStudentOfCoursePredicate firstPredicateCopy = new IsStudentOfCoursePredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different student -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_studentTakingCourse_returnsTrue() {
        // One keyword
        IsStudentOfCoursePredicate predicate = new IsStudentOfCoursePredicate(Collections.singletonList("CS2103T"));
        assertTrue(predicate.test(new StudentBuilder().withCourses("CS2103T").build()));

        // Only one matching course
        predicate = new IsStudentOfCoursePredicate(Arrays.asList("CS2100", "CS2101", "CS2102"));
        assertTrue(predicate.test(new StudentBuilder().withCourses("MA1100", "MA2101", "CS2102").build()));

        // Mixed-case course
        predicate = new IsStudentOfCoursePredicate(Arrays.asList("cS2103t"));
        assertTrue(predicate.test(new StudentBuilder().withCourses("CS2103T").build()));
    }

    @Test
    public void test_notTakingCourse_returnsFalse() {
        // Zero courses to search for
        IsStudentOfCoursePredicate predicate = new IsStudentOfCoursePredicate(Collections.emptyList());
        assertFalse(predicate.test(new StudentBuilder().withCourses("CS2103T").build()));

        // Zero courses taken by student
        predicate = new IsStudentOfCoursePredicate(Collections.emptyList());
        assertFalse(predicate.test(new StudentBuilder().withCourses().build()));

        // Non-matching course that contains the given course as a substring
        predicate = new IsStudentOfCoursePredicate(Arrays.asList("CS2103"));
        assertFalse(predicate.test(new StudentBuilder().withCourses("CS2103T").build()));

        // Keywords match phone, email and address, but does not match course
        predicate = new IsStudentOfCoursePredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new StudentBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> courses = List.of("CS2101", "CS2106");
        IsStudentOfCoursePredicate predicate = new IsStudentOfCoursePredicate(courses);

        String expected = IsStudentOfCoursePredicate.class.getCanonicalName() + "{courses=" + courses + "}";
        assertEquals(expected, predicate.toString());
    }
}
