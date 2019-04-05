package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class AP {
	int x, y; // 좌표
	int c; // 범위
	int p; // 충전량
	boolean use;

	public AP(int x, int y, int c, int p, boolean use) {
		super();
		this.x = x;
		this.y = y;
		this.c = c;
		this.p = p;
		this.use = use;
	}
}

class User {
	int x, y;

	public User(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
}

public class rrr_sw_5644 {

	static int T;
	static int M, A; // 총 이동시간, BC의 개수

	static int[][] move; // A와 B의 이동 정보
	static AP[] BC; // 충전소 정보

	static int[] dx = { 0, -1, 0, 1, 0 }; // X상우하좌
	static int[] dy = { 0, 0, 1, 0, -1 };

	static User[] user; // user 현재 좌표 정보

	static int sum, result;

	public static void Move() {
		// 이동 전에 충전
		sum = 0;
		DFS(0, 0);
		result += sum;

		// M번 이동
		for (int i = 0; i < M; i++) {
			for (int u = 0; u < 2; u++) { // 두명의 유저
				user[u].x += dx[move[u][i]];
				user[u].y += dy[move[u][i]];
			}
			sum = 0;
			DFS(0, 0);
			result += sum;
		}
	}

	public static void DFS(int u, int c) {
		if (u == 2) {
			// 저장된 값들 중 가장 큰 충전값
			sum = Math.max(sum, c);
			return;
		}

		// 모든 AP 탐색
		for (int ap = 0; ap < A; ap++) {
			int dist = Math.abs(user[u].x - BC[ap].x) + Math.abs(user[u].y - BC[ap].y);

			// 다른 사용자가 사용하지 않고, AP가 범위 안에 있으면(문제에 주어져 있음)
			if (!BC[ap].use && dist <= BC[ap].c) {
				BC[ap].use = true;
				DFS(u + 1, c + BC[ap].p); // 다음 유저 검색
				BC[ap].use = false; // 백트래킹
			}
		}
		// AP 접속 안하는 경우
		DFS(u + 1, c);
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			M = Integer.parseInt(st.nextToken());
			A = Integer.parseInt(st.nextToken());

			move = new int[2][M];
			BC = new AP[A];
			user = new User[2];

			for (int u = 0; u < 2; u++) {
				st = new StringTokenizer(br.readLine());
				for (int i = 0; i < M; i++) {
					move[u][i] = Integer.parseInt(st.nextToken());
				}
			}

			for (int i = 0; i < A; i++) {
				st = new StringTokenizer(br.readLine());
				// 가로 먼저 입력 (문제에서는 가로세로 순이지만 편의를 위해 세로가로로 바꿈)
				int y = Integer.parseInt(st.nextToken());
				int x = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				int p = Integer.parseInt(st.nextToken());

				BC[i] = new AP(x, y, c, p, false);
			}

			user[0] = new User(1, 1); // 좌표 (1,1)부터 시작
			user[1] = new User(10, 10);

			Move();
		}

	}

}
