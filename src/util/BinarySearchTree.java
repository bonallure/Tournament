package util;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<P>{
	public Node<P> root;
	public int depth;
	public int height;
	public List<Node<P>> binarySearchTree;
	
	public BinarySearchTree(List<P> list) {
		assert list.size() > 0 : "list.size() = 0!";
		double logBase2 = (Math.log10(list.size())/Math.log10(2));
		boolean listSizeIsAPowerOf2 = (logBase2 == (int)logBase2);
		assert listSizeIsAPowerOf2 : "list.size() = " + list.size() + " is not a power of 2!";
		
		this.height = (int)logBase2;
		this.depth = this.height - 1;
		//this.root = new Node<P>(list);
		int nodeCount = list.size() + (list.size() - 1);
		this.binarySearchTree = new ArrayList<Node<P>>(nodeCount);
		this.binarySearchTree.add(0, root);
		
		for(int i = 0; i < this.depth; i++) {
			//List<List<TreeNode<P>>> parentLists = this.splitList(root.participants);
		}
	}
	private List<List<Node<P>>> splitList(List<Node<P>> theList){
		List<Node<P>> leftSide = new ArrayList<Node<P>>(theList.size()/2);
		List<Node<P>> rightSide = new ArrayList<Node<P>>(theList.size()/2);
		for(int i = 0; i < theList.size(); i++) {
			if(i / (theList.size()/2) == 0)
				leftSide.add(i, theList.get(i));
			else
				rightSide.add(i % (theList.size()/2), theList.get(i));
		}
		List<List<Node<P>>> splitList = new ArrayList<List<Node<P>>>(2);
		splitList.add(0, leftSide); splitList.add(1, rightSide);
		return splitList;
	}
}
