package helperbot.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import helperbot.task.Task;

/**
 * Test <code>Ui</code>.
 */
class ResponseTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Response response;

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
        String output = response.getGreetMessage();
        String expectedOutput = "Hello! I'm HelperBot.\nWhat can I do for you?";
        assertEquals(expectedOutput, output);
    }

    @Test
    void showMarked_validTask_correctOutput() {
        Task mockTask = new Task("test task") {
            @Override
            public String toString() {
                return "[T][X] test task";
            }
        };
        String output = response.getMarkCommandResponse(true, new String[] {"1"},
                new String[] {mockTask.toString()});
        String expectedOutput = "Nice! I have marked HelperBot task 1 as done!\n\t[T][X] test task";
        assertEquals(expectedOutput, output);
    }

    @Test
    void showUnmarked_validTask_correctOutput() {
        Task mockTask = new Task("another test task") {
            @Override
            public String toString() {
                return "[T][ ] another test task";
            }
        };
        String output = response.getMarkCommandResponse(false, new String[] {"2"},
                new String[] {mockTask.toString()});
        String expectedOutput = "Nice! I have marked HelperBot task 2 as not done yet!\n\t[T][ ] another test task";
        assertEquals(expectedOutput, output);
    }

    @Test
    void showError_validMessage_correctOutput() {
        String errorMessage = "Invalid command entered.";
        String output = response.getErrorMessage(errorMessage);
        String expectedOutput = "Error!\n" + errorMessage;
        assertEquals(expectedOutput, output);
    }

    @Test
    void exit_noError_correctOutput() {
        String output = response.getExitMessage();
        String expectedOutput = "Bye. Hope to see you again soon!";
        assertEquals(expectedOutput, output);
    }

    @Test
    void exitWithError_validMessage_correctOutput() {
        String errorMessage = "File could not be loaded.";
        String output = response.getExitErrorMessage(errorMessage);
        String expectedOutput = errorMessage + "\n"
                + "Bye. Hope to see you again soon!";
        assertEquals(expectedOutput, output);
    }
}
