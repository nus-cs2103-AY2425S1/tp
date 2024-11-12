package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.NoWindowException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.PersonDetails;

/**
 * Represents a command to view the details of a person identified by their index in the last shown list.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the full information of the person "
            + "identified by the index number used in the last person listing. "
            + "View window can be closed by the \"close\" command.\n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String NO_WINDOWS_OPEN = "No view windows are currently open.";
    private static Stage currentStage;
    private static Person currentPersonDisplayed;
    private static PersonDetails personDetailsController;
    private final Index index;


    /**
     * Constructs a {@code ViewCommand} with the specified {@code Index}.
     *
     * @param index The index of the person to view.
     */
    public ViewCommand(Index index) {
        requireNonNull(index);
        this.index = index;
        if (personDetailsController == null) {
            personDetailsController = new PersonDetails();
        }
    }

    /**
     * Executes the view command, displaying the details of the person identified by the index.
     *
     * @param model The model containing the list of persons.
     * @return A {@code CommandResult} indicating the success of the command.
     * @throws CommandException If the index is out of bounds or if an error occurs when loading the new window.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToShow = lastShownList.get(index.getZeroBased());

        try {
            // Close the previous window if it's still open
            if (currentStage != null && currentStage.isShowing()) {
                currentStage.close();
            }

            // Load the FXML file for the new window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PersonDetails.fxml"));
            Parent root = fxmlLoader.load();

            PersonDetails controller = fxmlLoader.getController();
            personDetailsController = controller;
            controller.setPersonDetails(personToShow);

            // Create a new stage (window) for the popup
            Stage newStage = new Stage();
            newStage.setTitle("Person Details");

            // Set the scene with the loaded FXML content
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            // Make it non-modal (won't block focus)
            newStage.initModality(Modality.NONE);
            newStage.setAlwaysOnTop(false);

            // Keep track of the current stage (for closing later)
            currentStage = newStage;
            currentPersonDisplayed = personToShow;

            // Show the new window without stealing focus
            newStage.show();

        } catch (IOException e) {
            throw new CommandException("Failed to load the new window.", e);
        }

        return new CommandResult("Person details displayed.");
    }

    /**
     * Manually close the current window if it's still open.
     */
    public static void closeCurrentWindow() {
        if (currentStage == null) {
            throw new NoWindowException(NO_WINDOWS_OPEN);
        }
        if (currentStage.isShowing()) {
            currentStage.close();
            currentStage = null;
        }
    }

    /**
     * Returns the instant of the current stage, or {null} if there is no stage open.
     */

    public static Stage getCurrentStage() {
        return currentStage;
    }

    /**
     * Updates the current display window with any new information for the specified person, if required.
     * <p>
     * This method compares the person currently displayed with the updated information provided in {@code newPerson}.
     * If {@code personToEdit} matches the currently displayed person and the display window (stage) is open,
     * it refreshes the view with the new data.
     *
     * <p>
     * This method follows the Model-View-Controller (MVC) pattern, where it updates the view based on the model's data
     * without reopening the display window.
     *
     * @param personToEdit the {@code Person} object representing the person currently displayed
     * @param newPerson the updated {@code Person} object containing the latest information
     */
    public static void updateDisplay(Person personToEdit, Person newPerson) {
        if (!personToEdit.isSamePerson(currentPersonDisplayed) || currentStage == null) {
            return;
        }
        currentPersonDisplayed = newPerson;
        personDetailsController.refresh(newPerson);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        return index.equals(otherViewCommand.index);
    }
}
