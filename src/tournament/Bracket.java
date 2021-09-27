package tournament;

import java.util.Set;

//This Bracket concept is not conscious of regions or play-in games.
public interface Bracket<P>
{
	//pre: true
	//part of post: getGroupings(rv).size() == 1
	public int getMaxLevel();
	
	//part of pre: 0 <= level <= getMaxLevel()
	//part of post: rv != null
	//part of post: !rv.contains(null)
	//part of post: rv.contains(s) ==> (s != null)
	//part of post: rv.contains(s) ==> (!s.contains(null))
	//part of post: rv.contains(s) ==> (s.size() == 2^(level))
	//part of post: (rv.contains(s) && rv.contains(t)) ==> (s.equals(t) || (s.removeAll(t).size() == t.removeAll(s).size() == 0))
	//part of post: (rv.contains(s) && level > 0) ==> s.equals(a.addAll(b)) for some a, b in getGroupings(level-1)
	public Set<Set<P>> getGroupings(int level);
	
	//part of pre: getGroupings(level).contains(grouping) for some 0 <= level <= getMaxLevel()
	//part of post: rv != null
	//part of post: rv.size() > 0
	//part of post: grouping.containsAll(rv)
	//part of post: For each participant t:
	//				[(0 < level) 
	//				&& (g in getGroupings(level - 1) ==> !getViableParticipants(g).contains(t))]
	//						==> !rv.contains(t)
	public Set<P> getViableParticipants(Set<P> grouping);
	
	//part of pre: participant != null
	//part of pre: participant is in getGroupings(getMaxLevel()).iterator().next()
	//part of pre: 0 <= winCount
	//part of pre: winCount <= getMaxLevel()
	//part of post: (0 < level <= winCount())) ==>
	//						getViableParticipants(getGrouping(level)).contains(participant)
	//part of post: (getViableParticipants(getGrouping(exactWinCount)).contains(t) && 
	//					(winCount < level <= getMaxLevel())) ==>
	//						!getViableParticipants(getGrouping(level)).contains(t)
	public void setWinCount(P participant, int winCount);
}