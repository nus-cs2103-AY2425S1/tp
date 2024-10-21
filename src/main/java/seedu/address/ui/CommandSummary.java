package seedu.address.ui;

/**
 * A UI component that displays command summary.
 */
public class CommandSummary {
    private final String action;
    private final String format;

    /**
     * Creates a command summary to display.
     */
    public CommandSummary(String action, String format) {
        assert action != null;
        assert format != null;
        this.action = action;
        this.format = format;
    }

    public String getAction() {
        return action;
    }

    public String getFormat() {
        return format;
    }
}
