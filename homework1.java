import java.io.BufferedReader;
import java.io.*;
import java.util.*;

public class homework {
	static int lines, nodes;
	String state1, state2, value;
	static String algo_name = "";

	public static void main(String args[])  {

		String line = null;
		String start = null, goal = null, lines1 = null;
		Map<String, List<Node>> graph = new LinkedHashMap<String, List<Node>>();
		ArrayList al = new ArrayList();
		ArrayList<Integer> al1 = new ArrayList<Integer>();
		Set<String> hashset = new HashSet<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("input.txt"), "UTF-8"));

			algo_name = br.readLine();
			start = br.readLine();
			goal = br.readLine();
			lines1 = br.readLine();
			lines = Integer.parseInt(lines1);

			int c = 0;
			line = br.readLine();

			while (line != null && c < lines) {
				String[] temp = line.split(" ");
				hashset.add(temp[0]);
				hashset.add(temp[1]);
				if (graph.containsKey(temp[0])) {
					graph.get(temp[0]).add(new Node(temp[1], Integer.parseInt(temp[2])));
				} else {
					List<Node> arraylist = new ArrayList<Node>();
					arraylist.add(new Node(temp[1], Integer.parseInt(temp[2])));
					graph.put(temp[0], arraylist);
				}
				line = br.readLine();
				c++;
			}

			for (String name : graph.keySet()) {
				String key = name;
				List<Node> list = graph.get(key);
				for (int i = 0; i < list.size(); i++) {
					Node node1 = list.get(i);
					int value = node1.data;
					String node = node1.node;
				}
			}

			String nodes1 = line;
			nodes = Integer.parseInt(nodes1);
			//System.out.println(graph);
			int c1 = 0;
			line = br.readLine();
			
			while (line != null && c1 < nodes) {
				String[] temp = line.split(" ");
				hashset.add(temp[0]);
				al.add(temp[0]);
				al1.add(Integer.parseInt(temp[1]));
				line = br.readLine();
				c++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (algo_name.equals("BFS")) {
			BFS b = new BFS();
			b.init(graph, start, goal, lines, nodes, hashset);
		} else if (algo_name.equals("DFS")) {
			DFS d = new DFS();
			d.init(graph, start, goal, lines, nodes, hashset);
		} else if (algo_name.equals("UCS")) {
			UCS u = new UCS();
			u.init(graph, start, goal, lines, nodes, hashset);
		} else if (algo_name.equals("A*")) {
			a_star a = new a_star();
			a.init(graph, start, goal, lines, nodes, hashset, al1, al);
		}
	}

}

class Node {
	public String node;
	public int data;
	int visited;

	public Node() {

	}

	public Node(String node, int data) {
		this.node = node;
		this.data = data;
	}

	public Node(String node, int data, int visited) {
		this.node = node;
		this.data = data;
		this.visited = 0;
	}
}

class BFS {
	public BFS() {
	}

	public void init(Map<String, List<Node>> graph, String start, String goal, int lines, int nodes, Set hashset) {
		String element;
		int path_cost = 0;
		Stack st = new Stack();
		Queue<String> q = new LinkedList<String>();
		Map<String, String> parent = new HashMap<String, String>();
		Map<String, Integer> visit = new HashMap<String, Integer>();
		int size;
		FileWriter out = null;
		for (Iterator<String> it = hashset.iterator(); it.hasNext();) {
			String a = it.next();
			visit.put(a, 0);
		}
		q.add(start);
		visit.put(start, 1);

		outerloop: while (!q.isEmpty()) {
			element = q.remove();
			if (graph.get(element) == null)
				size = 0;
			else {
				size = graph.get(element).size();
			}
			for (int i = 0; i < size; i++) {
				if ((graph.get(element).get(i).node) != null) {

					if (visit.get(graph.get(element).get(i).node) == 0) {
						q.add(graph.get(element).get(i).node);
						// graph.get(element).get(i).visited=1;
						visit.put((graph.get(element).get(i).node), 1);
						parent.put(graph.get(element).get(i).node, element);
						if (graph.get(element).get(i).node.equals(goal))
							break outerloop;
					}
				}
			}
		}

		try {
			out = new FileWriter("output.txt");
			if (!start.equals(goal)) {
				String current = goal;
				st.add(current);
				do {
					current = parent.get(current);
					st.add(current);
				} while (parent.get(current) != null);
			} else if (goal.equals(start)) {
				st.add(start);
			}
			int l = st.size();
			for (int i = 0; i < l; i++) {
				out.write((String) st.pop() + " " + path_cost);
				out.write("\r\n");
				path_cost++;
			}

			out.close();
		} catch (Exception ex) {

		}
	}
}

