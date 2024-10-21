package seedu.address.ui.commandpopup;

import java.util.SortedSet;
import java.util.TreeSet;
/**
 * Contains a static method to generate a TreeSet containing all the commands as Strings
 */
public class PopUpCommandsSet {

    /**
     * Generates a TreeSet containing all the commands as Strings
     */
    public static SortedSet<String> commands() {
        TreeSet<String> commandSet = new TreeSet<>();
        commandSet.add("add");
        commandSet.add("help");
        commandSet.add("editgame");
        commandSet.add("edit");
        commandSet.add("delete");
        commandSet.add("find");
        commandSet.add("list");
        commandSet.add("save");
        commandSet.add("load");
        commandSet.add("clear");
        return commandSet;
    }

}
