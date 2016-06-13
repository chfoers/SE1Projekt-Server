package gruppe10.club;

/**
 * Klasse ClubBewertung, um eine Bewertung von 1 bis 5 für den Club abzugeben.
 * [int rating]
 * 
 * @author M.Tork
 */
public class ClubBewertung {

	int rating;

	// Bewertungen unter 1 werden mit 1 gewertet.
	// Bewertungen über 5 werden mit 5 gewertet.
	public ClubBewertung(int rating) {
		if (rating < 1) {
			this.rating = 1;
		} else if (rating > 5) {
			this.rating = 5;
		} else {
			this.rating = rating;
		}
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
