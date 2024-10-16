package spleetwaise.address.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import spleetwaise.address.model.person.Person;

/**
 * A utility class that provides static assertion methods for testing purposes. This class includes methods for
 * asserting exceptions, validating person card displays, and matching person lists in the application.
 */
public class Assert {

    /**
     * Asserts that the given executable throws an exception of the expected type.
     *
     * @param expectedType The class of the expected exception.
     * @param executable   The executable that is expected to throw the exception.
     */
    public static void assertThrows(Class<? extends Throwable> expectedType, Executable executable) {
        Assertions.assertThrows(expectedType, executable);
    }

    /**
     * Asserts that the given executable throws an exception of the expected type with the expected message.
     *
     * @param expectedType    The class of the expected exception.
     * @param expectedMessage The expected message of the thrown exception.
     * @param executable      The executable that is expected to throw the exception.
     */
    public static void assertThrows(
            Class<? extends Throwable> expectedType, String expectedMessage,
            Executable executable
    ) {
        Throwable thrownException = Assertions.assertThrows(expectedType, executable);
        Assertions.assertEquals(expectedMessage, thrownException.getMessage());
    }

    /**
     * Asserts that the actual card displays the expected person's details correctly.
     *
     * @param expectedPerson The person whose details are expected to be displayed.
     * @param actualCard     The actual card handle that displays the person's details.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals(
                expectedPerson.getTags().stream().map(tag -> tag.tagName).sorted().collect(Collectors.toList()),
                actualCard.getTags()
        );
    }

    /**
     * Asserts that the person list panel displays the expected persons in the correct order.
     *
     * @param personListPanelHandle The handle for the person list panel.
     * @param persons               The expected persons to be displayed.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the person list panel displays the expected persons in the correct order.
     *
     * @param personListPanelHandle The handle for the person list panel.
     * @param persons               The expected persons to be displayed as a list.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }
}
