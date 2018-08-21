import java.io.*;
import java.util.*;

public class Connected {
	Map<String, List<String>> graph;
	public Connected(Map<String, List<String>> graph) {
		this.graph = graph;
	}

	public static void main(String args[]) throws Exception {
		if(args.length!=3) {
			System.out.println("Please invoke the command by using the following format:");
			System.out.println("java Connected <filename> <cityname1> <cityname2>");
			return;
		}		
		String fileName = args[0];
		String start = args[1];
		String end = args[2];
	
		List<String> lines = readFile(fileName);
		List<Tuple<String>> edges = stringsToEdges(lines);
		Map<String, List<String>> graph = setupGraph(edges);

		Connected connected = new Connected(graph);
		
		if (connected.BFS(start, end)) {
			System.out.println("yes");
		} else {
			System.out.println("no");
		}
	}

	public static Map<String, List<String>> setupGraph(List<Tuple<String>> edges) {
		Map<String, List<String>> graph = new HashMap<String, List<String>>();
		for (Tuple<String> edge : edges) {
			addEdge(edge.getX(), edge.getY(), graph);
			addEdge(edge.getY(), edge.getX(), graph);
		}
		return graph;
	}

	public static void addEdge(String x, String y, Map<String, List<String>> graph) {
		List<String> nextNodes = null;
		if (graph.containsKey(x)) {
			nextNodes = new ArrayList<String>();
			nextNodes = graph.get(x);
		} else {
			nextNodes = new ArrayList<String>();
		}
		nextNodes.add(y);
		graph.put(x, nextNodes);
	}

	public boolean BFS(String startCity, String endCity) throws Exception {

		startCity = startCity.trim();
		if (startCity.equals("")) {
			throw new IllegalArgumentException("The cityname1 argument should not be an empty string.");
		}
		if (endCity.equals("")) {
			throw new IllegalArgumentException("The cityname2 argument should not be an empty string.");
		}	
		if (!graph.containsKey(startCity)) {
			return false;
		}

		Map<String, String> visited = new HashMap<String, String>();

		//The queue(FIFO) for BFS
		LinkedList<String> queue = new LinkedList<String>();
		
		visited.put(startCity, startCity);
		queue.add(startCity);

		while (queue.size() != 0) {
			String vertex = queue.poll();

			Iterator<String> it = graph.get(vertex).listIterator();
			while (it.hasNext()) {
				String nextVertex = it.next();
				//End the BFS when the end city is reached.
				if (nextVertex.equals(endCity))
					return true;
				if (!visited.containsKey(nextVertex)) {
					visited.put(nextVertex, nextVertex);
					queue.add(nextVertex);
				}
			}
		}
		return false;
	}

	public static List<Tuple<String>> stringsToEdges(List<String> lines) {
		List<Tuple<String>> edges = new ArrayList<Tuple<String>>();
		for (String line : lines) {
			if (line.trim().equals(""))
				continue;
			else {
				String words[] = line.split(",");
				Tuple<String> tuple = new Tuple<String>(words[0].trim(), words[1].trim());
				edges.add(tuple);
			}
		}
		return edges;
	}

	public static List<String> readFile(String fileName) {
		BufferedReader br = null;
		FileReader fr = null;
		List<String> lines = new LinkedList<String>();
		try {
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				lines.add(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();

				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return lines;
	}
}

class Tuple<T> {

	private T x;
	private T y;

	public Tuple(T x, T y) {
		this.x = x;
		this.y = y;
	}

	public T getX() {
		return x;
	}

	public void setX(T x) {
		this.x = x;
	}

	public T getY() {
		return y;
	}

	public void setY(T y) {
		this.y = y;
	}
}
