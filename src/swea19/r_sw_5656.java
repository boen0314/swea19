package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Brick {
	int number;
	int h, w;

	public Brick(int number, int h, int w) {
		super();
		this.number = number;
		this.h = h;
		this.w = w;
	}
}

// 스스로 풀었지만, 다시 한번 복습하면 좋을 문제 (배열 복사, 핵심 부분, 구현 능력)
// 코드 훑으면서 이런식으로 풀었구나 정도만 복습해도 될듯
public class r_sw_5656 {

	static int T;
	static int N, W, H; // H 세로, W 가로
	static int[][] matrix;

	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { 1, 0, -1, 0 };

	static int result;

	public static void Game_DFS(int cnt) {
		if (cnt == N) {
			result = Math.min(result, Count());
			return;
		}

		int[][] tmp = new int[H][W]; // 임시 배열 (백트래킹 위해)
		// 2차원 배열 복제 (현재 상태 저장)
		for (int h = 0; h < H; h++) {
			tmp[h] = matrix[h].clone();
		}

		for (int m = 0; m < W; m++) { // 구슬의 범위
			for (int h = 0; h < H; h++) {
				// 벽돌 충돌
				if (matrix[h][m] != 0) {
					BFS(matrix[h][m], h, m); // 벽돌 뽀개기
					Puyo(); // 뿌요(벽돌 내리기)
					break; // 빼먹으면 안됨
				}
			}
			// 다음 구슬
			Game_DFS(cnt + 1);
			// 백트래킹
			for (int h = 0; h < H; h++) {
				matrix[h] = tmp[h].clone();
			}
		}

	}

	public static void BFS(int num, int h, int w) {
		Queue<Brick> q = new LinkedList<>();
		q.add(new Brick(num, h, w));

		boolean[][] visited = new boolean[H][W];
		visited[h][w] = true;
		matrix[h][w] = 0; // 처음 벽돌 뽀개짐

		while (!q.isEmpty()) {
			Brick b = q.poll();

			for (int i = 0; i < 4; i++) {
				// 이 부분이 핵심!♡♡♡♡♡
				for (int k = 1; k <= (b.number - 1); k++) {
					int nh = b.h + (dx[i] * k);
					int nw = b.w + (dy[i] * k);

					if (nh < 0 || nw < 0 || nh >= H || nw >= W)
						continue;

					if (matrix[nh][nw] == 0 || visited[nh][nw])
						continue;

					visited[nh][nw] = true;
					q.add(new Brick(matrix[nh][nw], nh, nw));
					matrix[nh][nw] = 0; // 위 코드랑 순서 바뀌면 안됨
				}
			}
		}
	}

	// 벽돌 아래로 내리기
	public static void Puyo() {
		for (int w = 0; w < W; w++) {
			for (int h = H - 1; h >= 1; h--) {
				if (matrix[h][w] == 0 && matrix[h - 1][w] != 0) {
					matrix[h][w] = matrix[h - 1][w];
					matrix[h - 1][w] = 0;
					h = H;
				}
			}
		}
	}

	// 남은 벽돌 카운트
	public static int Count() {
		int count = 0;
		for (int h = 0; h < H; h++) {
			for (int w = 0; w < W; w++) {
				if (matrix[h][w] != 0) {
					count++;
				}
			}
		}
		return count;
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());

			matrix = new int[H][W];

			for (int i = 0; i < H; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < W; j++) {
					matrix[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			result = Integer.MAX_VALUE;
			Game_DFS(0); // 구슬 떨어뜨림
			System.out.println("#" + tc + " " + result);
		}

		br.close();
	}
}
