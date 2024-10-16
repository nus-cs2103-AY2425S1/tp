package seedu.address.model.person;


import static seedu.address.logic.parser.CliSyntax.PREFIX_DONATED_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTNERSHIP_END_DATE;

import java.util.Set;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * A factory class to create instances of {@code Person} subclasses based on the specified {@code Role}.
 * <p>
 * This factory uses the provided role and other person attributes to determine which type of
 * {@code Person} subclass to instantiate (e.g., {@code Volunteer}, {@code Donor}, or {@code Partner}).
 * <p>
 * Each role requires specific attributes that are checked during the creation process. If a required
 * attribute is missing or invalid, a {@code ParseException} is thrown. The factory relies on the
 * {@code ArgumentMultimap} to supply role-specific fields.
 */
public class PersonFactory {

    /**
     * Creates a specific subclass of {@code Person} based on the provided role-specific fields.
     * This method examines the provided fields to determine which subclass of {@code Person}
     * (i.e., {@code Volunteer}, {@code Donor}, or {@code Partner}) to instantiate.
     *
     * @param name The name of the person.
     * @param phone The phone number of the person.
     * @param email The email address of the person.
     * @param address The address of the person.
     * @param tags The tags associated with the person.
     * @param hours The hours contributed, applicable for {@code Volunteer}; otherwise, {@code null}.
     * @param donatedAmount The donated amount, applicable for {@code Donor}; otherwise, {@code null}.
     * @param partnershipEndDate The partnership end date, applicable for {@code Partner}; otherwise, {@code null}.
     * @return A new instance of the appropriate {@code Person} subclass based on the provided fields.
     * @throws IllegalArgumentException If none of the subclass-specific fields are provided, or if
     *         more than one subclass-specific field is non-null.
     */
    public static Person createPerson(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                                      Hours hours, DonatedAmount donatedAmount, Date partnershipEndDate) {
        if (hours != null) {
            return new Volunteer(name, phone, email, address, tags, hours);
        } else if (donatedAmount != null) {
            return new Donor(name, phone, email, address, tags, donatedAmount);
        } else if (partnershipEndDate != null) {
            return new Partner(name, phone, email, address, tags, partnershipEndDate);
        } else {
            throw new IllegalArgumentException("At least one role-specific attribute (hours, donatedAmount, "
                    + "or partnershipEndDate) must be provided.");
        }
    }
    /**
     * Creates a {@code Person} instance based on the specified {@code Role}.
     *
     * @param role        The role of the person to create.
     * @param name        The name of the person.
     * @param phone       The phone number of the person.
     * @param email       The email address of the person.
     * @param address     The address of the person.
     * @param tags        The tags associated with the person.
     * @param argMultimap A map containing all parsed arguments, used to retrieve additional fields for specific roles.
     * @return A new instance of a subclass of {@code Person} based on the role.
     * @throws ParseException If a required field is missing or cannot be parsed.
     */
    public static Person createPerson(Role role, Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                                      ArgumentMultimap argMultimap) throws ParseException {
        switch (role) {
        case VOLUNTEER:
            if (argMultimap.getValue(PREFIX_HOURS).isEmpty()) {
                throw new ParseException("Hours must be specified for a Volunteer.");
            }
            Hours hours = ParserUtil.parseHours(argMultimap.getValue(PREFIX_HOURS).get());
            return new Volunteer(name, phone, email, address, tags, hours);

        case DONOR:
            if (argMultimap.getValue(PREFIX_DONATED_AMOUNT).isEmpty()) {
                throw new ParseException("Donated amount must be specified for a Donor.");
            }
            DonatedAmount donatedAmount =
                    ParserUtil.parseDonatedAmount(argMultimap.getValue(PREFIX_DONATED_AMOUNT).get());
            return new Donor(name, phone, email, address, tags, donatedAmount);
        case PARTNER:
            if (argMultimap.getValue(PREFIX_PARTNERSHIP_END_DATE).isEmpty()) {
                throw new ParseException("Partnership end date must be specified for a Partner.");
            }
            Date partnershipEndDate =
                    ParserUtil.parsePartnershipEndDate(argMultimap.getValue(PREFIX_PARTNERSHIP_END_DATE).get());
            return new Partner(name, phone, email, address, tags, partnershipEndDate);
        default:
            throw new ParseException("Unknown role:" + role);
        }
    }

