package tournament;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BracketofIntegersImpl_Mwamba implements BracketofIntegers
{
	private final int PARTICIPANT_COUNT = 16;
	private List<Integer> ranking;
	
	public BracketofIntegersImpl_Mwamba(List<Integer> ranking)
	{
		assert ranking.size() == PARTICIPANT_COUNT : "ranking.size() = " + ranking.size() + " <> " + PARTICIPANT_COUNT + " PARTICIPANT_COUNT!";
		assert  Collections.min(ranking) == 0 : "Collections.min(ranking) = " + Collections.min(ranking) + " <> 0!";
		assert  Collections.max(ranking) == (PARTICIPANT_COUNT - 1) : "Collections.max(ranking) = " + Collections.max(ranking) + " <> " + (PARTICIPANT_COUNT - 1) + " = (PARTICIPANT_COUNT - 1)";
		
		this.ranking = new ArrayList<Integer>(ranking);
	}
	
	public int getMaxLevel()
	{
		return 4;
	}
	
	public Set<Set<Integer>> getGroupings(int level)
	{
		Set<Set<Integer>> groupings = new HashSet<Set<Integer>>();
		if(level == 0)
		{
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{0})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{1})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{2})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{3})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{4})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{5})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{6})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{7})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{8})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{9})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{10})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{11})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{12})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{13})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{14})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{15})));
		}
		else if(level == 1)
		{
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{0, 1})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{2, 3})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{4, 5})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{6, 7})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{8, 9})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{10, 11})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{12, 13})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{14, 15})));
		}
		else if(level == 2)
		{
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{0, 1, 2, 3})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{4, 5, 6, 7})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{8, 9, 10, 11})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{12, 13, 14, 15})));
		}
		else if(level == 3)
		{
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7})));
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{8, 9, 10, 11, 12, 13, 14, 15})));
		}
		else if(level == 4)
		{
			groupings.add(new HashSet<Integer>(Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})));
		}
		return groupings;
	}
	
	public void setWinCount(Integer participant, int exactWinCount)
	{
		throw new RuntimeException("NOT IMPLEMENTED!");
	}
	
	public static final int NOT_FOUND = -1;
	private static int getSmallestIndexOfMatch(List<Integer> list, int target)
	{
		assert list != null : "list is null!";
		boolean foundMatch = false;
		int i = 0;
		while(!foundMatch && i < list.size())
		{
			foundMatch = (list.get(i) == target);
			if(!foundMatch) i++;
		}
		return (foundMatch ? i : NOT_FOUND);
	}
	
	private static Set<Integer> findTopRankedElements(Set<Integer> s, List<Integer> ranking)
	{
		assert s != null : "s is null!";
		assert s.size() > 0 : "s.size is 0!";
		assert !s.contains(null) : "s contains null!";
		assert ranking != null : "ranking is null!";
		assert (new HashSet<Integer>(ranking)).containsAll(s) : "Not all elements of s are in ranking! : s = " + s + " ranking = " + ranking;
		assert !ranking.contains(null) : "ranking contains null!";
		
		List<Integer> elements = new ArrayList<Integer>(s);
		Set<Integer> topRankedElements = new HashSet<Integer>();
		topRankedElements.add(elements.get(0));
		int topRank = getSmallestIndexOfMatch(ranking, elements.get(0));
		
		for(int i = 0; i < elements.size(); i++)
		{
			int element = elements.get(i);
			int rank = getSmallestIndexOfMatch(ranking, element);
			if(rank <= topRank)
			{
				if(rank < topRank)
				{
					topRankedElements.clear();
					topRank = rank;
				}
				topRankedElements.add(element);
			}
		}
		return topRankedElements;
	}
	
	public String toString()
	{
		int maxLevel = getMaxLevel();
		final String DEFAULT_PARTICIPANT_STRING = "*";
		String maxLevelString = "Max Level = "+maxLevel;
		List<List<String>> participantsList = new ArrayList<List<String>>(maxLevel+1);
		
		int groupingLevel = maxLevel;
		while (groupingLevel >= 0) {
			Set<Set<Integer>> currentGroupings = getGroupings(groupingLevel);
			List<String> viableGroupingParticipants = new ArrayList<String>(currentGroupings.size());
			
			for (Set<Integer> grouping : currentGroupings) {
				 Set<Integer> viableParticipants = getViableParticipants(grouping);
				 boolean isThereAWinner = viableParticipants.size() == 1;
				 String winner = viableParticipants.iterator().next().toString();
				 String viableParticipant = (isThereAWinner ? winner : DEFAULT_PARTICIPANT_STRING);

				 viableGroupingParticipants.add(viableParticipant);
			}
			participantsList.add(groupingLevel, viableGroupingParticipants);
			groupingLevel --;
		}

		String viableParticipants = maxLevelString;
		groupingLevel = maxLevel;
		while (groupingLevel >= 0) {
			Iterator<String> viableParticipantsIterator = participantsList.get(groupingLevel).iterator();
			String levelParticipants = "Level "+groupingLevel+":";
			while (viableParticipantsIterator.hasNext()) {
				levelParticipants += " "+viableParticipantsIterator.next();
			}
			viableParticipants += "\n"+levelParticipants;
		}
		return viableParticipants;	}

	public Set<Integer> getViableParticipants(Set<Integer> grouping)
	{
		return findTopRankedElements(grouping, ranking);
	}
	
	public boolean equals(Object obj)
	{		
		boolean establishedEquality = false;
		if(obj != null && BracketofIntegers.class.isAssignableFrom(obj.getClass()))
		{
			BracketofIntegers otherBracket = (BracketofIntegers)obj;
			if(getMaxLevel() == otherBracket.getMaxLevel())
			{
				int level = 0;
				boolean sameGroupings = true;
				boolean sameViableParticipants = true;
				while (level <= getMaxLevel()) {
					Set<Set<Integer>> groupings = getGroupings(level);
					Set<Set<Integer>> otherBracketGroupings = otherBracket.getGroupings(level);
					if (! groupings.equals(otherBracketGroupings)) {
						sameGroupings = false;
						break;
					}
					for (Set<Integer> grouping : groupings) {
						Set<Integer> viableParticipants = getViableParticipants(grouping);
						if (! viableParticipants.equals(otherBracket.getViableParticipants(grouping))) {
							sameViableParticipants = false;
							break;
						}
					}
					
					level ++;
				}
				establishedEquality = sameGroupings && sameViableParticipants;
			}
		}
		return establishedEquality;
	}
	
	public static void main(String[] args)
	{
		final String NO_WINNER = "*";
		List<Integer> ranking = Arrays.asList(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
		List<Integer> primesRuleRanking = Arrays.asList(new Integer[]{2, 3, 5, 7, 11, 13, 0, 1, 4, 6, 8, 9, 10, 12, 14, 15});
		List<Integer> squaresRanking = Arrays.asList(new Integer[]{0, 1, 4, 9, 2, 3, 5, 6, 7, 8, 10, 11, 12, 13, 14, 15});
		BracketofIntegers b = new BracketofIntegersImpl_Mwamba(ranking);
		
		System.out.println("MAX LEVEL = " + b.getMaxLevel());
	}

	@Override
	public void setWinCount(int participant, int exactWinCount) {
		// TODO Auto-generated method stub
		
	}
}
