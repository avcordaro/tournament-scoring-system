package tss;

import java.util.ArrayList;

public class Team implements Comparable<Team> {
	
	private String club;
	private ArrayList<Integer> archerIDs;
	private int totalScore;
	private int totalHits;
	private int totalGolds;
	private int totalXs;
	
	public Team(String c) {
		club = c;
		archerIDs = new ArrayList<Integer>();
	}
	
	public void addArcherID(int id) {
		archerIDs.add(id);
	}
	
	public void incrementTotalScore(int score) {
		totalScore += score;
	}
	
	public void incrementTotalHits(int hits) {
		totalHits += hits;
	}
	
	public void incrementTotalGolds(int golds) {
		totalGolds += golds;
	}
	
	public void incrementTotalXs(int Xs) {
		totalXs += Xs;
	}
	
	public String getClub() {
		return club;
	}
	
	public ArrayList<Integer> getArcherIDs() {
		return archerIDs;
	}
	
	public int getTotalScore() {
		return totalScore;
	}
	
	public int getTotalHits() {
		return totalHits;
	}
	
	public int getTotalGolds() {
		return totalGolds;
	}
	
	public int getTotalXs() {
		return totalXs;
	}

	@Override
	public int compareTo(Team o) {
		int compareScore = o.getTotalScore();
		if(totalScore > compareScore) {
			return -1;
		} else if(totalScore < compareScore) {
			return 1;
		} else {
			int compareHits = o.getTotalHits();
			if(totalHits > compareHits) {
				return -1;
			} else if(totalHits < compareHits) {
				return 1;
			} else {
				int compareGolds = o.getTotalGolds();
				if(totalGolds > compareGolds) {
					return -1;
				} else if(totalGolds < compareGolds) {
					return 1;
				} else {
					int compareXs = o.getTotalXs();
					if(totalXs > compareXs) {
						return -1;
					} else if(totalXs < compareXs) {
						return 1;
					} else {
						return 0;
					}
				}
			}
		}
	}
}
