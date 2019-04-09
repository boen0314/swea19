package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class Guest implements Comparable<Guest> {
	int cNum, time; // 고객 번호
	int nNum, nTime; // 접수 창구
	int mNum, mTime; // 정비 창구
	int tTime;

	public Guest(int cNum, int time, int nNum, int nTime, int mNum, int mTime, int tTime) {
		super();
		this.cNum = cNum;
		this.time = time;
		this.nNum = nNum;
		this.nTime = nTime;
		this.mNum = mNum;
		this.mTime = mTime;
		this.tTime = tTime;
	}

	@Override
	public int compareTo(Guest o) {
		if (this.tTime < o.tTime) {
			return -1;
		} else if (this.tTime > o.tTime) {
			return 1;
		} else {
			// 접수 창구 번호 더 작을 수록
			if (this.nNum < o.nNum) {
				return -1;
			} else if (this.nNum > o.nNum) {
				return 1;
			} else {
				// 고객 번호 더 작을 수록 (접수 창구 전)
				if (this.cNum < o.cNum) {
					return -1;
				} else if (this.cNum > o.cNum) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}
}

public class xx_sw_2477 {

	static int T;
	static int N, M, K; // 접수 창구 개수, 정비 창구 개수, 방문 고객 수
	static int A, B; // 지갑을 두고 간 손님의 접수 정비 창구 번호

	static int[] a, b, k;

	static int result;

	public static int BFS() {
		PriorityQueue<Guest> queue = new PriorityQueue<>();
		Queue<Guest> queue2 = new LinkedList<>();

		for (int i = 0; i < K; i++)
			queue.add(new Guest(i + 1, 0, 11, 0, 0, 0, 0));

		boolean[] visitedN = new boolean[N];
		boolean[] visitedM = new boolean[M];

		int result = 0;
		while (!queue.isEmpty() || !queue2.isEmpty()) {
			// 각 size
			int size = queue.size();
			int size2 = queue2.size();

			for (int i = 0; i < size2; i++) {
				Guest t = queue2.poll();

				if (t.mTime == 0) {
					boolean flag = false;
					for (int j = 0; j < M; j++) {
						if (visitedM[j])
							continue;
						visitedM[j] = true;

						t.mNum = j;
						t.mTime++;
						flag = true;
						queue2.add(t);
						break;
					}

					if (flag)
						continue;
					queue2.add(t);
					continue;
				}

				if (t.mTime == b[t.mNum]) {
					visitedM[t.mNum] = false;
					if (t.nNum + 1 == A && t.mNum + 1 == B)
						result += t.cNum;
					continue;
				} else {
					t.mTime++;
					queue2.add(t);
					continue;
				}
			}

			for (int i = 0; i < size; i++) {
				Guest t = queue.poll();
				t.tTime++;
				if (t.time != k[t.cNum - 1]) {
					t.time++;
					queue.add(t);
					continue;
				}

				if (t.nTime == 0) {
					boolean flag = false;
					for (int j = 0; j < N; j++) {
						if (visitedN[j])
							continue;
						visitedN[j] = true;
						t.nNum = j;
						t.nTime++;
						flag = true;
						queue.add(t);
						break;
					}

					if (flag)
						continue;
					queue.add(t);
					continue;
				}

				if (t.nTime == a[t.nNum]) {
					// 시간이 다됐다면 queue2로 넣어주기
					visitedN[t.nNum] = false;
					queue2.add(t);
					continue;
				} else {
					t.nTime++;
					queue.add(t);
					continue;
				}
			}
		}

		if (result == 0)
			result = -1;
		return result;
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			A = Integer.parseInt(st.nextToken());
			B = Integer.parseInt(st.nextToken());

			a = new int[N];
			b = new int[M];
			k = new int[K];

			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				a[i] = Integer.parseInt(st.nextToken());
			}

			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < M; i++) {
				b[i] = Integer.parseInt(st.nextToken());
			}

			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < K; i++) {
				k[i] = Integer.parseInt(st.nextToken());
			}

			System.out.println("#" + tc + " " + BFS());
		}
	}

}
