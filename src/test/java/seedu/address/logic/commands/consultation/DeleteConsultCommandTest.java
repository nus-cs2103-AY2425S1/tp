package seedu.address.logic.commands.consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.consultation.Consultation;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteConsultCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        Index firstIndex = Index.fromOneBased(1);
        Consultation consultToDelete = model.getFilteredConsultationList().get(firstIndex.getZeroBased());
        Set<Index> firstIndexSet = new HashSet<>();
        firstIndexSet.add(firstIndex);
        DeleteConsultCommand deleteConsultCommand = new DeleteConsultCommand(firstIndexSet);

        String expectedMessage = String.format(DeleteConsultCommand.MESSAGE_DELETE_CONSULT_SUCCESS,
                Messages.format(consultToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteConsult(consultToDelete);

        assertCommandSuccess(deleteConsultCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleValidIndex_success() {
        ArrayList<Consultation> consultsToDelete = new ArrayList<>();
        Index firstIndex = Index.fromOneBased(1);
        Index thirdIndex = Index.fromOneBased(3);
        consultsToDelete.add(model.getFilteredConsultationList().get(firstIndex.getZeroBased()));
        consultsToDelete.add(model.getFilteredConsultationList().get(thirdIndex.getZeroBased()));


        Set<Index> indexSet = new HashSet<>();
        indexSet.add(firstIndex);
        indexSet.add(thirdIndex);
        DeleteConsultCommand deleteConsultCommand = new DeleteConsultCommand(indexSet);

        String formattedDeletedConsults = consultsToDelete.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n"));
        String expectedMessage = String.format(DeleteConsultCommand.MESSAGE_DELETE_CONSULT_SUCCESS,
                formattedDeletedConsults);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        consultsToDelete.forEach(expectedModel::deleteConsult);

        assertCommandSuccess(deleteConsultCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allInvalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredConsultationList().size() + 1);
        Index outOfBoundIndex2 = Index.fromOneBased(model.getFilteredConsultationList().size() + 2);
        Set<Index> outOfBoundIndexSet = new HashSet<>();
        outOfBoundIndexSet.add(outOfBoundIndex);
        outOfBoundIndexSet.add(outOfBoundIndex2);
        DeleteConsultCommand deleteConsultCommand = new DeleteConsultCommand(outOfBoundIndexSet);

        String formattedOutOfBoundIndices = outOfBoundIndex.getOneBased() + ", " + outOfBoundIndex2.getOneBased();


        assertCommandFailure(deleteConsultCommand, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                formattedOutOfBoundIndices));
    }

    @Test
    public void execute_someInvalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredConsultationList().size() + 1);
        Index outOfBoundIndex2 = Index.fromOneBased(model.getFilteredConsultationList().size() + 2);

        Set<Index> outOfBoundIndexSet = new HashSet<>();
        outOfBoundIndexSet.add(outOfBoundIndex);
        outOfBoundIndexSet.add(Index.fromOneBased(1));
        outOfBoundIndexSet.add(outOfBoundIndex2);

        DeleteConsultCommand deleteConsultCommand = new DeleteConsultCommand(outOfBoundIndexSet);

        String formattedOutOfBoundIndices = outOfBoundIndex.getOneBased() + ", " + outOfBoundIndex2.getOneBased();

        assertCommandFailure(deleteConsultCommand, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                formattedOutOfBoundIndices));
    }

    @Test
    public void execute_oneOfOneInvalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredConsultationList().size() + 1);
        Set<Index> outOfBoundIndexSet = new HashSet<>();
        outOfBoundIndexSet.add(outOfBoundIndex);
        outOfBoundIndexSet.add(Index.fromOneBased(1));
        DeleteConsultCommand deleteConsultCommand = new DeleteConsultCommand(outOfBoundIndexSet);

        assertCommandFailure(deleteConsultCommand, model, String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN,
                String.valueOf(outOfBoundIndex.getOneBased())));
    }

    @Test
    public void equals() {
        // create commands
        Index firstIndex = Index.fromOneBased(1);
        Index secondIndex = Index.fromOneBased(2);
        Set<Index> firstIndexSet = new HashSet<>();
        firstIndexSet.add(firstIndex);
        Set<Index> secondIndexSet = new HashSet<>();
        secondIndexSet.add(secondIndex);
        DeleteConsultCommand deleteFirstCommand = new DeleteConsultCommand(firstIndexSet);
        DeleteConsultCommand deleteSecondCommand = new DeleteConsultCommand(secondIndexSet);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        Set<Index> firstIndexSetCopy = new HashSet<>();
        firstIndexSetCopy.add(firstIndex);
        DeleteConsultCommand deleteFirstCommandCopy = new DeleteConsultCommand(firstIndexSetCopy);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Set<Index> targetIndexSet = new HashSet<>();
        targetIndexSet.add(targetIndex);
        DeleteConsultCommand deleteConsultCommand = new DeleteConsultCommand(targetIndexSet);
        String expected = DeleteConsultCommand.class.getCanonicalName() + "{targetIndices=" + targetIndexSet + "}";
        assertEquals(expected, deleteConsultCommand.toString());
    }

    @Test
    public void getCommandTypeMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Set<Index> targetIndexSet = new HashSet<>();
        targetIndexSet.add(targetIndex);
        DeleteConsultCommand deleteCommand = new DeleteConsultCommand(targetIndexSet);
        assertEquals(deleteCommand.getCommandType(), CommandType.DELETECONSULT);
    }
}
