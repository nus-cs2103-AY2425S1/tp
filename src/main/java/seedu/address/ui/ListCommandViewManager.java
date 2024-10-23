package seedu.address.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.logic.commands.CommandResult;

/**
 * Manages the list views (owners, pets, combined) based on the command result.
 */
public class ListCommandViewManager {

    private VBox ownerList;
    private VBox petList;
    private StackPane combinedListPanelPlaceholder;

    /**
     * Constructs a {@code ListCommandViewManager} with the relevant view elements.
     *
     * @param ownerList                  The owner list view (VBox).
     * @param petList                    The pet list view (VBox).
     * @param combinedListPanelPlaceholder The combined list view placeholder (StackPane).
     */
    public ListCommandViewManager(VBox ownerList, VBox petList, StackPane combinedListPanelPlaceholder) {
        this.ownerList = ownerList;
        this.petList = petList;
        this.combinedListPanelPlaceholder = combinedListPanelPlaceholder;
    }

    /**
     * Toggles the list view based on the {@code commandResult}.
     *
     * @param commandResult The result of the command that determines which list to show.
     */
    public void toggleListView(CommandResult commandResult) {
        if (commandResult.isOwnerListCommand()) {
            changeToOwnersOnly();
        } else if (commandResult.isPetListCommand()) {
            changeToPetsOnly();
        } else if (commandResult.isCombinedListCommand()) {
            changeToCombinedList();
        }
    }

    /**
     * Changes the view to show only the pet list.
     */
    private void changeToPetsOnly() {
        ownerList.setVisible(false);
        ownerList.setManaged(false);
        combinedListPanelPlaceholder.setVisible(false);
        combinedListPanelPlaceholder.setManaged(false);

        // Show the pet list
        petList.setVisible(true);
        petList.setManaged(true);
    }

    /**
     * Changes the view to show only the owner list.
     */
    private void changeToOwnersOnly() {
        petList.setVisible(false);
        petList.setManaged(false);
        combinedListPanelPlaceholder.setVisible(false);
        combinedListPanelPlaceholder.setManaged(false);

        // Show the owner list
        ownerList.setVisible(true);
        ownerList.setManaged(true);
    }

    /**
     * Shows both the pet and owner lists in the combined view.
     */
    private void changeToCombinedList() {
        petList.setVisible(false);
        petList.setManaged(false);
        combinedListPanelPlaceholder.setVisible(true);
        combinedListPanelPlaceholder.setManaged(true);

        // Hide the owner list
        ownerList.setVisible(false);
        ownerList.setManaged(false);
    }
}
