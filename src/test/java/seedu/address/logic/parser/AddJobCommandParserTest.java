package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_JOB_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_JOB_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_JOB_REQUIREMENTS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_JOB_SALARY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.JOB_COMPANY_DESC_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.JOB_DESCRIPTION_DESC_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.JOB_NAME_DESC_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.JOB_REQUIREMENTS_DESC_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.JOB_SALARY_DESC_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_COMPANY_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_DESCRIPTION_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_NAME_BARISTA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_SALARY_BARISTA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalJobs.BARISTA;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddJobCommand;
import seedu.address.model.common.Name;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobCompany;
import seedu.address.model.job.JobDescription;
import seedu.address.model.job.JobSalary;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.JobBuilder;

public class AddJobCommandParserTest {
    private AddJobCommandParser parser = new AddJobCommandParser();
    private String validJobString = PREAMBLE_WHITESPACE + JOB_NAME_DESC_BARISTA + JOB_COMPANY_DESC_BARISTA
            + JOB_SALARY_DESC_BARISTA + JOB_DESCRIPTION_DESC_BARISTA;

    @Test
    public void parse_allFieldsPresent_success() {
        Job expectedJob = BARISTA;

        // whitespace only preamble with multiple requirements
        assertParseSuccess(parser, validJobString + JOB_REQUIREMENTS_DESC_BARISTA, new AddJobCommand(expectedJob));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero requirements
        Job expectedJob = new JobBuilder(BARISTA).withRequirements().build();

        assertParseSuccess(parser, validJobString, new AddJobCommand(expectedJob));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {

        // multiple names
        assertParseFailure(parser, JOB_NAME_DESC_BARISTA + validJobString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple companies
        assertParseFailure(parser, JOB_COMPANY_DESC_BARISTA + validJobString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY));

        // multiple salaries
        assertParseFailure(parser, JOB_SALARY_DESC_BARISTA + validJobString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SALARY));

        // multiple descriptions
        assertParseFailure(parser, JOB_DESCRIPTION_DESC_BARISTA + validJobString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // multiple fields repeated
        assertParseFailure(parser, validJobString + validJobString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_COMPANY, PREFIX_SALARY,
                        PREFIX_DESCRIPTION));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validJobString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid company, note that it extends name
        assertParseFailure(parser, INVALID_COMPANY_NAME_DESC + validJobString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid salary
        assertParseFailure(parser, INVALID_JOB_SALARY_DESC + validJobString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SALARY));

        // invalid description
        assertParseFailure(parser, INVALID_JOB_DESCRIPTION_DESC + validJobString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validJobString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid company
        assertParseFailure(parser, validJobString + INVALID_COMPANY_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid salary
        assertParseFailure(parser, validJobString + INVALID_JOB_SALARY_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SALARY));

        // invalid description
        assertParseFailure(parser, validJobString + INVALID_JOB_DESCRIPTION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddJobCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_JOB_NAME_BARISTA + JOB_COMPANY_DESC_BARISTA + JOB_SALARY_DESC_BARISTA
                + JOB_DESCRIPTION_DESC_BARISTA, expectedMessage);

        // missing company prefix
        assertParseFailure(parser, JOB_NAME_DESC_BARISTA + VALID_JOB_COMPANY_BARISTA + JOB_SALARY_DESC_BARISTA
                + JOB_DESCRIPTION_DESC_BARISTA, expectedMessage);

        // missing salary prefix
        assertParseFailure(parser, JOB_NAME_DESC_BARISTA + JOB_COMPANY_DESC_BARISTA + VALID_JOB_SALARY_BARISTA
                + JOB_DESCRIPTION_DESC_BARISTA, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, JOB_NAME_DESC_BARISTA + JOB_COMPANY_DESC_BARISTA + JOB_SALARY_DESC_BARISTA
                + VALID_JOB_DESCRIPTION_BARISTA, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_JOB_NAME_BARISTA + VALID_JOB_COMPANY_BARISTA + VALID_JOB_SALARY_BARISTA
                + VALID_JOB_DESCRIPTION_BARISTA, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + JOB_COMPANY_DESC_BARISTA + JOB_SALARY_DESC_BARISTA
                + JOB_DESCRIPTION_DESC_BARISTA, Name.MESSAGE_CONSTRAINTS);

        // invalid company
        assertParseFailure(parser, JOB_NAME_DESC_BARISTA + INVALID_JOB_COMPANY_DESC + JOB_SALARY_DESC_BARISTA
                + JOB_DESCRIPTION_DESC_BARISTA, JobCompany.MESSAGE_CONSTRAINTS);

        // invalid salary
        assertParseFailure(parser, JOB_NAME_DESC_BARISTA + JOB_COMPANY_DESC_BARISTA + INVALID_JOB_SALARY_DESC
                + JOB_DESCRIPTION_DESC_BARISTA, JobSalary.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, JOB_NAME_DESC_BARISTA + JOB_COMPANY_DESC_BARISTA + JOB_SALARY_DESC_BARISTA
                + INVALID_JOB_DESCRIPTION_DESC, JobDescription.MESSAGE_CONSTRAINTS);

        // invalid requirement, uses tag under the hood
        assertParseFailure(parser, JOB_NAME_DESC_BARISTA + JOB_COMPANY_DESC_BARISTA + JOB_SALARY_DESC_BARISTA
                + JOB_DESCRIPTION_DESC_BARISTA + INVALID_JOB_REQUIREMENTS_DESC, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported, note that name and company will have the same
        // constraints, so avoid testing them together
        assertParseFailure(parser, INVALID_NAME_DESC + JOB_COMPANY_DESC_BARISTA + INVALID_JOB_SALARY_DESC
                + JOB_DESCRIPTION_DESC_BARISTA, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + validJobString,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddJobCommand.MESSAGE_USAGE));
    }
}
