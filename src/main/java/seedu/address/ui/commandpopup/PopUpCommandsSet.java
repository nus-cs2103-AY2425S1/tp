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
        commandSet.add("help");
        commandSet.add("list");
        commandSet.add("add");
        commandSet.add("edit");
        commandSet.add("addgame");
        commandSet.add("editgame");
        commandSet.add("deletegame");
        commandSet.add("favgame");
        commandSet.add("unfavgame");
        commandSet.add("find");
        commandSet.add("findtime");
        commandSet.add("delete");
        commandSet.add("clear");
        commandSet.add("undo");
        commandSet.add("save");
        commandSet.add("load");
        commandSet.add("exit");
        return commandSet;
    }

}
