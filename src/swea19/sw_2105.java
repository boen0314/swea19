package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class sw_2105 {

	static int T; // 테스트 케이스
	static int N;
	static int[][] matrix;
	static boolean[] visited;

	static int[] dx = { 1, 1, -1, -1 }; // 좌하우상(대각선으로)
	static int[] dy = { -1, 1, 1, -1 }; // ↙↘↗↖ 순

	static int sx, sy; // 디저트 투어 시작점
	static int result;

	public static void DFS(int x, int y, int dir, int cnt) {
		if (dir == 4)
			return; // 회전 끝

		int nx = x + dx[dir];
		int ny = y + dy[dir];

		if (nx < 0 || ny < 0 || nx >= N || ny >= N)
			return;

		// 다음 지점이, 시작 지점 다시 돌아가는 지점일 경우
		if (nx == sx && ny == sy) {
			result = Math.max(result, cnt);
			return;
		}

		if (visited[matrix[nx][ny]])
			return;

		visited[matrix[nx][ny]] = true;
		DFS(nx, ny, dir, cnt + 1); // 직진
		DFS(nx, ny, dir + 1, cnt + 1); // 회전
		visited[matrix[nx][ny]] = false; // 백트래킹
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());

			matrix = new int[N][N];
			visited = new boolean[101]; // 1~100

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					matrix[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			result = -1; // 초기화
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					sx = i;
					sy = j;
					visited[matrix[i][j]] = true;
					DFS(i, j, 0, 1);
					visited[matrix[i][j]] = false; // 백트래킹
				}
			}

			System.out.println("#" + tc + " " + result);
		}

		br.close();
	}

}
