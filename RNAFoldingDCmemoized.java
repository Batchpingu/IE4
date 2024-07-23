
/*
 * Implements the memoized divide-and-conquer solution to the RNA
 * folding problem.
 */

public class RNAFoldingDCmemoized implements RNAFolding {
	private int[][] M;

	public int opt(String rna) {
		int n = rna.length();
		M = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                M[i][j] = -1;
            }
        }
        return compOpt(rna, 0, n - 1);
    }
	public int compOpt (String rna, int i, int j){
        if (j - i + 1 < 5) {
            return 0;
        }

        if (M[i][j] != -1) {
            return M[i][j];
        }

        int result = compOpt(rna, i, j - 1);

        // Consider all possible valid pairings
        for (int k = i; k <= j - 4; k++) {
            if (RNAFolding.match(rna.charAt(k), rna.charAt(j))) {
                int value = (k > i) ? compOpt(rna, i, k - 1) : 0;
                value += 1 + compOpt(rna, k + 1, j - 1);
                result = Math.max(result, value);
            }
        }

        // Memoize and return the result
        M[i][j] = result;
        return result;
	}



	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			String fileName = args[0];
			String rna = RNAFolding.readString(fileName);
			if (RNAFolding.sequenceValid(rna)) {
				RNAFolding solver = new RNAFoldingDCmemoized();
				System.out.println(solver.opt(rna));
			} else
				System.out.println("Input string is not valid");
		} else
			System.out.println("Usage (DC memoized): filename");
	}
}
