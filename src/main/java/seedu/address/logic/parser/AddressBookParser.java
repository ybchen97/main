package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_MERGE_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.commands.AssignPolicyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePolicyCommand;
import seedu.address.logic.commands.DoNotMergeCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListPeopleCommand;
import seedu.address.logic.commands.ListPolicyCommand;
import seedu.address.logic.commands.MergeCommand;
import seedu.address.logic.commands.MergeConfirmedCommand;
import seedu.address.logic.commands.MergeRejectedCommand;
import seedu.address.logic.commands.MergeStopCommand;
import seedu.address.logic.commands.UnassignPolicyCommand;
import seedu.address.logic.parser.exceptions.MergeParseException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private boolean isMerging = false;
    private MergeCommand currentMergeCommand;

    /**
     * Parses user input into command for execution. Calls the parseCommand(String, boolean) where the boolean's
     * default is false.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        return parseCommand(userInput, false);
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @param isSystemInput whether the command was invoked by the user or the system
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, boolean isSystemInput) throws ParseException {
        if (isMerging) {
            return parseMerge(userInput);
        } else {
            final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
            if (!matcher.matches()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }

            final String commandWord = matcher.group("commandWord");
            final String arguments = matcher.group("arguments");
            switch (commandWord) {

            case AddCommand.COMMAND_WORD:
                return new AddCommandParser().parse(arguments);

            case AddPolicyCommand.COMMAND_WORD:
                return new AddPolicyCommandParser().parse(arguments);

            case EditCommand.COMMAND_WORD:
                return new EditCommandParser().parse(arguments);

            case EditPolicyCommand.COMMAND_WORD:
                return new EditPolicyCommandParser().parse(arguments);

            case DeleteCommand.COMMAND_WORD:
                return new DeleteCommandParser().parse(arguments);

            case DeletePolicyCommand.COMMAND_WORD:
                return new DeletePolicyCommandParser().parse(arguments);

            case AssignPolicyCommand.COMMAND_WORD:
                return new AssignPolicyCommandParser().parse(arguments);


            case UnassignPolicyCommand.COMMAND_WORD:
                return new UnassignPolicyCommandParser().parse(arguments);

            /*
            case AddTagCommand.COMMAND_WORD:
                return new AddTagCommandParser().parse(arguments);

            case AddPolicyTagCommand.COMMAND_WORD:
                return new AddPolicyTagCommandParser().parse(arguments);

            case DeleteTagCommand.COMMAND_WORD:
                return new DeleteTagCommandParser().parse(arguments);

            case DeletePolicyTagCommand.COMMAND_WORD:
                return new DeletePolicyTagCommandParser().parse(arguments);
            */

            case ClearCommand.COMMAND_WORD:
                return new ClearCommand();

            case FindCommand.COMMAND_WORD:
                return new FindCommandParser().parse(arguments);
            /*
            case FindPolicyCommand.COMMAND_WORD:
                return new FindPolicyCommandParser().parse(arguments);
            */
            case ListPeopleCommand.COMMAND_WORD:
                return new ListPeopleCommand();

            case ListPolicyCommand.COMMAND_WORD:
                return new ListPolicyCommand();

            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();

            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();

            case MergeCommand.COMMAND_WORD:
                if (isSystemInput) {
                    isMerging = true;
                    MergeCommand command = new MergeCommandParser().parse(arguments);
                    currentMergeCommand = command;
                    return command;
                } else {
                    throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
                }

            case DoNotMergeCommand.COMMAND_WORD:
                return new DoNotMergeCommandParser().parse(arguments);

            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }

    /**
     * Parses the merge commands into commands for execution.
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseMerge(String userInput) throws ParseException {
        String commandWord = userInput.trim();
        switch (commandWord) {

        case (MergeConfirmedCommand.COMMAND_WORD):
        case (MergeConfirmedCommand.DEFAULT_COMMAND_WORD):
            MergeConfirmedCommand confirmCommand = new MergeConfirmedCommand(currentMergeCommand);
            if (confirmCommand.isLastMerge()) {
                isMerging = false;
            }
            return confirmCommand;

        case MergeRejectedCommand.COMMAND_WORD:
            MergeRejectedCommand rejectCommand = new MergeRejectedCommand(currentMergeCommand);
            if (rejectCommand.isLastMerge()) {
                isMerging = false;
            }
            return rejectCommand;
        case MergeStopCommand.COMMAND_WORD:
            isMerging = false;
            return new MergeStopCommand(currentMergeCommand);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        default:
            throw new MergeParseException(String.format(MESSAGE_UNKNOWN_MERGE_COMMAND,
                    currentMergeCommand.getNextMergePrompt()));
        }
    }

}
