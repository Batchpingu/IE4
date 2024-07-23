
/*
 * Implements the dynamic programming solution to the RNA folding
 * problem.
 */

public class RNAFoldingDP implements RNAFolding {

	public int opt(String rna) {
		int n = rna.length();
        int[][] M = new int[n][n+1];

		for (int i = 5; i <= n; i++){
            for (int j = 0; j <= n - i; j++) {
				int tail = i + j - 1;
                M[j][i] = M[j][i - 1];
				for (int k = j; k < tail - 4; k ++) {
					if (RNAFolding.match(rna.charAt(k), rna.charAt(tail))) {
						int value = k > j ? M[j][k - 1] : 0;
						value += 1 + M[k + 1][tail - 1];
                        M[j][tail] = Math.max(M[j][tail], value);
                    }
				}
			}
		}
		return M[0][n - 1];
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
