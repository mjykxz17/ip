import java.util.Scanner;

public class Yoyo {

    public static void main(String[] args) {
        String logo = """
 ___                    __   __                
|_ _|   __ _ _ __ ___   \\ \\ / /__  _   _  ___  
 | |   / _` | '_ ` _ \\   \\ V / _ \\| | | |/ _ \\ 
 | |  | (_| | | | | | |   | | (_) | |_| | (_) |
|___|  \\__,_|_| |_| |_|   |_|\\___/ \\__, |\\___/ 
                                   |___/       
                """;

        System.out.println("Hello from\n" + logo);
        System.out.println("WHAT IS UP, my g");

        Scanner sc = new Scanner(System.in);
        String input;

        while (true) {
            input = sc.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon.");
                break; // exit when user types bye
            }
            System.out.println(input); // echo back the input
        }

        sc.close();
    }
}
