/**
 * 
 */
/**
 * @author joona
 *
 */
package fi.joonas.veikkaus.util;

public class Score {

	private int home;
	private int away;
	
	private Score() {}
	
	public Score(int home, int away) {
		super();
		this.home = home;
		this.away = away;
	}
	
	public int getHome() {
		return home;
	}
	
	public void setHome(int home) {
		this.home = home;
	}
	
	public int getAway() {
		return away;
	}
	
	public void setAway(int away) {
		this.away = away;
	}
}