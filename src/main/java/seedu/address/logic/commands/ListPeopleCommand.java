package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListPeopleCommand extends Command {

    public static final String COMMAND_WORD = "listpeople";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
<<<<<<< HEAD
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, true, false, false, false, false);
=======
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, true,
                false, false);
>>>>>>> 8ac341c0287e67facfc9d3d342b063598885e9d3
    }
}
