import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int numberOfPlants = Integer.parseInt(st.nextToken());
		List<List<Integer>> plants = new LinkedList<List<Integer>>();
		/**
		 * Each element in a group is less or equal to the previous.
		 * 
		 * Thus an initial input of 6,5,8,12,9,3 will result in the following groups:
		 * [6,5], [8], [12,9,3].
		 */
		List<Integer> group = new LinkedList<Integer>();
		st = new StringTokenizer(br.readLine());
		int previous = Integer.parseInt(st.nextToken());
		group.add(previous);
		plants.add(group);

		for (int i = 1; i < numberOfPlants; i++) {
			int current = Integer.parseInt(st.nextToken());
			if (current <= previous) {
				plants.get(plants.size() - 1).add(current);
			} else {
				group = new LinkedList<Integer>();
				group.add(current);
				plants.add(group);
			}
			previous = current;
		}
		System.out.println(minimumDays(plants));
	}

	/**
	 * Finding the minimum days:
	 * 
	 * If there are more than one group in the queue:
	 * 
	 * 1. Excepting the first group, remove the first element of each group.
	 * 
	 * 2. If the last element of each previous group, including the first group, is
	 * greater or equal to the first element of the following group, then merge the
	 * two groups.
	 * 
	 * 3. Repeat steps one and two until the queue contains only one group. The
	 * number of loops that take to go through steps one and two is equal to the
	 * minimum number of days.
	 * 
	 * Example: Initial groups [6,5], [8], [12,9,3].
	 * 
	 * Step One:[6,5],[9,3]. Step Two:[6,5],[9,3] - no conditions for merging.
	 * 
	 * Step One: [6,5], [3]. Step Two: [6,5,3] - conditions for merging.
	 * 
	 * Total minimum days = 2.
	 */
	public static int minimumDays(List<List<Integer>> plants) {

		int minimum = 0;
		List<Integer> current = null;
		List<Integer> previous = null;

		while (plants.size() > 1) {
			minimum++;

			Iterator<List<Integer>> plantsIterator = plants.iterator();
			/**
			 * Excluding the first group from step one.
			 */
			if (plantsIterator.hasNext()) {
				plantsIterator.next();
			}

			while (plantsIterator.hasNext()) {
				current = plantsIterator.next();
				current.remove(0);
				if (current.isEmpty()) {
					plantsIterator.remove();
				}

			}

			plantsIterator = plants.iterator();
			/**
			 * Assigning the first group as the first previous group.
			 */
			if (plantsIterator.hasNext()) {
				previous = plantsIterator.next();
			}

			while (plantsIterator.hasNext()) {
				current = plantsIterator.next();

				if (previous.get(previous.size() - 1) >= current.get(0)) {
					previous.addAll(current);
					plantsIterator.remove();
				} else {
					previous = current;
				}
			}
		}
		return minimum;
	}
}
