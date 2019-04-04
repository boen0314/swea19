package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Cell implements Comparable<Cell> {
	int state; // 비활성화(0), 활성화(1)
	int x, y;
	int k; // K시간
	int life; // 생명력
	int lifeTime; // 남은 생명력

	public Cell(int state, int x, int y, int k, int life, int lifeTime) {
		super();
		this.state = state;
		this.x = x;
		this.y = y;
		this.k = k;
		this.life = life;
		this.lifeTime = lifeTime;
	}

	@Override
	public int compareTo(Cell o) {
		if (this.k < o.k) {
			return -1;
		} else if (this.k > o.k) {
			return 1;
		} else {
			if (this.life > o.life) {
				return -1;
			} else if (this.life < o.life) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}

public class sw_5653 {

	static int T;
	static int N, M, K;
	static int[][] matrix;

	static ArrayList<Cell> list; // 초기 배양 세포 리스트

	static int[] dx = { 0, 1, 0, -1 };
	static int[] dy = { 1, 0, -1, 0 };

	static int result;

	public static void BFS() {
		PriorityQueue<Cell> pq = new PriorityQueue<>();
		pq.addAll(list); // 초기 세포

		while (!pq.isEmpty()) {
			Cell c = pq.poll();
			// 활성화 상태, 생명력 다함 (죽음)
			if (c.state == 1 && c.lifeTime == 0) {
				matrix[c.x][c.y] = -1; // 죽은 세포
				continue;
			}

			// 시간 초과 (죽은 것 먼저 체크하고 큐 삭제되야함) if문 순서도 중요함★★★
			if (c.k == K) {
				continue; // 큐에 넣지 않음 (삭제)
			}

			// 비활성화 상태, 생명력 다하는 순간 활성화 상태 됨(0초 걸림)
			if (c.state == 0 && c.lifeTime == 0) {
				pq.add(new Cell(1, c.x, c.y, c.k, c.life, c.life));
				// 활성화 되는 순간에 번식!
				// (실제로는 다음 시간에 번식-시간을 k+1로 해서 큐에 넣으면 됨)
				// 번식은 1번 밖에 못함. 어차피 번식 한번 하고 나면 더이상 번식 할 자리 없으므로
			}
			// 상태에 상관 없이 생명력이 남았을 때
			else {
				pq.add(new Cell(c.state, c.x, c.y, c.k + 1, c.life, c.lifeTime - 1));
				continue;
			}

			// 다음 시간 번식
			for (int i = 0; i < 4; i++) {
				int nx = c.x + dx[i];
				int ny = c.y + dy[i];

				if (matrix[nx][ny] != 0)
					continue;

				// 범위체크 안해도 됨(용기 무한)
				// 방문여부 체크도 안해도됨. matrix에 이미 표시 되있음

				matrix[nx][ny] = c.life; // 잊으면 안됨(방문여부 체크 안하는 이유)
				pq.add(new Cell(0, nx, ny, c.k + 1, c.life, c.life));
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

			// 번식할 수 있는 최대 크기 (생명력 1 세포는 2시간마다 번식 비활-활성)
			matrix = new int[N + K][M + K];
			list = new ArrayList<>();

			for (int i = K / 2; i < K / 2 + N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = K / 2; j < K / 2 + M; j++) {
					matrix[i][j] = Integer.parseInt(st.nextToken());

					if (matrix[i][j] != 0) {
						list.add(new Cell(0, i, j, 0, matrix[i][j], matrix[i][j]));
					}
				}
			}

			BFS();

			result = 0;
			for (int i = 0; i < N + K; i++) {
				for (int j = 0; j < M + K; j++) {
					// 빈공간이 아니고, 죽은 세포가 아닌 것
					if (matrix[i][j] > 0)
						result++;
				}
			}

			System.out.println("#" + tc + " " + result);
		}
		br.close();
	}

}
