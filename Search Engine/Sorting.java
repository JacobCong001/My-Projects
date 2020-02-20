import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may need it to implement fastSort

public class Sorting {

	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> slowSort (HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

        int N = sortedUrls.size();
        for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) <0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
        }
        return sortedUrls;                    
    }
    
    
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {
    	// ADD YOUR CODE HERE
    	
    	
    	
    	ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());
    	
    	quickSort(sortedUrls, results, 0, sortedUrls.size()-1);
    	
    	return sortedUrls;
    }
    
   public static <K, V extends Comparable> void quickSort(ArrayList<K> list, HashMap<K, V> results, int lIndex, int rIndex) {
	   
	   if(rIndex <= lIndex) {
		   return;
	   }else {
		   
		   int i = placeAndDivide(list, results, lIndex, rIndex);
		   quickSort(list, results, lIndex, i-1);
		   quickSort(list, results, i+1, rIndex);
		   
	   }
	   
   }
   
   public static <K, V extends Comparable> int placeAndDivide(ArrayList<K> list, HashMap<K, V> results, int lIndex, int rIndex) {
	   
	   K temp;
	   
	  
	   
	   K pivot = list.get( (rIndex+lIndex) /2 );
	   int wall = lIndex-1;
	   int pivPos = (rIndex+lIndex) /2;
	   
	   for(int i=lIndex; i<= rIndex; i++) {
		  
		   
		   if(results.get(list.get(i)).compareTo((results.get(pivot)))  > 0) {
			   
			   if(wall+1 == pivPos) {
				   pivPos = i;
			   }
			   
			   wall ++; 
			   
			   temp = list.get(i);
			   list.set(i, list.get(wall));
			   list.set(wall, temp);
		   
		   }
		   
		   }
		  
		   temp = list.get(pivPos);
		   list.set( pivPos, list.get(wall+1));
		   list.set(wall+1, temp);
		   
		   
		   return wall+1;
	   
   }
    

}