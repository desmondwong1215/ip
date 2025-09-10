package helperbot.ui;

import helperbot.task.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test <code>Ui</code>.
 */
class ResponseTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Response response;
    private static final String LINE = "____________________________________________________________";

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        response = new Response();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void greet_validOutput_success() {
        response.getGreetMessage();
        String expectedOutput = LINE + "\n"
                + "Hello! I'm HelperBot.\nWhat can I do for you?\n"
                + LINE + "\n";
        assertEquals(expectedOutput, outputStream.toString().replace("\r\n", "\n"));
    }

    @Test
    void showMarked_validTask_correctOutput() {
        Task mockTask = new Task("test task") {
            @Override
            public String toString() {
                return "[T][X] test task";
            }
        };
        response.getMarkCommandResponse(new String[] {"0"}, new String[] {mockTask.toString()});
        String expectedOutput = LINE + "\n"
                + "Nice! I have marked HelperBot task 1 as done!\n\t[T][X] test task\n"
                + LINE + "\n";
        assertEquals(expectedOutput, outputStream.toString().replace("\r\n", "\n"));
    }

    @Test
    void showUnmarked_validTask_correctOutput() {
        Task mockTask = new Task("another test task") {
            @Override
            public String toString() {
                return "[T][ ] another test task";
            }
        };
        response.getUnmarkCommandResponse(new String[] {"1"}, new String[] {mockTask.toString()});
        String expectedOutput = LINE + "\n"
                + "Nice! I have marked HelperBot task 2 as not done yet!\n\t[T][ ] another test task\n"
                + LINE + "\n";
        assertEquals(expectedOutput, outputStream.toString().replace("\r\n", "\n"));
    }

    @Test
    void showError_validMessage_correctOutput() {
        String errorMessage = "Invalid command entered.";
        response.getErrorMessage(errorMessage);
        String expectedOutput = LINE + "\n"
                + "Error!\n"
                + errorMessage + "\n"
                + LINE + "\n";
        assertEquals(expectedOutput, outputStream.toString().replace("\r\n", "\n"));
    }

    @Test
    void exit_noError_correctOutput() {
        response.getExitMessage();
        String expectedOutput = LINE + "\n"
                + "Bye. Hope to see you again soon!\n"
                + LINE + "\n";
        assertEquals(expectedOutput, outputStream.toString().replace("\r\n", "\n"));
    }

    @Test
    void exitWithError_validMessage_correctOutput() {
        String errorMessage = "File could not be loaded.";
        response.getExitErrorMessage(errorMessage);
        String expectedOutput = LINE + "\n"
                + errorMessage + "\n"
                + "Bye. Hope to see you again soon!\n"
                + LINE + "\n";
        assertEquals(expectedOutput, outputStream.toString().replace("\r\n", "\n"));
    }
}