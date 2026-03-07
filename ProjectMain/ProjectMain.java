import java.util.*;

public class ProjectMain {

    static ArrayDeque<String> line = new ArrayDeque<>();
    static HashMap<String, Integer> arrivalTime = new HashMap<>();

    static int currentTime = 0;
    static long totalWait = 0;
    static int servedCount = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        printHelp();

        while (true) {

            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.trim().isEmpty()) continue;

            String[] parts = input.split(" ");
            String command = parts[0].toUpperCase();

            try {

                switch (command) {

                    case "HELP":
                        printHelp();
                        break;

                    case "ARRIVE":
                        if (parts.length < 2) {
                            System.out.println("Usage: ARRIVE <name>");
                            break;
                        }
                        arrive(parts[1]);
                        break;

                    case "VIP_ARRIVE":
                        if (parts.length < 2) {
                            System.out.println("Usage: VIP_ARRIVE <name>");
                            break;
                        }
                        vipArrive(parts[1]);
                        break;

                    case "SERVE":
                        serve();
                        break;

                    case "LEAVE":
                        if (parts.length < 2) {
                            System.out.println("Usage: LEAVE <name>");
                            break;
                        }
                        leave(parts[1]);
                        break;

                    case "PEEK":
                        peek();
                        break;

                    case "SIZE":
                        System.out.println("Size: " + line.size());
                        break;

                    case "PRINT":
                        System.out.println("Line (front -> back): " + line);
                        break;

                    case "TICK":
                        if (parts.length < 2) {
                            System.out.println("Usage: TICK <minutes>");
                            break;
                        }
                        tick(Integer.parseInt(parts[1]));
                        break;

                    case "STATS":
                        stats();
                        break;

                    case "EXIT":
                        System.out.println("Goodbye!");
                        return;

                    default:
                        System.out.println("Unknown command. Type HELP.");
                }

            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        }
    }

    static void printHelp() {
        System.out.println("Cafeteria Line Manager — Commands:");
        System.out.println("HELP");
        System.out.println("ARRIVE <name>");
        System.out.println("VIP_ARRIVE <name>");
        System.out.println("SERVE");
        System.out.println("LEAVE <name>");
        System.out.println("PEEK");
        System.out.println("SIZE");
        System.out.println("PRINT");
        System.out.println("TICK <minutes>");
        System.out.println("STATS");
        System.out.println("EXIT");
    }

    static void arrive(String name) {

        if (arrivalTime.containsKey(name)) {
            System.out.println("Name already in system");
            return;
        }

        line.addLast(name);
        arrivalTime.put(name, currentTime);

        System.out.println(name + " arrived at time " + currentTime +
                ". Line size = " + line.size());
    }

    static void vipArrive(String name) {

        if (arrivalTime.containsKey(name)) {
            System.out.println("Name already in system");
            return;
        }

        line.addFirst(name);
        arrivalTime.put(name, currentTime);
System.out.println("VIP " + name + " arrived at front. Line size = " + line.size());
    }

    static void serve() {

        if (line.isEmpty()) {
            System.out.println("No one to serve.");
            return;
        }

        String name = line.removeFirst();

        int arrival = arrivalTime.get(name);
        int wait = currentTime - arrival;

        servedCount++;
        totalWait += wait;

        arrivalTime.remove(name);

        System.out.println("Served: " + name + " (waited " + wait + " min).");
    }

    static void leave(String name) {

        if (!line.removeFirstOccurrence(name)) {
            System.out.println("Not found");
            return;
        }

        arrivalTime.remove(name);

        System.out.println(name + " left the line. Line size = " + line.size());
    }

    static void peek() {

        if (line.isEmpty()) {
            System.out.println("Line is empty.");
            return;
        }

        System.out.println("Next: " + line.peekFirst());
    }

    static void tick(int minutes) {

        if (minutes < 0) {
            System.out.println("Minutes must be >= 0");
            return;
        }

        currentTime += minutes;

        System.out.println("Time advanced by " + minutes +
                " minutes. Current time = " + currentTime);
    }

    static void stats() {

        double avg = servedCount == 0 ? 0 : (double) totalWait / servedCount;

        System.out.printf("Served count = %d, Avg wait = %.2f min.%n",
                servedCount, avg);
    }
}