package tournament;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class BracketImpl_Mwamba<P> extends BracketAbstract<P>
{
	public BracketImpl_Mwamba(List<P> participantMatchups)
	{
		super(participantMatchups);
	}
	
	@Override
	public int getMaxLevel()
	{
		double logBase2NumOfParticipants = (Math.log10(this.getNumOfParticipants())/Math.log10(2));
		int maxLevel = (int) logBase2NumOfParticipants;
		return maxLevel;
	}

	//part of pre: 0 <= level <= getMaxLevel()
	@Override
	public Set<Set<P>> getGroupings(int level)
	{
		boolean isLevelProper = (0 <= level) && (level <= getMaxLevel());
		assert isLevelProper : "The level is out of range.";
		
		int groupingSize = (int) Math.pow(2.0, level);
		Set<Set<P>> groupings = new HashSet<Set<P>>(this.getNumOfParticipants()/groupingSize);
		List<P> participants = this.getAllParticipants();
		Set<P> grouping = new HashSet<P>(groupingSize);

		for(int i = 0; i < participants.size(); i++) {	
			grouping.add(participants.get(i));
			if (i% groupingSize == (groupingSize-1)) {
				groupings.add(grouping);
				grouping = new HashSet<P>(groupingSize);
			}
		}
		return groupings;
	}

	//part of pre: getGroupings(level).contains(grouping) for some 0 <= level <= getMaxLevel()
	@Override
	public Set<P> getViableParticipants(Set<P> grouping)
	{
		if(grouping.size()==1) 
			return grouping;
		double logBase2Size = (Math.log10(grouping.size())/Math.log10(2));
		int level = (int) logBase2Size;
		
		boolean isGroupingValid = false;
		for(int i=0; i <= level; i++) {
			if(getGroupings(i).contains(grouping)) {
				isGroupingValid = true;
				break;
			}
		}
		assert isGroupingValid : "getGroupings() does not contain grouping at any level";
		
		Iterator<P> groupingIterator = grouping.iterator();
		while (groupingIterator.hasNext()) {
			P participant = groupingIterator.next();
			int winCount = this.getWinCount(participant);
			if (winCount >= level) {
				 Set<P> returnSet = new HashSet<P>(1);
				 returnSet.add(participant);
				 return returnSet;
			}
		}
		Set<P> returnSet = new HashSet<P>();
		Iterator<Set<P>> subGroupings = this.getSubordinateGroupings(grouping).iterator();
		while(subGroupings.hasNext()) {
			Set<P> subGrouping = subGroupings.next();
			returnSet.addAll(this.getViableParticipants(subGrouping));
		}
		return returnSet;
	}
	
	//part of pre: participant != null
	//part of pre: participant is in getGroupings(getMaxLevel()).iterator().next()
	//part of pre: 0 <= winCount
	//part of pre: winCount <= getMaxLevel()
	@Override
	public void setWinCount(P participant, int winCount)
	{
		assert participant != null : "participant is null.";
		assert getAllParticipants().contains(participant) : "participant is not in the tournament";
		assert 0 <= getWinCount(participant) && getWinCount(participant) <= getMaxLevel() : "Wins are out of bounds.";
		
		int participantIndex = this.getParticipantIndex(participant);
		
		while(winCount > 0) {
			int parentIndex = getParentIndex(participantIndex);
			super.predictions.set(parentIndex, participant);
			participantIndex = parentIndex;
			winCount -= 1;
		}
	}
	
	private int getWinCount(P participant) {
		int mentions = 0;
		
		for(int i = 0; i < super.predictions.size(); i++) {
			boolean isParticipant = participant.equals(super.predictions.get(i));
			
			if (isParticipant) mentions ++;
		}
		int winCount = mentions - 1;
		
		return winCount;
	}
	
	private List<P> getAllParticipants() {
		List<P> participantsList = new ArrayList<P>(super.predictions.size()/2 + 1);
		for(int i = super.predictions.size()/2; i < super.predictions.size(); i++)
			participantsList.add(super.predictions.get(i));
		return participantsList;
	}
	
	private int getNumOfParticipants() {
		return this.getAllParticipants().size();
	}
	
	//Find two groupings a and b at a lower level such that a U b = grouping with a INT b = empty
	private Set<Set<P>> getSubordinateGroupings(Set<P> grouping)
	{
		assert grouping.size() > 1 : "grouping.size() = " + grouping.size() + " <= 1!: grouping = " + grouping;
		
		P participant = grouping.iterator().next();
		
		int groupingSize = grouping.size();
		double logBase2Size = (Math.log10(groupingSize)/Math.log10(2));
		int groupingLevel = (int) logBase2Size;
		
		int subordinateGroupingLevel = groupingLevel - 1;
		Set<Set<P>> subordinateGroupings = this.getGroupings(subordinateGroupingLevel);
		Iterator<Set<P>> subGroupingsIterator = subordinateGroupings.iterator();
		
		while(subGroupingsIterator.hasNext()) {
			Set<P> tempGrouping = subGroupingsIterator.next();
			boolean isSubGrouping = tempGrouping.contains(participant);
			if (! isSubGrouping) subordinateGroupings.remove(tempGrouping);
		}
		return subordinateGroupings;
	}
	
	private int getParticipantIndex(P participant)
	{
		List<P> participants = this.getAllParticipants();
		assert participants.contains(participant): "This participant is not in the tournament.";
		int numOfParticipants = participants.size();
		int index = participants.indexOf(participant) + numOfParticipants - 1;
		return index;
	}
	
	private static int getParentIndex(int childIndex)
	{
		boolean isEven = childIndex % 2 == 0;
		int childIndexValue = (isEven ? childIndex - 1: childIndex);
		int parentIndex = childIndexValue /2;
		return parentIndex;
	}
	
	private Set<P> getGrouping(P member, int level)
	{
		assert level >= 0 && level <= this.getMaxLevel(): "The level entered is not valid.";
		
		Iterator<Set<P>> groupings = this.getGroupings(level).iterator();
		Set<P> grouping = groupings.next();
		
		while(! grouping.contains(member))
			grouping = groupings.next();
		
		return grouping;
	}
	
	public boolean equals(Object obj)
	{		
		boolean establishedEquality = false;
		if(obj != null && Bracket.class.isAssignableFrom(obj.getClass()))
		{
			Bracket<P> otherBracket = (Bracket<P>)obj;
			if(getMaxLevel() == otherBracket.getMaxLevel())
			{
				int level = 0;
				boolean sameGroupings = true;
				boolean sameViableParticipants = true;
				while (level <= getMaxLevel()) {
					Set<Set<P>> groupings = getGroupings(level);
					Set<Set<P>> otherBracketGroupings = otherBracket.getGroupings(level);
					if (! groupings.equals(otherBracketGroupings)) {
						sameGroupings = false;
						break;
					}
					for (Set<P> grouping : groupings) {
						Set<P> viableParticipants = getViableParticipants(grouping);
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
	
	public String toString(){
		int maxLevel = getMaxLevel();
		final String DEFAULT_PARTICIPANT_STRING = "*";
		String maxLevelString = "Max Level = "+maxLevel;
		List<List<String>> participantsList = new ArrayList<List<String>>(maxLevel+1);
		
		int groupingLevel = maxLevel;
		while (groupingLevel >= 0) {
			Set<Set<P>> currentGroupings = getGroupings(groupingLevel);
			List<String> viableGroupingParticipants = new ArrayList<String>(currentGroupings.size());
			
			for (Set<P> grouping : currentGroupings) {
				 Set<P> viableParticipants = getViableParticipants(grouping);
				 boolean isThereAWinner = viableParticipants.size() == 1;
				 String winner = viableParticipants.iterator().next().toString();
				 String viableParticipant = (isThereAWinner ? winner : DEFAULT_PARTICIPANT_STRING);

				 viableGroupingParticipants.add(viableParticipant);
			}
			participantsList.add(0, viableGroupingParticipants);
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
			groupingLevel --;
		}
		return viableParticipants;	
	}	
}