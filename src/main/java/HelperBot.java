import java.util.Scanner;

public class HelperBot {

    public static final String LINE = "____________________________________________________________";
    public static final String NAME = "HelperBot";

    private final String[] texts = new String[100];
    private int textIndex = 0;

    public void greet(String name) {
        System.out.println(HelperBot.LINE);
        System.out.printf("Hello! I'm %s.\n", name);
        System.out.println("What can I do for you?");
        System.out.println(HelperBot.LINE);
    }

    public void echo(String message) {

        this.texts[this.textIndex++] = message;

        System.out.println(HelperBot.LINE);
        System.out.println("added: " + message);
        System.out.println(HelperBot.LINE);
    }

    public void exit() {
        System.out.println(HelperBot.LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(HelperBot.LINE);
    }

    public void printTexts() {
        System.out.println(HelperBot.LINE);
        for (int i = 0; i < this.textIndex; i++) {
            System.out.println((i + 1) + ". " + this.texts[i]);
        }
        System.out.println(HelperBot.LINE);
    }

    public void chat() {
        // greet the user
        this.greet(HelperBot.NAME);

        Scanner scanner = new Scanner(System.in);

        // chat with the user, exit when user enter 'bye'
        while (true) {
            System.out.println();
            String message = scanner.nextLine();

            // user want to exit
            if (message.equalsIgnoreCase("bye")) {
                this.exit();
                break;
            } else if (message.equalsIgnoreCase("list")) {
                printTexts();
                continue;
            }

            // repeat the message entered by user
            this.echo(message);
        }
    }

    public static void main(String[] args) {
        HelperBot bot = new HelperBot();
        bot.chat();
    }
}
