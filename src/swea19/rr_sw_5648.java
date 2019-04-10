package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Atom {
	int x, y;
	int d;
	int energy;

	public Atom(int x, int y, int d, int energy) {
		super();
		this.x = x;
		this.y = y;
		this.d = d;
		this.energy = energy;
	}
}

// 0.5초 단위로 마주쳐서 충돌하는 경우 있음!♡♡♡♡♡
// 좌표를 2배로 늘려서 계산해야함

// 좌표가 -1000 ~ 1000 밖으로 나가면 원소 소멸
// 원자들의 방향이 변하지 않으므로 -1000 ~ 1000 사이에서 만나지 않는 이상 밖에서 만날 일 없음
// (바깥에서 안쪽으로 들어오는 원소의 존재가 불가능하므로)

// 좌표 양수로 표현하기 위해 +1000 해줌
public class rr_sw_5648 {

	static int T;
	static int N;

	static int[] dx = { 0, 0, -1, 1 }; // 상하좌우 (문제에 주어진대로! 일반적인 좌표와 좀 다름)
	static int[] dy = { 1, -1, 0, 0 }; // y세로, x 가로

	static int[][] matrix;
	static ArrayList<Atom> list; // 초기 원소 리스트

	static int result;

	public static void BFS() {
		Queue<Atom> q = new LinkedList<>();
		q.addAll(list);

		while (!q.isEmpty()) {

			int size = q.size(); // 기존 큐만 검사
			for (int i = 0; i < size; i++) {
				Atom at = q.poll();
				int x = at.x;
				int y = at.y;
				int d = at.d;
				int k = at.energy;

				// 이동 전에 체크! 핵심!♡♡♡♡♡
				// 이전 시간에 폭발한 자리에 첫번째로 와있었던 원자 처리
				// 배열에 이 원자의 에너지보다 더 큰 에너지가 더해져 있다는 것은
				// 다른 원자들이 이후에 도착해서 충돌했다는 뜻!
				// 그러므로 이 원자도 제거해줘야함
				if (matrix[x][y] > k) {
					result += k;
					matrix[x][y] = 0;
					continue;
				}

				// 이동
				int nx = x + dx[d];
				int ny = y + dy[d];

				// 범위 벗어나면 원자 아웃
				if (nx < 0 || ny < 0 || nx > 4000 || ny > 4000) {
					// 빼먹으면 안됨! 이거 안하면 배열 초기화 하는 작업 해야돼(시간 걸려)
					matrix[x][y] = 0;
					continue;
				}

				// 충돌함
				if (matrix[nx][ny] > 0) {
					result += k;
					matrix[nx][ny] += matrix[x][y];
					matrix[x][y] = 0;
				}
				// 충돌 안함
				else {
					matrix[nx][ny] = matrix[x][y];
					matrix[x][y] = 0;
					q.add(new Atom(nx, ny, d, k));
				}
			}
		}

	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		// for문 안에 넣으면 런타임 에러 남ㅠㅠ
		matrix = new int[4001][4001];

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());

			list = new ArrayList<>();

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());
				int k = Integer.parseInt(st.nextToken());

				x = (x + 1000) * 2; // 좌표 양수로 바꿔주고(+1000) 2배로 만들어 줌(0.5초 처리 위해)
				y = (y + 1000) * 2;

				matrix[x][y] = k;
				list.add(new Atom(x, y, d, k));
			}

			result = 0; // 초기화
			BFS();
			System.out.println("#" + tc + " " + result);
		}

		br.close();
	}

}
