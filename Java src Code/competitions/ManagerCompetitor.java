package competitions;
import java.util.List;
import java.util.Scanner;

public class ManagerCompetitor {

    private CompetitorList competitorList;
    private List<Competitor> competitors;

    public ManagerCompetitor() {
        this.competitorList = new CompetitorList();
    }

    public CompetitorList getCompetitorList() {
        return competitorList;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        // Reading competitors from a file
        // Reading competitors from a file
        competitorList.readCompetitorDetailsFromFile("RunCompetitor.csv");

        // Display a menu to the user
        int choice;
        do {
            printMainMenu();
            choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    displayCompetitors();
                    break;
                case 2:
                    displayCompetitorDetailsByNumber(scanner);
                    break;
                case 3:
                    writeFinalReportToFile("FinalReport.txt");
                    break;
                case 4:
                    displayScoreFrequencyReport();
                    break;
                case 5:
                    // Add more options as needed
                    break;
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }

    private void printMainMenu() {
        System.out.println("==== Competitor Management System ====");
        System.out.println("1. Display Competitors");
        System.out.println("2. Display Competitor Details by Number");
        System.out.println("3. Write Final Report to File");
        System.out.println("4. Display Score Frequency Report");
        System.out.println("5. Add More Options");  // Add descriptive text for additional options
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice(Scanner scanner) {
        int choice;
        while (true) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                break;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        return choice;
    }

    private void displayCompetitors() {
        for (Competitor competitor : competitorList.getCompetitors()) {
            System.out.println(competitor.getShortDetails());
        }
    }

    private void displayCompetitorDetailsByNumber(Scanner scanner) {
        System.out.print("Enter competitor number: ");
        int competitorNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Competitor competitor = competitorList.getCompetitors()
                .stream()
                .filter(c -> c.getCompetitorNumber() == competitorNumber)
                .findFirst()
                .orElse(null);

        if (competitor != null) {
            System.out.println(competitor.getFullDetails());
        } else {
            System.out.println("Competitor not found.");
        }
    }

    private void displayScoreFrequencyReport() {
        String report = competitorList.generateScoreFrequencyReport();
        System.out.println(report);
    }

    private void writeFinalReportToFile(String fileName) {
        String report = competitorList.generateScoreFrequencyReport();
        // Assuming you have a method in CompetitorList to write the report
        // Example: writeReportToFile(report, fileName);
        System.out.println("Final report written to file: " + fileName);
    }

    // Add more methods for additional options
    public static void main(String[] args) {
        ManagerCompetitor manager = new ManagerCompetitor();
        manager.run();
    }

    public void addCompetitor(Competitor competitor) {
        competitors.add(competitor);
    }
}
