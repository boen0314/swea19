package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class r_sw_4013 {

	static int T; // 테스트 케이스
	static int K; // 자석을 회전시키는 횟수

	static int[][] magnet;
	static boolean[][] check;
	static boolean[] visited;

	static int result;

	public static void polCheck() {
		if (magnet[1][3] == magnet[2][7]) {
			check[1][2] = true;
			check[2][1] = true;
		} else {
			check[1][2] = false;
			check[2][1] = false;
		}
		if (magnet[2][3] == magnet[3][7]) {
			check[2][3] = true;
			check[3][2] = true;
		} else {
			check[2][3] = false;
			check[3][2] = false;
		}
		if (magnet[3][3] == magnet[4][7]) {
			check[3][4] = true;
			check[4][3] = true;
		} else {
			check[3][4] = false;
			check[4][3] = false;
		}
	}

	public static void Turn(int num, int dir) {
		// 해당 num자석을 dir방향으로 돌림
		// 미리 pole_check 해놔서 먼저 돌려도 상관 없음
		Rotate(num, dir);

		int before = num - 1;
		int after = num + 1;
		int nd = 0;

		if (dir == 1) {
			nd = -1;
		} else if (dir == -1) {
			nd = 1;
		}

		if (before >= 1 && !visited[before]) {
			if (!check[num][before]) { // 자성이 다르면
				visited[before] = true;
				Turn(before, nd);
				visited[before] = false; // 백트래킹
			}
		}

		if (after <= 4 && !visited[after]) {
			if (!check[num][after]) { // 자성이 다르면
				visited[after] = true;
				Turn(after, nd);
				visited[after] = false; // 백트래킹
			}
		}
	}

	public static void Rotate(int num, int dir) {
		int tmp = 0; // 임시 저장
		// 시계방향
		if (dir == 1) {
			tmp = magnet[num][8];
			for (int i = 8; i >= 2; i--) {
				magnet[num][i] = magnet[num][i - 1];
			}
			magnet[num][1] = tmp;
		}
		// 반시계방향
		else {
			tmp = magnet[num][1];
			for (int i = 1; i <= 7; i++) {
				magnet[num][i] = magnet[num][i + 1];
			}
			magnet[num][8] = tmp;
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			K = Integer.parseInt(br.readLine());

			magnet = new int[5][9];
			check = new boolean[5][5];
			visited = new boolean[5];

			for (int i = 1; i <= 4; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 1; j <= 8; j++) {
					magnet[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			for (int k = 0; k < K; k++) {
				st = new StringTokenizer(br.readLine());
				int num = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken());

				polCheck();

				visited[num] = true;
				Turn(num, dir);
				visited[num] = false; // 백트래킹
			}

			result = (magnet[1][1] * 1) + (magnet[2][1] * 2) + (magnet[3][1] * 4) + (magnet[4][1] * 8);

			System.out.println("#" + tc + " " + result);
		}

		br.close();
	}

}