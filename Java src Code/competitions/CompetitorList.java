package competitions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompetitorList {

    private ArrayList<Competitor> competitors;

    public CompetitorList() {
        this.competitors = new ArrayList<>();
    }

    public void addCompetitor(Competitor competitor) {
        competitors.add(competitor);
    }

    public ArrayList<Competitor> getCompetitors() {
        return competitors;
    }

    public void readCompetitorDetailsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Competitor competitor = parseCompetitorFromLine(line);
                if (competitor != null) {
                    competitors.add(competitor);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void writeFinalReportToFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write the final report content to the file
            for (Competitor competitor : competitors) {
                writer.write(competitor.getFullDetails() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add other methods as needed
    private Competitor parseCompetitorFromLine(String line) throws ParseException {
        String[] parts = line.split(",");
        int competitorNumber = Integer.parseInt(parts[0].trim());
        String name = parts[1].trim();
        String dateOfBirth = parts[2].trim();
        String email = parts[3].trim();
        // Set country based on the position in the array
        String country = parts[4].trim();  // Assuming country is at index 4
        // Set gender based on the position in the array
        String gender = parts[3].trim();  //  

        // Dynamically read scores based on the length
        int[] scores = new int[parts.length - 6];  // Adjusted for the gender field
        for (int i = 6; i < parts.length; i++) {
            scores[i - 6] = Integer.parseInt(parts[i].trim());
        }

        return new Competitor(competitorNumber, name, dateOfBirth, gender, email, country, scores);
    }

    public String generateScoreFrequencyReport() {
        // Map to store frequency of each score
        Map<Integer, Integer> scoreFrequencyMap = new HashMap<>();

        // Count the occurrences of each score
        for (Competitor competitor : competitors) {
            int[] scores = competitor.getScores();
            for (int score : scores) {
                scoreFrequencyMap.put(score, scoreFrequencyMap.getOrDefault(score, 0) + 1);
            }
        }

        // Build the report
        StringBuilder report = new StringBuilder("Score Frequency Report:\n");

        // Display the frequency for each score
        for (Map.Entry<Integer, Integer> entry : scoreFrequencyMap.entrySet()) {
            report.append("Score ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" times\n");
        }

        return report.toString();
    }

}
