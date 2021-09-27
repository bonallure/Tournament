package tournament;

import java.util.List;
import java.util.Set;

public class BracketImpl_Skeleton<P> extends BracketAbstract<P>
{
	public BracketImpl_Skeleton(List<P> participantMatchups)
	{
		super(participantMatchups);
	}
	
	@Override
	public int getMaxLevel()
	{
		throw new RuntimeException("NOT IMPLEMENTED YET!");
	}

	@Override
	public Set<Set<P>> getGroupings(int level)
	{
		throw new RuntimeException("NOT IMPLEMENTED YET!");
	}

	@Override
	public Set<P> getViableParticipants(Set<P> grouping)
	{
		throw new RuntimeException("NOT IMPLEMENTED YET!");
	}
	
	@Override
	public void setWinCount(P participant, int winCount)
	{
		throw new RuntimeException("NOT IMPLEMENTED YET!");
	}
	
	//Find two groupings a and b at a lower level such that a U b = grouping with a INT b = empty
	private Set<Set<P>> getSubordinateGroupings(Set<P> grouping)
	{
		assert grouping.size() > 1 : "grouping.size() = " + grouping.size() + " <= 1!: grouping = " + grouping;
		throw new RuntimeException("NOT IMPLEMENTED!");
	}
	
	private int getParticipantIndex(P participant)
	{
		throw new RuntimeException("NOT IMPLEMENTED!");
	}
	
	private static int getParentIndex(int childIndex)
	{
		throw new RuntimeException("NOT IMPLEMENTED!");
	}
	
	private Set<P> getGrouping(P member, int level)
	{
		throw new RuntimeException("NOT IMPLEMENTED!");
	}
}
