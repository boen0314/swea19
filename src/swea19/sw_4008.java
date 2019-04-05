package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// '백준 14888번 연산자 끼워넣기'랑 똑같은 문제
public class sw_4008 {

	static int T;
	static int N;

	static int[] num;
	static int[] op;

	static int min, max, result;

	public static void DFS(int rs, int cnt) {
		if (cnt == N) {
			min = Math.min(min, rs);
			max = Math.max(max, rs);
			return;
		}

		for (int i = 0; i < 4; i++) {
			if (op[i] != 0) {
				op[i]--;

				if (i == 0) // +
					DFS(rs + num[cnt], cnt + 1);
				else if (i == 1) // -
					DFS(rs - num[cnt], cnt + 1);
				else if (i == 2) // *
					DFS(rs * num[cnt], cnt + 1);
				else if (i == 3) // /
					DFS(rs / num[cnt], cnt + 1);

				op[i]++; // 백트래킹
			}
		}

	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());

			num = new int[N];
			op = new int[4];

			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < 4; i++) {
				op[i] = Integer.parseInt(st.nextToken());
			}

			st = new StringTokenizer(br.readLine());
			for (int i = 0; i < N; i++) {
				num[i] = Integer.parseInt(st.nextToken());
			}

			min = Integer.MAX_VALUE;
			max = Integer.MIN_VALUE;

			DFS(num[0], 1);

			result = Math.abs(max - min);
			System.out.println("#" + tc + " " + result);
		}

		br.close();
	}

}
