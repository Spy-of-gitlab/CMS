package competitions;



public class IceSkater extends Competitor {

    private String skateBrand;
    private String coach;

    // Constructors
    public IceSkater(int competitorNumber, String name, String dateOfBirth, String gender, String email, String country, int[] scores, String skateBrand, String coach) {
        super(competitorNumber, name, dateOfBirth, gender, email, country, scores);
        this.skateBrand = skateBrand;
        this.coach = coach;
    }

    public IceSkater(int competitorNumber, String name, String dateOfBirth, String email, String level, int[] scores, String skateBrand, String coach) {
        super(competitorNumber, name, dateOfBirth, email, level, scores);
        this.skateBrand = skateBrand;
        this.coach = coach;
    }

    public IceSkater(int competitorNumber, String name, String dateOfBirth, String gender, String email, String level, String country, int[] scores, String skateBrand, String coach) {
        super(competitorNumber, name, dateOfBirth, gender, email, level, country, scores);
        this.skateBrand = skateBrand;
        this.coach = coach;
    }

    // Getter and setter methods for additional attributes
    public String getSkateBrand() {
        return skateBrand;
    }

    public void setSkateBrand(String skateBrand) {
        this.skateBrand = skateBrand;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    // Additional methods related to ice skating
    public String performTripleAxel() {
        return "Performed a triple axel!";
    }

    @Override
    public String getFullDetails() {
        // Override the getFullDetails method to include additional details
        StringBuilder details = new StringBuilder(super.getFullDetails());
        details.append(String.format("\nSkate Brand: %s", skateBrand));
        details.append(String.format("\nCoach: %s", coach));
        return details.toString();
    }

}
