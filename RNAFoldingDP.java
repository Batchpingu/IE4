
/*
 * Implements the dynamic programming solution to the RNA folding
 * problem.
 */

public class RNAFoldingDP implements RNAFolding {

	public int opt(String rna) {
		int N = rna.length();
		return opt(" " + rna, 0, N);

	}
	public int opt(String rna, int i, int j) {
		if (i >= j - 4) {
			return 0;
		}
	
		int maxPairs = opt(rna, i, j - 1);
		for (int t = i; t < j-1; t++) {
			if (RNAFolding.match(rna.charAt(t), rna.charAt(j))) {
				int value = 1 + opt(rna, i, t - 1) + opt(rna, t + 1, j - 1);
				maxPairs = Math.max(maxPairs, value);
			}
		}
		return maxPairs;
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
