package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 너무 어렵다ㅠㅠ 푸는 방법 아이디어 생각해내는게 어려운듯
public class rrr_sw_2115 {

	static int T; // 테스트 케이스
	static int N, M, C; // 벌통 크기, 선택 가능한 벌통 개수, 채취 가능 꿀의 최대 양
	static int[][] matrix;
	static int[][] honey;

	static int maxProfit, maxProfit_2, result;

	public static void DFS(int x, int y, int cnt, int c, int profit) {
		if (c > C)
			return; // 최대 채취양 넘어가면 종료

		if (cnt == M) { // 벌통 개수 다 선택했으면
			maxProfit = Math.max(maxProfit, profit);
			return;
		}

		// 벌꿀 선택하는 경우
		DFS(x, y + 1, cnt + 1, c + matrix[x][y], profit + (matrix[x][y] * matrix[x][y]));
		// 벌꿀 선택 안하는 경우
		DFS(x, y + 1, cnt + 1, c, profit);
	}

	public static void Select(int x, int y) {
		// 첫번째 벌꿀통 시작점 (x,y)
		// 첫번째 벌꿀통 선택 후 남은 자리에서 두번째 벌꿀통 선택 (같은 열)
		for (int j = y + M; j < N - M + 1; j++) {
			maxProfit_2 = Math.max(maxProfit_2, honey[x][j]);
		}

		// 첫번째 벌꿀통 시작점 (x,y)
		// 첫번째 벌꿀통 선택 후 남은 자리에서 두번째 벌꿀통 선택 (다음 행)
		for (int i = x + 1; i < N; i++) {
			for (int j = 0; j < N - M + 1; j++) {
				maxProfit_2 = Math.max(maxProfit_2, honey[i][j]);
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
			C = Integer.parseInt(st.nextToken());

			matrix = new int[N][N];
			honey = new int[N][N]; // 최대 수익 저장

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					matrix[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			result = 0;

			// 벌꿀통 선택 후(시작점) 최대 수익 계산 (미리 계산해놓기)
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N - M + 1; j++) {
					maxProfit = 0;
					DFS(i, j, 0, 0, 0);
					honey[i][j] = maxProfit; // (i,j)가 시작점일 때 최대 수익
				}
			}

			// 최대 수익 저장된 배열에서 시작점(벌꿀통) 2개 선택
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N - M + 1; j++) {
					maxProfit_2 = 0;
					// 첫번째 벌꿀통(i,j) 기준 어떤 두번째 벌꿀통 선택했을 때 최대 수익인지
					Select(i, j);
					// honey[i][j] -> 첫번째 벌꿀통 최대 수익
					// maxProfit_2 -> 두번째 벌꿀통 최대 수익
					result = Math.max(result, honey[i][j] + maxProfit_2);
				}
			}

			System.out.println("#" + tc + " " + result);
			result = 0; // 초기화
		}

		br.close();

	}
}
