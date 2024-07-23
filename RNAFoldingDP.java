
/*
 * Implements the dynamic programming solution to the RNA folding
 * problem.
 */

public class RNAFoldingDP implements RNAFolding {

	public int opt(String rna) {
	
		int n = rna.length();
        int[][] M = new int[n][n + 1];


        for (int length = 5; length <= n; length++) {
            for (int start = 0; start <= n - length; start++) {
                int end = start + length - 1;
                M[start][length] = M[start][length - 1];  

                for (int k = start; k <= end - 4; k++) {
                    if (RNAFolding.match(rna.charAt(k), rna.charAt(end))) {
                        int value = k > start ? M[start][k - start - 1] : 0;
                        value += 1 + M[k + 1][end - start - 1];
                        M[start][length] = Math.max(M[start][length], value);
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