class DFS {
	Stack st1 = new Stack();

	public DFS() {

	}

	public void init(Map<String, List<Node>> graph, String start, String goal, int lines, int nodes, Set hashset) {
		String element;
		int path_cost = 0;
		Stack st = new Stack();
		Stack q = new Stack();
		Map<String, String> parent = new HashMap<String, String>();
		Map<String, Integer> visit = new HashMap<String, Integer>();
		int size = 0;
		FileWriter out = null;
		// FileWriter out1=null;
		for (Iterator<String> it = hashset.iterator(); it.hasNext();) {
			String a = it.next();
			visit.put(a, 0);
		}
		st1.add(start);
		visit.put(start, 1);
		try {
			if (goal.equals(start)) {

			}
		} catch (Exception ex) {

		}
		outerloop: while (!st1.isEmpty()) {

			element = (String) st1.pop();
			if (graph.get(element) == null) {
				size = 0;
			} else {
				size = graph.get(element).size();
			}
			for (int i = 0; i < size; i++) {
				if (((graph.get(element).get(i).node) != null) && (visit.get(graph.get(element).get(i).node) == 0)) {

					q.add(graph.get(element).get(i).node);
					graph.get(element).get(i).visited = 1;
					parent.put(graph.get(element).get(i).node, element);
					visit.put(graph.get(element).get(i).node, 1);
					if (graph.get(element).get(i).node.equals(goal))
						break outerloop;
				}
			}
			reverse(q);

		}

		try {
			out = new FileWriter("output.txt");
			if (!goal.equals(start)) {
				String current = goal;
				st.add(current);
				do {
					current = parent.get(current);
					st.add(current);
				} while (parent.get(current) != null);
			} else if (goal.equals(start)) {
				st.add(start);
			}
			int l = st.size();
			for (int i = 0; i < l; i++) {
				out.write(st.pop() + " " + path_cost);
				out.write("\r\n");
				//
				path_cost++;
			}
			out.close();

		} catch (Exception ex) {

		}
	}

	public void reverse(Stack q) {
		while (!q.isEmpty()) {
			String a = (String) q.pop();
			st1.add(a);
		}
	}
}

class UCS {
	public static void main(String args[]) {
	}

	public static final int MAX_VALUE = 999;

	public void init(Map<String, List<Node>> graph, String start, String goal, int lines, int nodes, Set hashset) {
		int no = hashset.size();
		PriorityQueue<Node1> open = new PriorityQueue<Node1>(no, new Node1());
		Queue<String> closed = new LinkedList<String>();
		Map<String, Integer> pathcost = new HashMap<String, Integer>();
		Map<String, String> parent = new HashMap<String, String>();
		int size = 0, counter = 0;

		for (Iterator<String> it = hashset.iterator(); it.hasNext();) {
			String a = it.next();
			pathcost.put(a, Integer.MAX_VALUE);
		}
		pathcost.put(start, 0);
		open.add(new Node1(start, 0));
		Node1 element;

		outerloop: while (!open.isEmpty()) {
			element = open.remove();
			if (element.node1.equals(goal) || (graph.get(element.node1) == null)) {
				size = 0;
			} else {
				size = graph.get(element.node1).size();
			}

			for (int i = 0; i < size; i++) {
				if (((graph.get(element.node1).get(i).node) != null
						&& (!closed.contains(graph.get(element.node1).get(i).node)))) {
					if (!element.node1.equals(goal)) {
						closed.add(element.node1);
					}
					String n = graph.get(element.node1).get(i).node;

					if (pathcost.get(n) > (pathcost.get(element.node1) + (graph.get(element.node1).get(i).data))) {
						pathcost.put(n, (pathcost.get(element.node1) + graph.get(element.node1).get(i).data));

						parent.put(n, element.node1);

					}

					Node1 node1 = new Node1(n, pathcost.get(n));
					if (open.contains(node1)) {
						open.remove(node1);
					}
					node1.set_ts(counter);
					counter++;
					open.add(node1);
				}

			}
		}

		Stack st = new Stack();
		String current = goal;
		st.add(current);

		if (!goal.equals(start)) {
			do {
				current = parent.get(current);
				st.add(current);
			} while (parent.get(current) != null);
		} else if (goal.equals(start)) {
			//st.add(start);
		}

		FileWriter out = null;

		try {
			out = new FileWriter("output.txt");
			int l = st.size();
			for (int i = 0; i < l; i++) {
				String a = (String) st.pop();
				out.write(a + " " + pathcost.get(a));
				out.write("\r\n");
			}
			out.close();
		} catch (Exception ex) {

		}
	}
}

