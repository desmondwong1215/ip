public class HelperBot {

    public static void greet(String name) {
        String line = "____________________________________________________________";
        System.out.println(line);
        System.out.printf("Hello! I'm %s.\n", name);
        System.out.println("What can I do for you?");
        System.out.println(line);
    }

    public static void exit() {
        String line = "____________________________________________________________";
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(line);
    }

    public static void main(String[] args) {
        String name = "HelperBot";
        greet(name);
        exit();
    }
}
