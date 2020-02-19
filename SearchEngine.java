import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)	
	public MyWebGraph internet;
	public XmlParser parser;

	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
	}
	
	/* 
	 * This does a graph traversal of the web, starting at the given url.
	 * For each new page seen, it updates the wordIndex, the web graph,
	 * and the set of visited vertices.
	 * 
	 * 	This method will fit in about 30-50 lines (or less)
	 */
	public void crawlAndIndex(String url) throws Exception {
		// TODO : Add code here
		
		if(internet.getVisited(url)) {
			return;
		}
		
		
		
		internet.addVertex(url);
		ArrayList<String> links = parser.getLinks(url);
		
		//Update the word index
		wordIndex.put(url, parser.getContent(url));
		internet.setVisited(url, true);
		
		for(int i = 0; i < links.size(); i++) {
			crawlAndIndex(links.get(i));
			internet.addEdge(url, links.get(i));
		}
		
		
		
	}
	
	
	
	/* 
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex(). 
	 * To implement this method, refer to the algorithm described in the 
	 * assignment pdf. 
	 * 
	 * This method will probably fit in about 30 lines.
	 */
	public void assignPageRanks(double epsilon) {
		// TODO : Add code here
		
		ArrayList<String> vertices = internet.getVertices();
		
		ArrayList<Double> ranks;
		
		for(String v: vertices) {
			internet.setPageRank(v, 1.0);
			
		}
		
		boolean complete = false;
		
		while(complete == false) {
			
			complete = true;
			ranks = computeRanks(vertices);
			
			for(int i = 0; i < vertices.size(); i ++) {
				
				if(Math.abs(ranks.get(i) - internet.getPageRank(vertices.get(i))) >= epsilon) {
					complete = false;
				}
				
				internet.setPageRank(vertices.get(i), ranks.get(i));
			}
			
		}
		
		
	}

	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph 
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls. 
	 * Note that the double in the output list is matched to the url in the input list using 
	 * their position in the list.
	 */
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		// TODO : Add code here
		
		ArrayList<Double> ranks = new ArrayList<Double>();
		
		ArrayList<String> edgesInto;
		
		double x = 0.0;
		
		for(int i = 0; i < vertices.size(); i ++) {
			
			edgesInto = internet.getEdgesInto(vertices.get(i));
			
			x = 0.0;
			
			
			for(String edges: edgesInto) {
				
				x += internet.getPageRank(edges) / (double) internet.getOutDegree(edges);
			}
			
			ranks.add(0.5 + 0.5 * x);
		}
		
		return ranks;
	}

	
	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 * 
	 * This method should take about 25 lines of code.
	 */
	public ArrayList<String> getResults(String query) {
		
		// TODO: Add code here
		
		ArrayList<String> vertices = internet.getVertices();
		HashMap<String, Double> urls = new HashMap<String, Double>();
		
		for(String v: vertices) {
			for(String s: wordIndex.get(v)) {
				
				if( s.equalsIgnoreCase(query) ) {
					urls.put(v, internet.getPageRank(v));
					break;
				}
				
			}
			
		}
		
		return Sorting.fastSort(urls);
		
		
	}
}
