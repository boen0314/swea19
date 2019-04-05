package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Safe {
	int x, y;
	int k; // 핵심! 범위

	public Safe(int x, int y, int k) {
		super();
		this.x = x;
		this.y = y;
		this.k = k;
	}
}

public class r_sw_2117 {

	static int T;
	static int N, M; // 도시의 크기, 각 집들이 지불할 수 있는 비용
	static int[][] matrix;

	static int[] dx = { 1, 0, -1, 0 };
	static int[] dy = { 0, 1, 0, -1 };

	static int result;

	public static void BFS(int x, int y) {
		Queue<Safe> q = new LinkedList<>();
		// k=2 부터 시작, 1은 미리 계산 하고 시작(바로 밑에)
		q.add(new Safe(x, y, 2));

		boolean[][] visited = new boolean[N][N];
		visited[x][y] = true;

		int home = 0;
		int cost = 1; // k=1 일때 운영비용 1
		int profit = 0;

		if (matrix[x][y] == 1)
			home++;

		profit = (home * M) - cost;

		// 손해보지 않는다면
		if (profit >= 0)
			result = Math.max(result, home);

		while (!q.isEmpty()) {
			Safe s = q.poll(); // 처음 s.k = 2

			// 범위 확산
			for (int i = 0; i < 4; i++) {
				int nx = s.x + dx[i];
				int ny = s.y + dy[i];

				if (nx < 0 || ny < 0 || nx >= N || ny >= N)
					continue;

				if (visited[nx][ny])
					continue;

				if (matrix[nx][ny] == 1)
					home++;

				visited[nx][ny] = true;
				q.add(new Safe(nx, ny, s.k + 1));
			}

			cost = s.k * s.k + (s.k - 1) * (s.k - 1); // 처음 s.k = 2
			profit = (home * M) - cost; // 2번째 범위 까지 home 갯수 포함

			// 손해보지 않는다면
			if (profit >= 0)
				result = Math.max(result, home);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		T = Integer.parseInt(st.nextToken());

		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			matrix = new int[N][N];

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					matrix[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					BFS(i, j);
				}
			}

			System.out.println("#" + tc + " " + result);
			result = 0; // 초기화

		}

		br.close();
	}

}
