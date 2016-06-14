package gruppe10.dao;

import java.util.List;

import javax.ejb.Local;

import gruppe10.musik.Music;

@Local
public interface ClubchampDAOLocal {

	public Music addMusic(String song, String artist);

	public void deleteMusic(int id);

	public void clearMusic();

	public Music findMusic(String song, String artist);

	public List musikListeAusgeben();

}
