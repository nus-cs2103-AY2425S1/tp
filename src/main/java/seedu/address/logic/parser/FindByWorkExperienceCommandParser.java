package seedu.address.logic.parser;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindByWorkExperienceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.WorkExperienceContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindByWorkExperience object.
 */

public class FindByWorkExperienceCommandParser implements Parser<FindByWorkExperienceCommand> {

    @Override
    public FindByWorkExperienceCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty() || !trimmedArgs.startsWith("w/")) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    FindByWorkExperienceCommand.MESSAGE_USAGE));
        }

        // Remove "w/" prefix
        String workExperience = trimmedArgs.substring(2).trim();

        // Split by commas
        String[] workExperienceFields = workExperience.split(",");

        String role = null;
        String company = null;
        String year = null;

        // Handle based on number of fields
        if (workExperienceFields.length == 1) {
            // Only company provided
            company = workExperienceFields[0].trim();
        } else if (workExperienceFields.length == 2) {
            // Either Role + Company or Company + Year
            String firstField = workExperienceFields[0].trim();
            String secondField = workExperienceFields[1].trim();

            if (secondField.matches("\\d{4}")) {
                // The second field is a valid year (Company + Year)
                company = firstField;
                year = secondField;
            } else {
                // The second field is not a year (Role + Company)
                role = firstField;
                company = secondField;
            }
        } else if (workExperienceFields.length == 3) {
            // Role, Company, and Year provided
            role = workExperienceFields[0].trim();
            company = workExperienceFields[1].trim();
            year = workExperienceFields[2].trim();

            if (!year.matches("\\d{4}")) {
                throw new ParseException("Year must be a valid 4-digit number.");
            }
        } else {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    FindByWorkExperienceCommand.MESSAGE_USAGE));
        }

        // Ensure company is present
        if (company == null || company.isEmpty()) {
            throw new ParseException("Company is a required field.");
        }

        return new FindByWorkExperienceCommand(new WorkExperienceContainsKeywordsPredicate(role, company, year));
    }
}
