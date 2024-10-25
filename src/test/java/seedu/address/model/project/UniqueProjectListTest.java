package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProjects.ALPHA;
import static seedu.address.testutil.TypicalProjects.BETA;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.project.exceptions.DuplicateProjectException;
import seedu.address.model.project.exceptions.ProjectNotFoundException;
import seedu.address.testutil.ProjectBuilder;

public class UniqueProjectListTest {

    private final UniqueProjectList uniqueProjectList = new UniqueProjectList();

    @Test
    public void contains_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.contains(null));
    }

    @Test
    public void contains_projectNotInList_returnsFalse() {
        assertFalse(uniqueProjectList.contains(ALPHA));
    }

    @Test
    public void contains_projectInList_returnsTrue() {
        uniqueProjectList.add(ALPHA);
        assertTrue(uniqueProjectList.contains(ALPHA));
    }

    @Test
    public void contains_projectWithSameIdentityFieldsInList_returnsTrue() {
        uniqueProjectList.add(ALPHA);
        Project editedAlice = new ProjectBuilder(ALPHA).withId("A0276123J")
                .build();
        assertTrue(uniqueProjectList.contains(editedAlice));
    }

    @Test
    public void add_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.add(null));
    }

    @Test
    public void add_duplicateProject_throwsDuplicateEmployeeException() {
        uniqueProjectList.add(ALPHA);
        assertThrows(DuplicateProjectException.class, () -> uniqueProjectList.add(ALPHA));
    }

    @Test
    public void setProject_nullTargetProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.setProject(null, ALPHA));
    }

    @Test
    public void setProject_nullEditedProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.setProject(ALPHA, null));
    }

    @Test
    public void setProject_targetProjectNotInList_throwsProjectNotFoundException() {
        assertThrows(ProjectNotFoundException.class, () -> uniqueProjectList.setProject(ALPHA, ALPHA));
    }

    @Test
    public void setProject_editedProjectIsSameProject_success() {
        uniqueProjectList.add(ALPHA);
        uniqueProjectList.setProject(ALPHA, ALPHA);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(ALPHA);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProject_editedProjectHasSameIdentity_success() {
        uniqueProjectList.add(ALPHA);
        Project editedAlpha = new ProjectBuilder(ALPHA).withId("A0276123K")
                .build();
        uniqueProjectList.setProject(ALPHA, editedAlpha);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(editedAlpha);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProject_editedProjectHasDifferentIdentity_success() {
        uniqueProjectList.add(ALPHA);
        uniqueProjectList.setProject(ALPHA, BETA);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(BETA);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProject_editedProjectHasNonUniqueIdentity_throwsDuplicateProjectException() {
        uniqueProjectList.add(ALPHA);
        uniqueProjectList.add(BETA);
        assertThrows(DuplicateProjectException.class, () -> uniqueProjectList.setProject(ALPHA, BETA));
    }

    @Test
    public void remove_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.remove(null));
    }

    @Test
    public void remove_projectDoesNotExist_throwsProjectNotFoundException() {
        assertThrows(ProjectNotFoundException.class, () -> uniqueProjectList.remove(ALPHA));
    }

    @Test
    public void remove_existingProject_removesProject() {
        uniqueProjectList.add(ALPHA);
        uniqueProjectList.remove(ALPHA);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProjects_nullUniqueProjectList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.setProjects((UniqueProjectList) null));
    }

    @Test
    public void setProjects_uniqueProjectList_replacesOwnListWithProvidedUniqueProjectList() {
        uniqueProjectList.add(ALPHA);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(BETA);
        uniqueProjectList.setProjects(expectedUniqueProjectList);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProjects_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueProjectList.setProjects((List<Project>) null));
    }

    @Test
    public void setProjects_list_replacesOwnListWithProvidedList() {
        uniqueProjectList.add(ALPHA);
        List<Project> employeeList = Collections.singletonList(BETA);
        uniqueProjectList.setProjects(employeeList);
        UniqueProjectList expectedUniqueProjectList = new UniqueProjectList();
        expectedUniqueProjectList.add(BETA);
        assertEquals(expectedUniqueProjectList, uniqueProjectList);
    }

    @Test
    public void setProjects_listWithDuplicateProjects_throwsDuplicateEmployeeException() {
        List<Project> listWithDuplicateEmployees = Arrays.asList(ALPHA, ALPHA);
        assertThrows(DuplicateProjectException.class, () -> uniqueProjectList.setProjects(listWithDuplicateEmployees));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueProjectList
                .asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueProjectList.asUnmodifiableObservableList().toString(), uniqueProjectList.toString());
    }
}
