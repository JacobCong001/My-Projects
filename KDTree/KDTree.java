//Cong Zhu 260826128
package assignments2019.a3posted;
import java.util.ArrayList;
import java.util.Iterator;
public class KDTree implements Iterable<Datum>{ 

	KDNode 		rootNode;
	int    		k; 
	int			numLeaves;
	boolean		firstNode = true; //whether it's the first node in the tree
	// constructor

	public KDTree(ArrayList<Datum> datalist) throws Exception {

		Datum[]  dataListArray  = new Datum[ datalist.size() ]; 

		if (datalist.size() == 0) {
			throw new Exception("Trying to create a KD tree with no data");
		}
		else
			this.k = datalist.get(0).x.length;

		int ct=0;
		for (Datum d :  datalist) {
			dataListArray[ct] = datalist.get(ct);
			ct++;
		}
		
	//   Construct a KDNode that is the root node of the KDTree.

		rootNode = new KDNode(dataListArray);
	}
	
	//   KDTree methods
	
	public Datum nearestPoint(Datum queryPoint) {
		return rootNode.nearestPointInNode(queryPoint);
	}
	

	public int height() {
		return this.rootNode.height();	
	}

	public int countNodes() {
		return this.rootNode.countNodes();	
	}
	
	public int size() {
		return this.numLeaves;	
	}

	//-------------------  helper methods for KDTree   ------------------------------

	public static long distSquared(Datum d1, Datum d2) {

		long result = 0;
		for (int dim = 0; dim < d1.x.length; dim++) {
			result +=  (d1.x[dim] - d2.x[dim])*((long) (d1.x[dim] - d2.x[dim]));
		}
		// if the Datum coordinate values are large then we can easily exceed the limit of 'int'.
		return result;
	}

	public double meanDepth(){
		int[] sumdepths_numLeaves =  this.rootNode.sumDepths_numLeaves();
		return 1.0 * sumdepths_numLeaves[0] / sumdepths_numLeaves[1];
	}
	
	class KDNode { 

		boolean leaf;
		Datum leafDatum;           //  only stores Datum if this is a leaf
		
		//  the next two variables are only defined if node is not a leaf

		int splitDim;      // the dimension we will split on
		double splitValue;    // datum is in low if value in splitDim <= splitValue, and high if value in splitDim > splitValue  

		KDNode lowChild, highChild;   //  the low and high child of a particular node (null if leaf)
		  //  You may think of them as "left" and "right" instead of "low" and "high", respectively

		KDNode(Datum[] datalist) throws Exception{

			/*
			 *  This method takes in an array of Datum and returns 
			 *  the calling KDNode object as the root of a sub-tree containing  
			 *  the above fields.
			 */

			//   ADD YOUR CODE BELOW HERE			
			
			if(datalist.length == 1) {
				
				leaf = true;
				leafDatum = datalist[0];
				lowChild = null;
				highChild = null;
				numLeaves ++;
				
				return;
			
			}else {
	
				int dim = 0;
				int range = 0;
				int max = 0;
				int min = 0;
				int Max = 0;
				int Min = 0;
				for(int i = 0; i < k; i++) {
					
					max = datalist[0].x[i];
					min = datalist[0].x[i];
					
					for(int j = 0; j < datalist.length; j++) {
						
						if(datalist[j].x[i] >= max) {
							max = datalist[j].x[i];
						}
						if(datalist[j].x[i] <= min) {
							min = datalist[j].x[i];
						}
						
					}
					if(max - min >= range) {
						range = max - min;
						Max = max;
						Min = min;
						dim = i;
					}
				}
				
				if(range == 0) {
					leaf = true;
					leafDatum = datalist[0];
					lowChild = null;
					highChild = null;
					
					numLeaves ++;
					return;
				}	//if greatest range is zero, all points are duplicates
				
				splitDim = dim;
				splitValue = (double)(Max + Min) / 2.0;	//get the split dim and value
				
				
				
				ArrayList<Datum> lList = new ArrayList<Datum>();
				int numL = 0;
				ArrayList<Datum> hList = new ArrayList<Datum>();
				int numH = 0;
			
				for(int i = 0; i < datalist.length; i ++) {
					
					if(datalist[i].x[splitDim] <= splitValue) {
						
							lList.add(datalist[i]);
							numL ++;
						
						
					}else {
						
						hList.add(datalist[i]);
						numH ++;
					
					}
				}	//get the children list without duplicate but with null in the back
					//Don't check duplicates in children Nodes!!!
				
				
				lowChild = new KDNode( lList.toArray(new Datum[numL]) );
				highChild = new KDNode( hList.toArray(new Datum[numH]) );	//create the children nodes
				
				
			}
			
			
			
			
			
			
			//   ADD YOUR CODE ABOVE HERE

		}

