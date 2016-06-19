package gruppe10.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Klasse ClubBewertung, um eine Bewertung von 1 bis 5 für den Club abzugeben.
 * [int rating]
 * 
 * Objekt wird in der DatenBank persistiert.
 * 
 * @author M.Tork und Christian Förster
 * 
 */
@Entity
@Table(name = "ClubBewertung")
public class ClubBewertung {

	@Id
	@GeneratedValue
	private int id;
	private int rating;
	
	public ClubBewertung() {
	}

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
