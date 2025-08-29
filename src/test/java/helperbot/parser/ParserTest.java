package helperbot.parser;

import helperbot.command.*;
import helperbot.exception.HelperBotCommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test <code>Parser</code>.
 */
public class ParserTest {

    @Test
    public void parse_validAddCommand_success() {
        try {
            assertInstanceOf(AddCommand.class, Parser.parse("todo read book"));
            assertInstanceOf(AddCommand.class, Parser.parse("deadline return book /by 2025-10-19 12:00"));
            assertInstanceOf(AddCommand.class, Parser.parse(
                    "event project meeting /from 2025-10-19 12:00 /to 2025-10-19 14:00"));
        } catch (HelperBotCommandException e) {
           fail();
        }
    }

    @Test
    public void parse_validCheckCommand_success() {
        try {
            assertInstanceOf(CheckCommand.class, Parser.parse("Check 2025-10-19"));
        } catch (HelperBotCommandException e) {
            fail();
        }
    }

    @Test
    public void parse_validDeleteCommand_success() {
        try {
            assertInstanceOf(DeleteCommand.class, Parser.parse("delete 8"));
        } catch (HelperBotCommandException e) {
            fail();
        }
    }

    @Test
    public void parse_validExitCommand_success() {
        try {
            assertInstanceOf(ExitCommand.class, Parser.parse("bye"));
        } catch (HelperBotCommandException e) {
            fail();
        }
    }

    @Test
    public void parse_validListCommand_success() {
        try {
            assertInstanceOf(ListCommand.class, Parser.parse("list"));
        } catch (HelperBotCommandException e) {
            fail();
        }
    }

    @Test
    public void parse_validMarkCommand_success() {
        try {
            assertInstanceOf(MarkCommand.class, Parser.parse("mark 8"));
            assertInstanceOf(MarkCommand.class, Parser.parse("unmark 8"));
        } catch (HelperBotCommandException e) {
            fail();
        }
    }

    @Test
    public void parse_invalidCommand_success() {
        try {
            Parser.parse("bala bala blah");
            fail();
        } catch (HelperBotCommandException e) {
            assertEquals("Invalid Command: bala is not found.", e.toString());
        }
    }
}
