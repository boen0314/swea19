package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// '백준 14889번 스타트와 링크'와 똑같은 문제
// 전형적인 DFS + 백트래킹 문제
public class sw_4012 {

	static int T;
	static int N;
	static int[][] matrix;
	static boolean[] A; // A음식 선택

	static int result;

	private static void DFS(int n, int cnt) {
		if (cnt == N / 2) {
			int a = 0, b = 0; // 시너지 합
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
					if (A[i] && A[j]) { // 같은 A음식 재료라면
						a += matrix[i][j];
					} else if (!A[i] && !A[j]) { // 같은 B음식 재료라면
						b += matrix[i][j];
					}
				}
			}
			result = Math.min(result, Math.abs(a - b));
			return;
		}

		// 범위 넘음
		if (n == N)
			return;

		// A가 선택 (A가 선택 안한건 자연스럽게 B가 선택한 것이 됨)
		A[n] = true;
		DFS(n + 1, cnt + 1);
		A[n] = false; // 백트래킹

		// B가 선택
		DFS(n + 1, cnt);

	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());

			matrix = new int[N + 1][N + 1];
			A = new boolean[N + 1];

			for (int i = 1; i <= N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 1; j <= N; j++) {
					matrix[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			result = Integer.MAX_VALUE;
			DFS(1, 0);
			System.out.println("#" + tc + " " + result);
		}
		br.close();
	}
}
