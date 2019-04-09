package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Pipe {
	int x, y;
	int pipe;
	int time;

	public Pipe(int x, int y, int pipe, int time) {
		super();
		this.x = x;
		this.y = y;
		this.pipe = pipe;
		this.time = time;
	}
}

// DFS로 풀면 시간초과 남. BFS로 풀어야함
public class r_sw_1953 {

	static int T;
	static int N, M, R, C, L; // 세로가로 크기, 맨홀 세로가로 위치, 소요 시간
	static int[][] matrix;
	static boolean[][] visited;

	static int[] dx = { -1, 1, 0, 0 }; // 상하좌우
	static int[] dy = { 0, 0, -1, 1 };

	static int count;

	public static void BFS(int x, int y) {
		Queue<Pipe> q = new LinkedList<>();
		q.add(new Pipe(x, y, matrix[x][y], 1));
		visited[x][y] = true;

		while (!q.isEmpty()) {
			Pipe p = q.poll();

			if (p.time == L) // 시간 초과
				continue;

			// 핵심!
			for (int i = 0; i < 4; i++) {
				int nx = p.x + dx[i];
				int ny = p.y + dy[i];

				if (nx < 0 || ny < 0 || nx >= N || ny >= M)
					continue;

				if (visited[nx][ny])
					continue;

				int np = matrix[nx][ny];

				if (i == 0) { // 위로 움직일 경우는
					// 현재 터널이 위쪽으로 갈 수 있으며
					if (p.pipe == 1 || p.pipe == 2 || p.pipe == 4 || p.pipe == 7) {
						// 다음 터널이 아래쪽을 갈 수 있을 때
						if (np == 1 || np == 2 || np == 5 || np == 6) {
							visited[nx][ny] = true;
							q.add(new Pipe(nx, ny, np, p.time + 1));
						}
					}
				}
				// 하
				else if (i == 1) {
					if (p.pipe == 1 || p.pipe == 2 || p.pipe == 5 || p.pipe == 6) {
						if (np == 1 || np == 2 || np == 4 || np == 7) {
							visited[nx][ny] = true;
							q.add(new Pipe(nx, ny, np, p.time + 1));
						}
					}
				}
				// 좌
				else if (i == 2) {
					if (p.pipe == 1 || p.pipe == 3 || p.pipe == 6 || p.pipe == 7) {
						if (np == 1 || np == 3 || np == 4 || np == 5) {
							visited[nx][ny] = true;
							q.add(new Pipe(nx, ny, np, p.time + 1));
						}
					}
				}
				// 우
				else {
					if (p.pipe == 1 || p.pipe == 3 || p.pipe == 4 || p.pipe == 5) {
						if (np == 1 || np == 3 || np == 6 || np == 7) {
							visited[nx][ny] = true;
							q.add(new Pipe(nx, ny, np, p.time + 1));
						}
					}
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
			R = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			L = Integer.parseInt(st.nextToken());

			matrix = new int[N][M];
			visited = new boolean[N][M];

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < M; j++) {
					matrix[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			BFS(R, C);

			count = 0; // 초기화
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if (visited[i][j])
						count++;
				}
			}

			System.out.println("#" + tc + " " + count);
		}

		br.close();
	}

}
