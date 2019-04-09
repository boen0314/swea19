package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 핵심부분만 알면 되는 문제!♡♡♡♡♡
public class r_sw_1949 {

	static int T;
	static int N, K; // 지도 크기, 최대 공사 가능 깊이
	static int[][] matrix;
	static boolean[][] visited;

	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { 1, 0, -1, 0 };

	static int top, result;

	public static void DFS(int x, int y, int len, boolean cut) {

		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];

			if (nx < 0 || ny < 0 || nx >= N || ny >= N)
				continue;

			// 방문여부 체크 안해주면
			// 들렸던 지점이지만(더 높음) K만큼 깎아서 다시 방문할 가능성 존재
			if (visited[nx][ny])
				continue;

			// 다음 등산로가 현재 높이보다 낮으면
			if (matrix[nx][ny] < matrix[x][y]) {
				visited[nx][ny] = true;
				DFS(nx, ny, len + 1, cut);
				visited[nx][ny] = false;
			}
			// 다음 등산로가 현재 높이보다 높거나 같으면
			else {
				// 아직 산을 한번도 깎지 않았고
				// 최대 K만큼 깎았을 때 현재 높이보다 높이가 낮아지면
				if (!cut && matrix[nx][ny] - K < matrix[x][y]) {
					// 핵심!♡♡♡♡♡
					// 현재 높이보다 1만큼만 낮아져야함♡♡♡♡♡
					int tmp = matrix[nx][ny];
					matrix[nx][ny] = matrix[x][y] - 1;

					visited[nx][ny] = true;
					DFS(nx, ny, len + 1, true);
					visited[nx][ny] = false;

					matrix[nx][ny] = tmp; // 백트래킹
				}
				// 더이상 등산로를 만들 수 없으면
				else {
					result = Math.max(result, len);
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
			K = Integer.parseInt(st.nextToken());

			matrix = new int[N][N];
			visited = new boolean[N][N];

			top = 0; // 초기화
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					matrix[i][j] = Integer.parseInt(st.nextToken());
					top = Math.max(top, matrix[i][j]);
				}
			}

			result = 0; // 초기화
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (matrix[i][j] == top) {
						visited[i][j] = true;
						DFS(i, j, 1, false);
						visited[i][j] = false;
					}
				}
			}

			System.out.println("#" + tc + " " + result);
		}

		br.close();
	}

}
