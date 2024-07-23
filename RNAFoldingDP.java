
/*
 * Implements the dynamic programming solution to the RNA folding
 * problem.
 */

public class RNAFoldingDP implements RNAFolding {

	public int opt(String rna) {
		int N = rna.length();
		return opt(" " + rna, 1, N);
    }
	public int opt (String rna, int i, int j){
		int n = rna.length();
		int[][] M = new int[n][n];

        for (int len = 5; len <= n; len++) {
            for (int start = 0; start <= n - len; start++) {
                int end = start + len - 1;
                int jCurr = M[start][end - 1];
                int jUsed = jCurr;
                char jChar = rna.charAt(end);

                for (int k = start; k < end - 4; k++) {
                    char tChar = rna.charAt(k);
                    if (RNAFolding.match(tChar, jChar)) {
						int value = k > start ? M[start][k - 1] : 0;
						value += 1 + M[k + 1][end - 1];
                        jUsed = Math.max(jUsed, value);
                    }
                }

                M[start][end] = Math.max(jCurr, jUsed);
            }
        }

        return M[i][j];
	}
	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			String fileName = args[0];
			String rna = RNAFolding.readString(fileName);
			if (RNAFolding.sequenceValid(rna)) {
				RNAFolding solver = new RNAFoldingDP();
				System.out.println(solver.opt(rna));
			} else
				System.out.println("Input string is not valid");
		} else
			System.out.println("Usage (DP): filename");
	}
}
