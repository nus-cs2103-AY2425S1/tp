package seedu.address.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ViewToggler;

/**
 * Manages the list views (owners, pets, combined) based on the command result.
 */
public class ListCommandViewManager {

    private VBox ownerList;
    private VBox petList;

    private VBox linkList;
    private StackPane combinedListPanelPlaceholder;

    private StackPane linkListPanelPlaceholder;

    /**
     * Constructs a {@code ListCommandViewManager} with the relevant view elements.
     *
     * @param ownerList                  The owner list view (VBox).
     * @param petList                    The pet list view (VBox).
     * @param combinedListPanelPlaceholder The combined list view placeholder (StackPane).
     */
    public ListCommandViewManager(VBox ownerList, VBox petList, StackPane combinedListPanelPlaceholder,
                                  VBox linkList, StackPane linkListPanelPlaceholder) {
        this.ownerList = ownerList;
        this.petList = petList;
        this.linkList = linkList;
        this.combinedListPanelPlaceholder = combinedListPanelPlaceholder;
        this.linkListPanelPlaceholder = linkListPanelPlaceholder;
    }

    /**
     * Toggles the list view based on the {@code commandResult}.
     *
     * @param commandResult The result of the command that determines which list to show.
     */
    public void toggleListView(CommandResult commandResult) {
        if (commandResult.getCommandType().equals(ViewToggler.LIST_OWNER_COMMAND)) {
            changeToOwnersOnly();
        } else if (commandResult.getCommandType().equals(ViewToggler.LIST_PET_COMMAND)) {
            changeToPetsOnly();
        } else if (commandResult.getCommandType().equals(ViewToggler.LIST_BOTH_COMMAND)) {
            changeToCombinedList();
        } else if (commandResult.getCommandType().equals(ViewToggler.LINK_OWNER_TO_PET_COMMAND)) {
            showLinks();
        } else {
            // do nothing as command does not change GUI
        }
    }

    /**
     * Changes the view to show the newly created link from an owner to a pet.
     *
     */
    public void showLinks() {
        ownerList.setVisible(false);
        ownerList.setManaged(false);
        petList.setVisible(false);
        petList.setManaged(false);
        combinedListPanelPlaceholder.setVisible(false);
        combinedListPanelPlaceholder.setManaged(false);
        linkList.setVisible(true);
        linkList.setManaged(true);
        //linkListPanelPlaceholder.setVisible(true);
        //linkListPanelPlaceholder.setManaged(true);

        /*
        // Create a new LinkCard for the link
        LinkCard linkCard = new LinkCard(link, 1); // Assuming 1 is the index for the first card

        // Add the LinkCard to the combinedListPanelPlaceholder or a new VBox
        VBox linkList = new VBox();
        linkList.getChildren().add(linkCard.getRoot());
        combinedListPanelPlaceholder.getChildren().clear(); // Clear previous content
        combinedListPanelPlaceholder.getChildren().add(linkList);

        // Show the LinkCard
        combinedListPanelPlaceholder.setVisible(true);
        combinedListPanelPlaceholder.setManaged(true);
         */
    }

    /**
     * Changes the view to show only the pet list.
     */
    private void changeToPetsOnly() {
        ownerList.setVisible(false);
        ownerList.setManaged(false);
        combinedListPanelPlaceholder.setVisible(false);
        combinedListPanelPlaceholder.setManaged(false);
        linkList.setVisible(false);
        linkList.setManaged(false);

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
        linkList.setVisible(false);
        linkList.setManaged(false);

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
        linkList.setVisible(false);
        linkList.setManaged(false);

        // Hide the owner list
        ownerList.setVisible(false);
        ownerList.setManaged(false);
    }
}
