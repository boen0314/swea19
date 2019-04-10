package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Guest {
	int num, time; // 고객 번호, 총 시간
	int nNum, nTime; // 접수 창구
	int mNum, mTime; // 정비 창구
	int wait, tK; // 번호표, 시작 시간

	public Guest(int num, int time, int nNum, int nTime, int mNum, int mTime, int wait, int tK) {
		super();
		this.num = num;
		this.time = time;
		this.nNum = nNum;
		this.nTime = nTime;
		this.mNum = mNum;
		this.mTime = mTime;
		this.wait = wait; // 번호표(정비 창구 대기실에 도착한 시간)♡♡♡♡♡
		this.tK = tK;
	}
}

// 우선순위 큐 조건 다르게 넣기!♡♡♡♡♡
// 적절한 구현!♡♡♡♡♡
// 객체(게스트)변수 수정해서 다시 넣기(add)
// 새로운 객체 만들어서 넣으면 코드 길어져서 지저분해짐
public class rrr_sw_2477 {

	static int T;
	static int N, M, K; // 접수 창구 개수, 정비 창구 개수, 방문 고객 수
	static int A, B; // 지갑을 두고 간 손님의 접수 정비 창구 번호 (1부터 시작)

	static int[] a, b;

	static ArrayList<Guest> list; // 초기 손님 리스트

	static int result;

	public static void BFS() {
		PriorityQueue<Guest> pq1 = new PriorityQueue<>(new Comparator<Guest>() {
			@Override
			public int compare(Guest o1, Guest o2) {
				if (o1.time < o2.time) {
					return -1;
				} else if (o1.time > o2.time) {
					return 1;
				} else {
					// 번호가 빠른 손님 부터
					if (o1.num < o2.num) {
						return -1;
					} else if (o1.num > o2.num) {
						return 1;
					} else {
						return 0;
					}
				}
			}
		});

		PriorityQueue<Guest> pq2 = new PriorityQueue<>(new Comparator<Guest>() {
			@Override
			public int compare(Guest o1, Guest o2) {
				if (o1.time < o2.time) {
					return -1;
				} else if (o1.time > o2.time) {
					return 1;
				} else {
					// 먼저 온 손님 부터(번호표 빠른 손님 = 도착 시간 빠른 손님)
					if (o1.wait < o2.wait) {
						return -1;
					} else if (o1.wait > o2.wait) {
						return 1;
					} else {
						// 동시에 온 손님이라면 접수 창구 번호가 빠른 손님 부터
						if (o1.nNum < o2.nNum) {
							return -1;
						} else if (o1.nNum > o2.nNum) {
							return 1;
						} else {
							return 0;
						}
					}
				}
			}
		});

		boolean[] visited_N = new boolean[N + 1]; // 접수 창구
		boolean[] visited_M = new boolean[M + 1]; // 정비 창구

		pq1.addAll(list); // 초기 손님 리스트

		// 접수 창구 먼저
		while (!pq1.isEmpty()) {
			Guest g = pq1.poll();

			// 아직 시작 시간 전이라면
			if (g.tK > g.time) {
				g.time++;
				pq1.add(g);
				continue;
			}

			// 접수 창구 안에 있으면
			if (g.nTime != 0) {
				// 접수 창구 처리 다 끝났으면 정비 창구 대기열로
				// 접수 -> 정비 창구 이동 시간 0초
				if (g.nTime == a[g.nNum]) {
					visited_N[g.nNum] = false;
					g.wait = g.time; // 번호표 줌(번호표=도착한시간)
					pq2.add(g); // 정비 창구 대기열
					continue;
				} else {
					g.nTime++;
					g.time++;
					pq1.add(g);
					continue;
				}
			}
			// 접수 창구 안에 없으면
			else {
				boolean flag = false;
				for (int i = 1; i <= N; i++) {
					// 빈 창구가 있으면
					if (!visited_N[i]) {
						visited_N[i] = true;
						g.nNum = i;
						g.nTime++;
						g.time++;
						pq1.add(g);
						flag = true;
						break;
					}
				}
				// 창구가 비어있지 않으면
				if (!flag) {
					g.time++;
					pq1.add(g);
				}
			}
		}

		// 정비 창구
		while (!pq2.isEmpty()) {
			Guest g = pq2.poll();

			// 정비 창구 안에 있으면
			if (g.mTime != 0) {
				// 정비 창구 처리 다 끝났으면
				if (g.mTime == b[g.mNum]) {
					visited_M[g.mNum] = false;
					if (g.nNum == A && g.mNum == B) {
						result += g.num;
					}
					continue;
				} else {
					g.mTime++;
					g.time++;
					pq2.add(g);
					continue;
				}
			}
			// 정비 창구 안에 없으면
			else {
				boolean flag = false;
				for (int i = 1; i <= M; i++) {
					// 빈 창구가 있으면
					if (!visited_M[i]) {
						visited_M[i] = true;
						g.mNum = i;
						g.mTime++;
						g.time++;
						pq2.add(g);
						flag = true;
						break;
					}
				}
				// 창구가 비어있지 않으면
				if (!flag) {
					g.time++;
					pq2.add(g);
				}
			}
		}

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

			a = new int[N + 1];
			b = new int[M + 1];
			list = new ArrayList<>();

			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= N; i++) {
				a[i] = Integer.parseInt(st.nextToken());
			}

			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= M; i++) {
				b[i] = Integer.parseInt(st.nextToken());
			}

			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= K; i++) {
				int k = Integer.parseInt(st.nextToken());
				list.add(new Guest(i, 0, 0, 0, 0, 0, 0, k));
			}

			result = 0;
			BFS();
			if (result == 0) {
				result = -1;
			}
			System.out.println("#" + tc + " " + result);
		}

		br.close();
	}
}
