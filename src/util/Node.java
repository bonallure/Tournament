package util;

import java.util.ArrayList;
import java.util.List;

public class Node<P> {
	private List<P> grouping;
	private List<P> winCount;
	public Node<P> left, right; 

	public Node(List<P> grouping, List<P> winCount, Node<P> left, Node<P> right) {
	    this.grouping = grouping;
	    this.left = left;
	 }
}