    /**
     * Creates a {@code Person} instance based on the specified {@code Role} and provided details, using
     * fields from the {@code EditPersonDescriptor} and default values from {@code personToEdit} if available.
     *
     * @param role                 The role of the person to create, determining the subclass to instantiate.
     * @param name                 The person's name.
     * @param phone                The person's phone number.
     * @param email                The person's email address.
     * @param address              The person's address.
     * @param tags                 The tags associated with the person.
     * @param editPersonDescriptor The descriptor containing fields to update or add for the new person.
     * @param personToEdit         The original person whose details can be used as defaults for role-specific fields.
     * @return A new instance of the appropriate subclass of {@code Person} based on the specified {@code Role}.
     * @throws CommandException If a required field for the specified role is missing or if the role is unknown.
     */
    public static Person createPerson(Role role, Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                                      EditCommand.EditPersonDescriptor editPersonDescriptor, Person personToEdit)
            throws CommandException {
        switch (role) {
        case VOLUNTEER:
            Hours hours = retrieveVolunteerHours(editPersonDescriptor, personToEdit);
            return new Volunteer(name, phone, email, address, tags, hours);

        case DONOR:
            DonatedAmount donatedAmount = retrieveDonorAmount(editPersonDescriptor, personToEdit);
            return new Donor(name, phone, email, address, tags, donatedAmount);

        case PARTNER:
            Date endDate = retrievePartnerEndDate(editPersonDescriptor, personToEdit);
            return new Partner(name, phone, email, address, tags, endDate);

        default:
            throw new CommandException("Unknown role: " + role);
        }
    }

    /**
     * Retrieves the hours value for a {@code Volunteer}. If {@code personToEdit} is a {@code Volunteer},
     * its hours value is used as the default. Otherwise, {@code editPersonDescriptor} must contain a non-null hours
     * value.
     *
     * @param descriptor The descriptor containing updated fields.
     * @param person     The original person whose role-specific field may be used as a default.
     * @return The {@code Hours} for the new or updated {@code Volunteer}.
     * @throws CommandException If the hours field is missing and no default is available.
     */
    private static Hours retrieveVolunteerHours(EditCommand.EditPersonDescriptor descriptor, Person person)
            throws CommandException {
        if (person instanceof Volunteer) {
            Volunteer volunteer = (Volunteer) person;
            return descriptor.getHours().orElse(volunteer.getHours());
        } else {
            return descriptor.getHours().orElseThrow(() -> new CommandException("Hours must be provided for a new "
                    + "Volunteer."));
        }
    }

    /**
     * Retrieves the donated amount for a {@code Donor}. If {@code personToEdit} is a {@code Donor},
     * its donated amount is used as the default. Otherwise, {@code editPersonDescriptor} must contain a non-null
     * donated amount.
     *
     * @param descriptor The descriptor containing updated fields.
     * @param person     The original person whose role-specific field may be used as a default.
     * @return The {@code DonatedAmount} for the new or updated {@code Donor}.
     * @throws CommandException If the donated amount field is missing and no default is available.
     */
    private static DonatedAmount retrieveDonorAmount(EditCommand.EditPersonDescriptor descriptor, Person person)
            throws CommandException {
        if (person instanceof Donor) {
            Donor donor = (Donor) person;
            return descriptor.getDonatedAmount().orElse(donor.getDonatedAmount());
        } else {
            return descriptor.getDonatedAmount().orElseThrow(() -> new CommandException("Donated amount must be "
                    + "provided for a new Donor."));
        }
    }

    /**
     * Retrieves the partnership end date for a {@code Partner}. If {@code personToEdit} is a {@code Partner},
     * its end date is used as the default. Otherwise, {@code editPersonDescriptor} must contain a non-null end date.
     *
     * @param descriptor The descriptor containing updated fields.
     * @param person     The original person whose role-specific field may be used as a default.
     * @return The {@code Date} for the new or updated {@code Partner}.
     * @throws CommandException If the end date field is missing and no default is available.
     */
    private static Date retrievePartnerEndDate(EditCommand.EditPersonDescriptor descriptor, Person person)
            throws CommandException {
        if (person instanceof Partner) {
            Partner partner = (Partner) person;
            return descriptor.getEndDate().orElse(partner.getEndDate());
        } else {
            return descriptor.getEndDate().orElseThrow(() -> new CommandException("Partnership end date must be "
                    + "provided for a new Partner."));
        }
    }



}


