package competitions;


import java.text.SimpleDateFormat;

public class Competitor {

    private int competitorNumber;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String email;
    private String level;
    private int[] scores;
    private String country;

    
    
    public Competitor(int competitorNumber, String name, String dateOfBirth, String gender, String email, String country, int[] scores) {
        this.competitorNumber = competitorNumber;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.country = country;
        this.scores = scores;
    }

    // Additional Constructor for the case when 'level' is provided separately
    public Competitor(int competitorNumber, String name, String dateOfBirth, String email, String level, int[] scores) {
        this.competitorNumber = competitorNumber;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.level = level;
        this.scores = scores;
    }

    public Competitor(int competitorNumber, String name, String dateOfBirth, String gender, String email, String level, String country, int[] scores) {
        this.competitorNumber = competitorNumber;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.level = level;
        this.country = country;
        this.scores = scores;
    }

    // Getter and setter methods for instance variables
    public int getCompetitorNumber() {
        return competitorNumber;
    }

    public void setCompetitorNumber(int competitorNumber) {
        this.competitorNumber = competitorNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Other methods (to be implemented in later steps)
    public double getOverallScore() {
        // Implement your scoring mechanism here
        // This is a simple average as a placeholder
        double sum = 0.0;
        for (int score : scores) {
            sum += score;
        }
        return sum / scores.length;
    }

    public String getFullDetails() {
        StringBuilder details = new StringBuilder();
        details.append(String.format("Competitor Details:\n"));
        details.append(String.format("Competitor Number: %d\n", competitorNumber));
        details.append(String.format("Name: %s\n", name));
        details.append(String.format("Country: %s\n", getCountry()));
//        details.append(String.format("%s is a %s aged %d and achieved the following scores:\n", name, level, getDateOfBirth()));

        // Display individual scores
        details.append("Scores: ");
        for (int score : scores) {
            details.append(score).append(", ");
        }

        // Remove the trailing comma and space
        if (details.length() > 2) {
            details.setLength(details.length() - 2);
        }

        details.append(String.format("\nOverall Score: %.2f\n", getOverallScore()));

        return details.toString();
    }

    public String getShortDetails() {
        return String.format("CN %d (%s) has overall score %.2f", competitorNumber, getInitials(), getOverallScore());
    }

    public String getCountry() {
        return country;
    }

    private String getInitials() {
        String[] nameParts = name.split(" ");
        StringBuilder initials = new StringBuilder();
        for (String part : nameParts) {
            initials.append(part.charAt(0));
        }
        return initials.toString().toUpperCase();
    }

  public void setScores(double score1, double score2, double score3, double score4) {
    // Convert double scores to integers or store them as doubles, depending on your requirements.
    // For example, you might want to round or cast them to integers:
    this.scores = new int[]{(int) score1, (int) score2, (int) score3, (int) score4};
}
public Competitor(int competitorNumber, String name, String dateOfBirth, String gender, String country) {
    this.competitorNumber = competitorNumber;
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.gender = gender;
    this.country = country;
}


}
