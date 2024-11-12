package seedu.address.model.participation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.participation.exceptions.DuplicateParticipationException;
import seedu.address.model.participation.exceptions.ParticipationNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.testutil.ParticipationBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TutorialBuilder;

public class UniqueParticipationListTest {
    private Person testStudent;
    private Tutorial testTutorial;
    private final UniqueParticipationList uniqueParticipationList = new UniqueParticipationList();
    @BeforeEach
    public void setUp() {

        // Create test data for a student and a tutorial
        testStudent = new PersonBuilder().withName("Alice").build();
        testTutorial = new TutorialBuilder().withSubject("physics").build();
    }

    @Test
    public void contains_nullParticipation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueParticipationList.contains(null));
    }

    @Test
    public void contains_participationNotInList_returnsFalse() {
        Participation participation = new ParticipationBuilder().build();
        assertFalse(uniqueParticipationList.contains(participation));
    }

    @Test
    public void contains_participationInList_returnsTrue() {
        Participation participation = new ParticipationBuilder().build();
        uniqueParticipationList.add(participation);
        assertTrue(uniqueParticipationList.contains(participation));
    }

    @Test
    public void contains_participationWithSameIdentityFieldsInList_returnsTrue() {
        Participation participation = new ParticipationBuilder().build();
        uniqueParticipationList.add(participation);
        Participation editedParticipation = new ParticipationBuilder().withStudent(testStudent).build();
        assertFalse(uniqueParticipationList.contains(editedParticipation));
    }

    @Test
    public void add_nullParticipation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueParticipationList.add(null));
    }

    @Test
    public void add_duplicateParticipation_throwsDuplicateParticipationException() {
        Participation participation = new ParticipationBuilder().build();
        uniqueParticipationList.add(participation);
        assertThrows(DuplicateParticipationException.class, () -> uniqueParticipationList.add(participation));
    }

    @Test
    public void setParticipation_nullTargetParticipation_throwsNullPointerException() {
        Participation editedParticipation = new ParticipationBuilder().build();
        assertThrows(NullPointerException.class, (
        ) -> uniqueParticipationList.setParticipation(null, editedParticipation));
    }

    @Test
    public void setParticipation_nullEditedParticipation_throwsNullPointerException() {
        Participation participation = new ParticipationBuilder().build();
        uniqueParticipationList.add(participation);
        assertThrows(NullPointerException.class, (
        ) -> uniqueParticipationList.setParticipation(participation, null));
    }

    @Test
    public void setParticipation_targetParticipationNotInList_throwsParticipationNotFoundException() {
        Participation participation = new ParticipationBuilder().build();
        assertThrows(ParticipationNotFoundException.class, (
        ) -> uniqueParticipationList.setParticipation(participation, participation));
    }

    @Test
    public void setParticipation_editedParticipationIsSameParticipation_success() {
        Participation participation = new ParticipationBuilder().build();
        uniqueParticipationList.add(participation);
        uniqueParticipationList.setParticipation(participation, participation);
        UniqueParticipationList expectedUniqueParticipationList = new UniqueParticipationList();
        expectedUniqueParticipationList.add(participation);
        assertEquals(expectedUniqueParticipationList, uniqueParticipationList);
    }

    @Test
    public void setParticipation_editedParticipationHasSameIdentity_success() {
        Participation participation = new ParticipationBuilder().build();
        uniqueParticipationList.add(participation);
        Participation editedParticipation = new ParticipationBuilder().build();
        uniqueParticipationList.setParticipation(participation, editedParticipation);
        UniqueParticipationList expectedUniqueParticipationList = new UniqueParticipationList();
        expectedUniqueParticipationList.add(editedParticipation);
        assertEquals(expectedUniqueParticipationList, uniqueParticipationList);
    }

    @Test
    public void remove_nullParticipation_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueParticipationList.remove(null));
    }

    @Test
    public void remove_participationDoesNotExist_throwsParticipationNotFoundException() {
        Participation participation = new ParticipationBuilder().build();
        assertThrows(ParticipationNotFoundException.class, (
        ) -> uniqueParticipationList.remove(participation));
    }

    @Test
    public void remove_existingParticipation_removesParticipation() {
        Participation participation = new ParticipationBuilder().build();
        uniqueParticipationList.add(participation);
        uniqueParticipationList.remove(participation);
        UniqueParticipationList expectedUniqueParticipationList = new UniqueParticipationList();
        assertEquals(expectedUniqueParticipationList, uniqueParticipationList);
    }

    @Test
    public void setParticipation_nullUniqueParticipationList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, (
        ) -> uniqueParticipationList.setParticipation((UniqueParticipationList) null));
    }

    @Test
    public void setParticipation_uniqueParticipationList_replacesOwnListWithProvidedUniqueParticipationList() {
        Participation participation = new ParticipationBuilder().build();
        uniqueParticipationList.add(participation);
        UniqueParticipationList expectedUniqueParticipationList = new UniqueParticipationList();
        Participation newParticipation = new ParticipationBuilder().build();
        expectedUniqueParticipationList.add(newParticipation);
        uniqueParticipationList.setParticipation(expectedUniqueParticipationList);
        assertEquals(expectedUniqueParticipationList, uniqueParticipationList);
    }

    @Test
    public void setParticipation_listWithDuplicateParticipations_throwsDuplicateParticipationException() {
        List<Participation> listWithDuplicateParticipations = Arrays.asList(
                new ParticipationBuilder().build(),
                new ParticipationBuilder().build());
        assertThrows(DuplicateParticipationException.class, (
        ) -> uniqueParticipationList.setParticipation(listWithDuplicateParticipations));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, (
        ) -> uniqueParticipationList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueParticipationList.asUnmodifiableObservableList().toString(),
                uniqueParticipationList.toString());
    }
}

