package seedu.address.testutil;

import seedu.address.model.person.Property;

/**
 * A utility class containing a list of {@code Property} objects to be used in tests.
 */
public class TypicalProperties {

    public static final Property PROPERTY_A_HDB = new PropertyToBuyBuilder().withHousingType("h")
            .withPostalCode("522522").withUnitNumber("10-01").withPrice("1500000").build();
    public static final Property PROPERTY_B_BTO = new PropertyToBuyBuilder().withHousingType("b")
            .withPostalCode("533255").withUnitNumber("11-21").withPrice("2000000").build();
    public static final Property PROPERTY_C_CONDO = new PropertyToBuyBuilder().withHousingType("c")
            .withPostalCode("544488").withUnitNumber("12-33").withPrice("2500000").withTags("spacious").build();
    public static final Property PROPERTY_D_CONDO = new PropertyToBuyBuilder().withHousingType("c")
            .withPostalCode("555511").withUnitNumber("13-31").withPrice("3000000")
            .withTags("near mrt", "spacious", "5 toilets").build();
    public static final Property PROPERTY_E_APARTMENT = new PropertyToBuyBuilder().withHousingType("a")
            .withPostalCode("566744").withUnitNumber("14-21").withPrice("3500000")
            .withTags("5 bedrooms", "corner unit").build();
    public static final Property PROPERTY_F_OTHERPROPERTY = new PropertyToBuyBuilder().withHousingType("o")
            .withPostalCode("577755").withUnitNumber("15-41").withPrice("4000000")
            .withTags("penthouse", "sea view").build();
    public static final Property PROPERTY_G_OTHERPROPERTY = new PropertyToBuyBuilder().withHousingType("o")
            .withPostalCode("588866").withUnitNumber("16-51").withPrice("4500000")
            .withTags("duplex", "garden").build();
    public static final Property PROPERTY_H_HDB = new PropertyToBuyBuilder().withHousingType("h")
            .withPostalCode("599977").withUnitNumber("17-61").withPrice("5000000")
            .withTags("luxury", "private pool").build();
    public static final Property PROPERTY_I_APARTMENT = new PropertyToBuyBuilder().withHousingType("a")
            .withPostalCode("588977").withUnitNumber("17-60").withPrice("6000000")
            .withTags("new", "private lift").build();
    public static final Property PROPERTY_J_BTO = new PropertyToBuyBuilder().withHousingType("b")
            .withPostalCode("587977").withUnitNumber("16-65").withPrice("6500000")
            .withTags("nice scenery").build();
}
