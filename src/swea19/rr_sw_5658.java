package swea19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;

// 16진수 -> 10진수 변환법 알아두기
// 트리셋, 해쉬셋 특징 알아두고, 이터레이터 사용법 알아두기
// 문자열 메소드 잘 알아두기
public class rr_sw_5658 {

	static int T; // 테스트 케이스
	static int N, K;

	static TreeSet<Long> ts; // 숫자 모음

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		T = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			// HashSet은 중복x, 순서x (주로 정보 수집용으로 사용됨)
			// TreeSet은 중복x, 순서x, 자동 정렬♡♡♡♡♡
			ts = new TreeSet<>();

			String number = br.readLine();
			int boxlen = number.length() / 4;

			// 시계방향 회전 횟수
			for (int i = 0; i < boxlen; i++) {
				// 생성 가능한 숫자
				for (int j = 0; j < number.length(); j += boxlen) {
					Long tmp = Long.parseLong(number.substring(j, j + boxlen), 16);
					ts.add(tmp); // 트리셋에 넣음 (중복 제거)
				}

				// 시계방향 회전
				String tmp1 = number.substring(0, number.length() - 1);
				char tmp2 = number.charAt(number.length() - 1);
				number = tmp2 + tmp1;

				// 10진수 -> 16진수 변환
				// Integer.toHexString(255).toUpperCase(); //FF
			}

			// 내림차순 이터레이터
			Iterator<Long> itr = ts.descendingIterator();

			while (K != 1) {
				itr.next();
				K--;
			}

			System.out.println("#" + tc + " " + itr.next());
		}

		br.close();
	}

}
