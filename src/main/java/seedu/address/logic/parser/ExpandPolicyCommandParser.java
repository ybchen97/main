package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExpandPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExpandPolicyCommand object
 */
public class ExpandPolicyCommandParser implements Parser<ExpandPolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExpandPersonCommand
     * and returns an ExpandPersonCommand object for execution.
     * @throws seedu.address.logic.parser.exceptions.ParseException if the user input does not conform the expected
     * format
     */
    public ExpandPolicyCommand parse(String args) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ExpandPolicyCommand.MESSAGE_USAGE), pe);
        }

        return new ExpandPolicyCommand(index);
    }
}
