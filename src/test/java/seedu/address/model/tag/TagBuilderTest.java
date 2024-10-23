package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagBuilderTest {

    private final TagBuilder tagBuilder = new TagBuilder();

    // DifficultyTag Tests
    @Test
    public void build_difficultyTagWithAllCases_returnsCorrectToString() {
        Tag tagLow = tagBuilder.build("difficulty_lOw");
        Tag tagMedium = tagBuilder.build("difficulty_MeDium");
        Tag tagHigh = tagBuilder.build("difficulty_HiGh");

        assertEquals("Difficulty:LOW", tagLow.toString());
        assertEquals("Difficulty:MEDIUM", tagMedium.toString());
        assertEquals("Difficulty:HIGH", tagHigh.toString());
    }

    // SalaryTag Tests
    @Test
    public void build_salaryTagWithAllCases_returnsCorrectToString() {
        Tag tagLow = tagBuilder.build("salary_LoW");
        Tag tagMedium = tagBuilder.build("salary_mEdIum");
        Tag tagHigh = tagBuilder.build("salary_High");

        assertEquals("Salary:LOW", tagLow.toString());
        assertEquals("Salary:MEDIUM", tagMedium.toString());
        assertEquals("Salary:HIGH", tagHigh.toString());
    }

    // Work-Life Balance Tag Tests
    @Test
    public void build_wlbTagWithAllCases_returnsCorrectToString() {
        Tag tagLow = tagBuilder.build("wlb_loW");
        Tag tagMedium = tagBuilder.build("wlb_MEDium");
        Tag tagHigh = tagBuilder.build("wlb_high");

        assertEquals("WLB:LOW", tagLow.toString());
        assertEquals("WLB:MEDIUM", tagMedium.toString());
        assertEquals("WLB:HIGH", tagHigh.toString());
    }

    // PeriodTag Tests
    @Test
    public void build_periodTagWithYear_returnsCorrectToString() {
        Tag tagSummer = tagBuilder.build("period_summer_2000");
        Tag tagWinter = tagBuilder.build("period_winter_2500");
        Tag tagPart = tagBuilder.build("period_PARTTIME_2000");

        assertEquals("Period:SUMMER-2000", tagSummer.toString());
        assertEquals("Period:WINTER-2500", tagWinter.toString());
        assertEquals("Period:PARTTIME-2000", tagPart.toString());

    }

    // Custom Tag Test
    @Test
    public void build_customTag_returnsCorrectToString() {
        Tag tag = tagBuilder.build("customTag");
        assertEquals("customTag", tag.toString());
    }

    @Test
    public void build_invalidEnumValue_throwsIllegalArgumentException() {
        // Test invalid difficulty levels
        assertThrows(IllegalArgumentException.class, () -> tagBuilder.build("difficulty_easy"));
        assertThrows(IllegalArgumentException.class, () -> tagBuilder.build("difficulty_hard"));

        // Test invalid work-life balance levels
        assertThrows(IllegalArgumentException.class, () -> tagBuilder.build("wlb_extreme"));

        // Test invalid period values
        assertThrows(IllegalArgumentException.class, () -> tagBuilder.build("period_Autumn"));

        // Test invalid salary levels
        assertThrows(IllegalArgumentException.class, () -> tagBuilder.build("salary_poor"));
    }


    @Test
    public void build_nonAlphanumericInput_throwsIllegalArgumentException() {
        // Test invalid inputs with special characters other than '_'
        assertThrows(IllegalArgumentException.class, () -> tagBuilder.build("wlb@MEDIUM"));
        assertThrows(IllegalArgumentException.class, () -> tagBuilder.build("salary#HIGH"));
        assertThrows(IllegalArgumentException.class, () -> tagBuilder.build("period!Summer"));
        assertThrows(IllegalArgumentException.class, () -> tagBuilder.build("difficulty$LOW"));
    }
}