class a_star {
	public static void main(String args[]) {
	}

	public void init(Map<String, List<Node>> graph, String start, String goal, int lines, int nodes, Set hashset,
			ArrayList al1, ArrayList al) {

		int no = hashset.size();
		PriorityQueue<Node1> open = new PriorityQueue<Node1>(no, new Node1());
		Map<String, Integer> pathcost = new HashMap<String, Integer>();
		Queue<String> closed = new LinkedList<String>();
		Map<String, String> parent = new HashMap<String, String>();
		Map<String, Integer> heuristic = new HashMap<String, Integer>();
		int size = 0, counter = 0;

		for (Iterator<String> it = hashset.iterator(); it.hasNext();) {
			String a = it.next();
			pathcost.put(a, Integer.MAX_VALUE);
		}

		for (int i = 0; i < al1.size(); i++) {
			String a = (String) al.get(i);
			pathcost.put(a, Integer.MAX_VALUE);
			heuristic.put(a, (Integer) al1.get(i));
		}
		pathcost.put(start, 0);
		open.add(new Node1(start, 0));
		Node1 element;

		outerloop: while (!open.isEmpty()) {
			element = open.remove();
			if(element.node1.equals(goal))
				break;
			if ((graph.get(element.node1) == null)) {
				size = 0;
			} else {
				size = graph.get(element.node1).size();
			}
			for (int i = 0; i < size; i++) {
				if (((graph.get(element.node1)
						.get(i).node) != null )) {
					if (!element.node1.equals(goal)) {
						closed.add(element.node1);
					}
					String n = graph.get(element.node1).get(i).node;

					if (element.node1.equals(start)) {
						pathcost.put(n, (pathcost.get(element.node1) + graph.get(element.node1).get(i).data
								+ heuristic.get(n)));
						parent.put(n, element.node1);
						Node1 node2 = new Node1(n, pathcost.get(n));
						if(open.contains(node2))
							open.remove(node2);
						node2.set_ts(counter);
						counter++;
						open.add(node2);

					}

					if ((pathcost.get(n) > (pathcost.get(element.node1))  - heuristic.get(element.node1)
							+ (graph.get(element.node1).get(i).data) + heuristic.get(n))
							&& !element.node1.equals(start)) {
						
						//System.out.println(graph.get(element.node1).get(i).data+"\t"+n);
						pathcost.put(n, (pathcost.get(element.node1) + graph.get(element.node1).get(i).data
								+ heuristic.get(n) - heuristic.get(element.node1)));
						parent.put(n, element.node1);
						Node1 node2 = new Node1(n, pathcost.get(n));
						if(open.contains(node2))
							open.remove(node2);
						node2.set_ts(counter);
						counter++;
						open.add(node2);
					}
				}
			}
		}
		Stack st = new Stack();
		String current = goal;
		st.add(current);

		if (!goal.equals(start)) {
			do {
				current = parent.get(current);
				st.add(current);
			} while (parent.get(current) != null);
		} else if (goal.equals(start)) {
			
		}
		heuristic.put(start, 0);
		FileWriter out = null;
		try {
			out = new FileWriter("output.txt");
			int l = st.size();
			for (int i = 0; i < l; i++) {
				String a = (String) st.pop();
				out.write(a + " " + (pathcost.get(a) - heuristic.get(a)));
				out.write("\r\n");
			}
			out.close();
		} catch (Exception ex) {

		}
	}
}

class Node1 implements Comparator<Node1> {
	public String node1;
	public int cost;
	public int pathcost;
	public int timestamp;

	public Node1() {
	}

	public Node1(String node1, int cost) {
		this.node1 = node1;
		this.cost = cost;
		this.timestamp = 0;
	}

	public void set_ts(int timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int compare(Node1 node1, Node1 node2) {
		if (node1.cost < node2.cost)
			return -1;
		if (node1.cost > node2.cost)
			return 1;
		else {
			return (node1.timestamp - node2.timestamp);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node1) {
			Node1 node = (Node1) obj;
			if (this.node1 == node.node1) {
				return true;
			}
		}
		return false;
	}

}
