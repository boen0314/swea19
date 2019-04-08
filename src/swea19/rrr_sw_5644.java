package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class BC {
	int x, y;
	int c; // 충전 범위
	int p; // 처리량
	boolean use; // 현재 다른 유저가 사용 중인지 여부

	public BC(int x, int y, int c, int p, boolean use) {
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

	static int[][] move; // 유저들의 이동 정보
	static BC[] bc;

	static int[] dx = { 0, -1, 0, 1, 0 }; // x상우하좌
	static int[] dy = { 0, 0, 1, 0, -1 };

	static User[] user; // 현재 유저 정보

	static int sum, result;

	public static void Move() {
		// 이동 전에 충전
		sum = 0;
		DFS(0, 0);
		result += sum;

		// M번 이동
		for (int m = 0; m < M; m++) {
			for (int u = 0; u < 2; u++) {
				user[u].x += dx[move[u][m]];
				user[u].y += dy[move[u][m]];
			}
			sum = 0;
			DFS(0, 0);
			result += sum;
		}

	}

	// 핵심!♡♡♡♡♡
	public static void DFS(int u, int p) {
		if (u == 2) {
			// 저장된 값들 중 가장 큰 충전값
			sum = Math.max(sum, p);
			return;
		}

		// 모든 BC 탐색
		for (int ap = 0; ap < A; ap++) {
			int dis = Math.abs(bc[ap].x - user[u].x) + Math.abs(bc[ap].y - user[u].y);

			// 다른 사용자가 사용하지 않고, BC가 범위 안에 있으면(문제에 주어져 있음)
			if (dis <= bc[ap].c && !bc[ap].use) {
				bc[ap].use = true;
				DFS(u + 1, p + bc[ap].p);
				bc[ap].use = false;
			}
		}
		// BC 접속 안하는 경우
		DFS(u + 1, p);
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
			bc = new BC[A];
			user = new User[2];

			// 유저 시작 위치 설정
			user[0] = new User(1, 1);
			user[1] = new User(10, 10);

			for (int u = 0; u < 2; u++) {
				st = new StringTokenizer(br.readLine());
				for (int m = 0; m < M; m++) {
					move[u][m] = Integer.parseInt(st.nextToken());
				}
			}

			for (int a = 0; a < A; a++) {
				st = new StringTokenizer(br.readLine());
				// 가로 먼저 입력 (문제에서는 가로세로 순이지만 편의를 위해 세로가로로 바꿈)
				int y = Integer.parseInt(st.nextToken()); // 가로
				int x = Integer.parseInt(st.nextToken()); // 세로
				int c = Integer.parseInt(st.nextToken());
				int p = Integer.parseInt(st.nextToken());

				bc[a] = new BC(x, y, c, p, false);
			}

			result = 0;
			Move();

			System.out.println("#" + tc + " " + result);
		}
		
		br.close();
	}
}
