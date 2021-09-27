package tournament;

import java.util.ArrayList;
import java.util.List;

public abstract class BracketAbstract<P> implements Bracket<P>
{
	protected List<P> predictions;
	
	public BracketAbstract(List<P> participantMatchups)
	{
		assert participantMatchups.size() > 0 : "participantMatchups.size() = 0!";
		double logBase2 = (Math.log10(participantMatchups.size())/Math.log10(2));
		boolean participantMatchupsSizeIsAPowerOf2 = (logBase2 == (int)logBase2);
		assert participantMatchupsSizeIsAPowerOf2 : "participantMatchups.size() = " + participantMatchups.size() + " is not a power of 2!";
		
		int nodeCount = participantMatchups.size() + (participantMatchups.size() - 1);
		predictions = new ArrayList<P>(nodeCount);
		for(int i = 0; i < participantMatchups.size() - 1; i++)
		{
			predictions.add(null);
		}
		
		for(int i = 0; i < participantMatchups.size(); i++)
		{
			predictions.add(participantMatchups.get(i));
		}
		assert predictions.size() == nodeCount : "predictions.size() = " + predictions.size() + " <> " + nodeCount + " = nodeCount!";
	}
}
