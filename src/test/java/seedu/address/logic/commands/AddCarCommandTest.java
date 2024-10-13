package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.car.Car;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddCarCommandTest {

    @Test
    public void AddCarCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCarCommand(null, null));
    }

    public void AddCarCommand_badIndex() {
        //assertThrows(Exception.class, () -> new AddCarCommand(-1, ));
    }

    @Test
    public void addCarToEligiblePerson() {

    }
}
