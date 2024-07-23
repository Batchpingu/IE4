
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
                int jNotUsed = M[start][end - 1];
                int jUsed = jNotUsed;
                char jB = rna.charAt(end);

                for (int t = start; t < end - 4; t++) {
                    char tB = rna.charAt(t);
                    if (RNAFolding.match(tB, jB)) {
                        int value = 1 + (t > start ? M[start][t - 1] : 0) + M[t + 1][end - 1];
                        jUsed = Math.max(jUsed, value);
                    }
                }

                M[start][end] = Math.max(jNotUsed, jUsed);
            }
        }

        return M[i][j];
	}
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