		public Datum nearestPointInNode(Datum queryPoint) {
			Datum nearestPoint, nearestPoint_otherSide;
		
			//   ADD YOUR CODE BELOW HERE
			
			if(leaf) {
				return leafDatum;
			}else {
				
				if(queryPoint.x[splitDim] <= splitValue) {
					
					nearestPoint = this.lowChild.nearestPointInNode(queryPoint);
					
					if(KDTree.distSquared(queryPoint, nearestPoint) <= (queryPoint.x[splitDim] - splitValue) * (queryPoint.x[splitDim] - splitValue)) {
						return nearestPoint;
						
					}else {
						nearestPoint_otherSide = this.highChild.nearestPointInNode(queryPoint);
						if( KDTree.distSquared(queryPoint, nearestPoint) <= KDTree.distSquared(queryPoint, nearestPoint_otherSide)  ) {
							return nearestPoint;
						}else {
							return nearestPoint_otherSide;
						}
					}
					
					
						
				}else {
					
                    nearestPoint = this.highChild.nearestPointInNode(queryPoint);
					
					if(KDTree.distSquared(queryPoint, nearestPoint) <= (queryPoint.x[splitDim] - splitValue) * (queryPoint.x[splitDim] - splitValue)) {
						return nearestPoint;
						
					}else {
						nearestPoint_otherSide = this.lowChild.nearestPointInNode(queryPoint);
						if( KDTree.distSquared(queryPoint, nearestPoint) <= KDTree.distSquared(queryPoint, nearestPoint_otherSide)  ) {
							return nearestPoint;
						}else {
							return nearestPoint_otherSide;
						}
					}
					
				}
				
				
				
			}
			
			
			
			
			
			
			//   ADD YOUR CODE ABOVE HERE

		}
		
		// -----------------  KDNode helper methods (might be useful for debugging) -------------------

		public int height() {
			if (this.leaf) 	
				return 0;
			else {
				return 1 + Math.max( this.lowChild.height(), this.highChild.height());
			}
		}

		public int countNodes() {
			if (this.leaf)
				return 1;
			else
				return 1 + this.lowChild.countNodes() + this.highChild.countNodes();
		}
		
		/*  
		 * Returns a 2D array of ints.  The first element is the sum of the depths of leaves
		 * of the subtree rooted at this KDNode.   The second element is the number of leaves
		 * this subtree.    Hence,  I call the variables  sumDepth_size_*  where sumDepth refers
		 * to element 0 and size refers to element 1.
		 */
				
		public int[] sumDepths_numLeaves(){
			int[] sumDepths_numLeaves_low, sumDepths_numLeaves_high;
			int[] return_sumDepths_numLeaves = new int[2];
			
			/*     
			 *  The sum of the depths of the leaves is the sum of the depth of the leaves of the subtrees, 
			 *  plus the number of leaves (size) since each leaf defines a path and the depth of each leaf 
			 *  is one greater than the depth of each leaf in the subtree.
			 */
			
			if (this.leaf) {  // base case
				return_sumDepths_numLeaves[0] = 0;
				return_sumDepths_numLeaves[1] = 1;
			}
			else {
				sumDepths_numLeaves_low  = this.lowChild.sumDepths_numLeaves();
				sumDepths_numLeaves_high = this.highChild.sumDepths_numLeaves();
				return_sumDepths_numLeaves[0] = sumDepths_numLeaves_low[0] + sumDepths_numLeaves_high[0] + sumDepths_numLeaves_low[1] + sumDepths_numLeaves_high[1];
				return_sumDepths_numLeaves[1] = sumDepths_numLeaves_low[1] + sumDepths_numLeaves_high[1];
			}	
			return return_sumDepths_numLeaves;
		}
		
	}

	public Iterator<Datum> iterator() {
		return new KDTreeIterator();
	}
	
	private class KDTreeIterator implements Iterator<Datum> {
		
		//   ADD YOUR CODE BELOW HERE
		
		ArrayList<Datum> list;
		int index = 0;
		
		public KDTreeIterator() {
			
			index = -1;
			list = new ArrayList<Datum>();
			createList(rootNode);
			
		}
		
		public void createList(KDNode node) {	
			
			if(node.leaf) {
				list.add(node.leafDatum);
			}else {
				createList(node.lowChild);
				createList(node.highChild);
			}
				
		}
		
		public boolean hasNext() {
			
			if(index +1  < list.size()) {
				return true;
			}else {
				return false;
			}
			
		}
		
		public Datum next() {
			if(hasNext()) {
				index ++;
				return list.get(index);
			}else {
				return null;
			}
		}
		
		
		
		
		
		
		
		//   ADD YOUR CODE ABOVE HERE

	}

}

