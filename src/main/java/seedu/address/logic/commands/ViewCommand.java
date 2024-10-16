package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import seedu.address.ui.PersonDetails;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";

//    public static final String MESSAGE_NaN = "Please provide a proper ID (integer)";
//    public static final String MESSAGE_OUT_OF_BOUNDS = "Please provide a proper ID within the range";

    private final Index index;

    public ViewCommand(Index index){
        requireNonNull(index);
        this.index = index;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToShow = lastShownList.get(index.getZeroBased());

        try {
            // Load the FXML file for the new window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PersonDetails.fxml"));
            Parent root = fxmlLoader.load();

            PersonDetails controller = fxmlLoader.getController();

            // Pass the selected person to the controller
            controller.setPersonDetails(personToShow);


            // Create a new stage (window) for the new window
            Stage newStage = new Stage();
            newStage.setTitle("Person Details");

            // Set the scene with the loaded FXML content
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            // Show the new window (non-modal, separate from main window)
            newStage.show();

        } catch (IOException e) {
            throw new CommandException("Failed to load the new window.", e);
        }

        return new CommandResult("Person details displayed.");

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        return index.equals(otherViewCommand.index);
    }


}
