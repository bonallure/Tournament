package tournament;

import java.util.Set;

public interface BracketofIntegers {
	
	public int getMaxLevel();
	public Set<Set<Integer>> getGroupings(int level);
	public Set<Integer> getViableParticipants(Set<Integer> grouping); 
	public void setWinCount(int participant,int exactWinCount);
	public boolean equals(Object obj);
}
