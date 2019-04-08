package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.StringTokenizer;

class Micro implements Comparable<Micro> {
	int x, y;
	int cnt;
	int dir;

	public Micro(int x, int y, int cnt, int dir) {
		super();
		this.x = x;
		this.y = y;
		this.cnt = cnt;
		this.dir = dir;
	}

	@Override
	public int compareTo(Micro o) {
		if (this.cnt > o.cnt) {
			return -1;
		} else if (this.cnt < o.cnt) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) { // 핵심!♡♡♡♡♡
		Micro g = (Micro) obj;
		return (this.x == g.x && this.y == g.y);
	}
}

// equals 메소드 오버라이드 활용법, 리스트 수정해서 문제 푸는 방법 잘 알아두기
public class rrr_sw_2382 {

	static int T; // 테스트 케이스
	static int N, M, K; // 셀 크기, 격리 시간, 군집 개수

	static LinkedList<Micro> list; // 초기 리스트

	static int[] dx = { 0, -1, 1, 0, 0 }; // x상하좌우
	static int[] dy = { 0, 0, 0, -1, 1 };

	static int result;

	public static void Move() {
		// 리스트 수정만 하면 됨!♡♡♡♡♡
		for (Micro mc : list) {
			int nx = mc.x + dx[mc.dir];
			int ny = mc.y + dy[mc.dir];

			if (nx == 0 || ny == 0 || nx == N - 1 || ny == N - 1) {
				int nd = mc.dir;

				if (mc.dir % 2 == 1) { // 상(1)좌(3)
					nd = mc.dir + 1;
				} else { // 하(2)우(4)
					nd = (mc.dir + 3) % 4;
				}

				mc.x = nx;
				mc.y = ny;
				mc.cnt = mc.cnt / 2;
				mc.dir = nd;
			} else {
				mc.x = nx;
				mc.y = ny;
			}
		}

		// 군집 소멸
		for (Micro mc : list) {
			if (mc.cnt == 0) {
				list.remove(list.indexOf(mc));
			}
		}

	}

	public static void Merge() {
		// 미생물 많은 순서대로 정렬
		Collections.sort(list);

		LinkedList<Micro> tmp = new LinkedList<>();
		// 리스트에 있는 객체 모두 임시 리스트로 옮김
		tmp.addAll(list);
		list.clear();

		for (Micro mc : tmp) {
			// equals 메소드 기준으로 좌표 같은 것이 있는 경우
			if (list.contains(mc)) {
				Micro cur = list.get(list.indexOf(mc));
				cur.cnt += mc.cnt; // 미생물 수 합쳐줌
			}
			// 없는 경우
			else {
				list.add(mc); // 리스트에 넣음
			}
		}

	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		list = new LinkedList<>();

		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			list.clear(); // 리스트 초기화

			for (int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken()); // 세로
				int y = Integer.parseInt(st.nextToken()); // 가로
				int c = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());

				list.add(new Micro(x, y, c, d));
			}

			// M시간 동안 반복
			while (M-- > 0) {
				Move();
				Merge();
			}

			// 미생물 수 모두 더해줌
			for (Micro e : list) {
				result += e.cnt;
			}

			System.out.println("#" + tc + " " + result);
			result = 0; // 초기화
		}

		br.close();
	}
}
