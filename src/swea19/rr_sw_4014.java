package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 스스로 못 풀었음, 다시 풀어도 못 풀 것 같음
// 문제 잘 익혀두기
public class rr_sw_4014 {

	static int T;
	static int N, X;
	static int[][] matrix;

	static int result;

	public static void Check() {
		// 가로 검사
		for (int i = 0; i < N; i++) {
			int before = matrix[i][0]; // 전 지형
			int cnt = 0; // 같은 높이 지형 카운트

			for (int j = 0; j < N; j++) {
				// 높이 차이가 2 이상
				if (Math.abs(before - matrix[i][j]) >= 2)
					break;

				// 내리막길
				if (before > matrix[i][j]) {
					if (cnt < 0) // 평평한 지형 부족
						break;
					cnt = -X;
				}
				// 오르막길
				else if (before < matrix[i][j]) {
					if (cnt < X) // 평평한 지형 부족
						break;
					cnt = 0; // 활주로 놓음, 다시 초기화
				}

				before = matrix[i][j];
				cnt++;

				// cnt가 양수여야 하는 이유는
				// 내리막길에서 -X가 되는데 평평한 지형이 충분하여 무조건 활주로가 건설되야 함
				// 오르막길 나오기 전에는 높은지형이 나올 때 까지 평평한 지형이 카운트 되는 것이므로 상관X
				// (평평한 지형만 나오다 끝나도 활주로 건설 가능)
				if (j == N - 1 && cnt >= 0)
					result += 1;
			}
		}

		// 세로 검사
		for (int j = 0; j < N; j++) {
			int before = matrix[0][j]; // 전 지형
			int cnt = 0; // 같은 높이 지형 카운트

			for (int i = 0; i < N; i++) {
				// 높이 차이가 2 이상
				if (Math.abs(before - matrix[i][j]) >= 2)
					break;

				// 내리막길
				if (before > matrix[i][j]) {
					if (cnt < 0)
						break;
					cnt = -X;
				}
				// 오르막길
				else if (before < matrix[i][j]) {
					if (cnt < X)
						break;
					cnt = 0; // 활주로 놓음, 다시 초기화
				}

				before = matrix[i][j];
				cnt++;

				// 마지막까지 무사히 활주로 건설함
				if (i == N - 1 && cnt >= 0)
					result += 1;
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
			X = Integer.parseInt(st.nextToken());

			matrix = new int[N][N];

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					matrix[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			result = 0; // 초기화
			Check();
			System.out.println("#" + tc + " " + result);
		}

		br.close();
	}

}
