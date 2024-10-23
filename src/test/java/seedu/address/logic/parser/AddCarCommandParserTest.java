package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CAR_MAKE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CAR_MODEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_MAKE_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_MODEL_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_VIN_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_VRN_B;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAKE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VRN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCarCommand;
import seedu.address.model.car.Car;
import seedu.address.model.car.CarMake;
import seedu.address.model.car.CarModel;
import seedu.address.model.car.Vin;
import seedu.address.model.car.Vrn;

public class AddCarCommandParserTest {

    private static final String INVALID_CAR_VRN = "SHA 7891 A (Z)";
    private static final String INVALID_CAR_VIN = "ASDF1234QWERTY5";
    private AddCarCommandParser parser = new AddCarCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // equivalent of 'add-car 1 vrn/VIN1234A vin/KMHGH4JH3EU073801 make/Toyota model/Corolla'
        Car car = new Car(new Vrn(VALID_CAR_VRN_B), new Vin(VALID_CAR_VIN_B),
                new CarMake(VALID_CAR_MAKE_B), new CarModel(VALID_CAR_MODEL_B));
        AddCarCommand expectedCommand = new AddCarCommand(Index.fromOneBased(1), car);

        // Test with full, valid input
        assertParseSuccess(parser, "1 " + PREFIX_VRN + VALID_CAR_VRN_B + " "
                        + PREFIX_VIN + VALID_CAR_VIN_B + " " + PREFIX_MAKE
                        + VALID_CAR_MAKE_B + " " + PREFIX_MODEL + VALID_CAR_MODEL_B,
                expectedCommand);
    }

    @Test
    public void parse_missingCarDetails_failure() {
        // Missing VIN
        assertParseFailure(parser, "1 " + PREFIX_VRN + VALID_CAR_VRN_B + " " + PREFIX_MAKE
                + VALID_CAR_MAKE_B + " " + PREFIX_MODEL
                + VALID_CAR_MODEL_B, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCarCommand.MESSAGE_USAGE));

        // Missing VRN
        assertParseFailure(parser, "1 " + PREFIX_VIN + VALID_CAR_VIN_B + " " + PREFIX_MAKE
                + VALID_CAR_MAKE_B + " " + PREFIX_MODEL
                + VALID_CAR_MODEL_B, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCarCommand.MESSAGE_USAGE));

        // Missing Make
        assertParseFailure(parser, "1 " + PREFIX_VRN + VALID_CAR_VRN_B + " "
                + PREFIX_VIN + VALID_CAR_VIN_B + " " + PREFIX_MODEL
                + VALID_CAR_MODEL_B, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCarCommand.MESSAGE_USAGE));

        // Missing Model
        assertParseFailure(parser, "1 " + PREFIX_VRN + VALID_CAR_VRN_B + " " + PREFIX_VIN
                + VALID_CAR_VIN_B + " " + PREFIX_MAKE
                + VALID_CAR_MAKE_B, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCarCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // bad VRN
        assertParseFailure(parser, "1 " + PREFIX_VRN + INVALID_CAR_VRN + " " + PREFIX_VIN
                + VALID_CAR_VIN_B + " " + PREFIX_MAKE + VALID_CAR_MAKE_B + " "
                + PREFIX_MODEL + VALID_CAR_MODEL_B, Vrn.MESSAGE_CONSTRAINTS);

        // bad VIN
        assertParseFailure(parser, "1 " + PREFIX_VRN + VALID_CAR_VRN_B + " " + PREFIX_VIN
                + INVALID_CAR_VIN + " " + PREFIX_MAKE + VALID_CAR_MAKE_B + " " + PREFIX_MODEL
                + VALID_CAR_MODEL_B, Vin.MESSAGE_CONSTRAINTS);

        // bad Make
        assertParseFailure(parser, "1 " + PREFIX_VRN + VALID_CAR_VRN_B + " " + PREFIX_VIN
                + VALID_CAR_VIN_B + " " + PREFIX_MAKE + INVALID_CAR_MAKE + " " + PREFIX_MODEL
                + VALID_CAR_MODEL_B, CarMake.MESSAGE_CONSTRAINTS);

        // bad Model
        assertParseFailure(parser, "1 " + PREFIX_VRN + VALID_CAR_VRN_B + " " + PREFIX_VIN
                + VALID_CAR_VIN_B + " " + PREFIX_MAKE + VALID_CAR_MAKE_B + " " + PREFIX_MODEL
                + INVALID_CAR_MODEL, CarModel.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // Missing index
        assertParseFailure(parser, PREFIX_VRN + VALID_CAR_VRN_B + " " + PREFIX_VIN + VALID_CAR_VIN_B + " "
                        + PREFIX_MAKE + VALID_CAR_MAKE_B + " " + PREFIX_MODEL + VALID_CAR_MODEL_B,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCarCommand.MESSAGE_USAGE));
    }
}
