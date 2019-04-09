package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Stair {
	int x, y;
	int k;

	public Stair(int x, int y, int k) {
		super();
		this.x = x;
		this.y = y;
		this.k = k;
	}
}

class Person implements Comparable<Person> {
	int x, y;
	int gate; // 계단 번호
	int down; // 계단 내려간 시간
	int dist; // 계단 입구까지 거리
	int time; // 시간

	public Person(int x, int y, int gate, int down, int dist, int time) {
		super();
		this.x = x;
		this.y = y;
		this.gate = gate;
		this.down = down;
		this.dist = dist;
		this.time = time;
	}

	@Override
	public int compareTo(Person o) {
		if (this.time < o.time) {
			return -1;
		} else if (this.time > o.time) {
			return 1;
		} else {
			// 핵심!♡♡♡♡♡
			// 거리 짧은게 더 대기시간 긺 (먼저 계단 go)
			if (this.dist < o.dist) {
				return -1;
			} else if (this.dist > o.dist) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}

public class rrrr_sw_2383 {

	static int T;
	static int N;
	static int[][] matrix;

	static Stair[] stair;
	static ArrayList<Person> list;

	static boolean[] group;

	static int min;

	public static void DFS(int idx) {
		if (idx == list.size()) {
			BFS();
			return;
		}
		// 1번 그룹
		group[idx] = true;
		DFS(idx + 1);
		group[idx] = false;

		// 2번 그룹
		DFS(idx + 1);
	}

	public static void BFS() {
		PriorityQueue<Person> pq = new PriorityQueue<>();

		for (int i = 0; i < list.size(); i++) {
			Person p = list.get(i);

			if (group[i]) { // 그룹에 따라 분류
				int distance = Math.abs(p.x - stair[0].x) + Math.abs(p.y - stair[0].y);
				pq.add(new Person(p.x, p.y, 0, 0, distance, 0));
			} else {
				int distance = Math.abs(p.x - stair[1].x) + Math.abs(p.y - stair[1].y);
				pq.add(new Person(p.x, p.y, 1, 0, distance, 0));
			}
		}

		int[] dCnt = new int[2];

		int last_time = 0;
		while (!pq.isEmpty()) {
			Person p = pq.poll();
			last_time = p.time;

			// 핵심!♡♡♡♡♡
			// 해당 계단에 아직 도달 안했다면(계단에 도달후 한 번 대기)
			if (p.dist >= p.time) {
				pq.add(new Person(p.x, p.y, p.gate, p.down, p.dist, p.time + 1));
				continue;
			}

			// 계단 내려가는 중
			if (p.down != 0) {
				if (p.down == stair[p.gate].k) { // 계단 다 내려갔으면
					dCnt[p.gate]--;
					continue;
				}
				pq.add(new Person(p.x, p.y, p.gate, p.down + 1, p.dist, p.time + 1));
				continue;
			}

			// 계단 내려가는 중이 아니고
			// 해당 계단이 꽉차있다면 대기
			if (dCnt[p.gate] == 3) {
				pq.add(new Person(p.x, p.y, p.gate, p.down, p.dist, p.time + 1));
			}
			// 계단으로 들어감 (들어가자마자 한칸 내려감)
			else {
				dCnt[p.gate]++;
				pq.add(new Person(p.x, p.y, p.gate, p.down + 1, p.dist, p.time + 1));
			}
		}

		// 핵심!♡♡♡♡♡
		// 가장 마지막 큐 시간
		min = Math.min(min, last_time);
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());

			matrix = new int[N][N];
			stair = new Stair[2]; // 계단 2개

			list = new ArrayList<>(); // 사람 list

			int cnt = 0;
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					matrix[i][j] = Integer.parseInt(st.nextToken());

					// 사람
					if (matrix[i][j] == 1) {
						list.add(new Person(i, j, 0, 0, 0, 0));

					}
					// 계단 입구
					else if (matrix[i][j] > 1) {
						stair[cnt++] = new Stair(i, j, matrix[i][j]);
					}
				}
			}

			// 사람들을 두 그룹으로 나눠줌
			group = new boolean[list.size()];
			min = Integer.MAX_VALUE;
			DFS(0);
			System.out.println("#" + tc + " " + min);
		}

		br.close();
	}

}
