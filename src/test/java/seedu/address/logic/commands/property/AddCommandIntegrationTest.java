package seedu.address.logic.commands.property;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.property.TypicalProperties.getTypicalPropertyList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.property.Property;
import seedu.address.testutil.property.PropertyBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalBuyerList(), new UserPrefs(),
                getTypicalMeetUpList(), getTypicalPropertyList());
    }

    @Test
    public void execute_newProperty_success() {
        Property validProperty = new PropertyBuilder().build();

        Model expectedModel = new ModelManager(getTypicalBuyerList(), new UserPrefs(),
                getTypicalMeetUpList(), model.getPropertyList());
        expectedModel.addProperty(validProperty);

        assertCommandSuccess(new AddCommand(validProperty), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validProperty)),
                expectedModel);
    }

    @Test
    public void execute_duplicateProperty_throwsCommandException() {
        Property propertyInList = model.getPropertyList().getPropertyList().get(0);
        assertCommandFailure(new AddCommand(propertyInList), model,
                AddCommand.MESSAGE_DUPLICATE_PROPERTY);
    }
}
