
/**
 * Asterisk Sudoku solver.
 * 
 * Prints the number of solutions of a Sudoku if there are multiple solutions.
 * If there is only a single solution, it prints this solution instead.
 *
 * TODO: Fill in your names and student IDs
 * 
 * @author Matyas Szabolcs
 * @id 1835521
 * @author Quinn Caris
 * @id 1779133
 */


class SudokuSolver {

    // Size of the grid.
    static final int SUDOKU_SIZE = 9;
    // Minimum digit to be filled in.
    static final int SUDOKU_MIN_NUMBER = 1;
    // Maximum digit to be filled in.
    static final int SUDOKU_MAX_NUMBER = 9;
    // Dimension of the boxes, i.e., the sub-grids that should contain all digits.
    static final int SUDOKU_BOX_DIMENSION = 3;

    // The puzzle grid; 0 represents empty.
    // This particular grid has exactly one solution.
    // Other grids might have multiple solutions.
    static int[][] grid = new int[][] {
            { 0, 9, 0, 7, 3, 0, 4, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 5, 0, 0 },
            { 3, 0, 0, 0, 0, 6, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 2, 6, 4, 0 },
            { 0, 0, 0, 6, 5, 1, 0, 0, 0 },
            { 0, 0, 6, 9, 0, 7, 0, 0, 0 },
            { 5, 8, 0, 0, 0, 0, 0, 0, 0 },
            { 9, 0, 0, 0, 0, 3, 0, 2, 5 },
            { 6, 0, 3, 0, 0, 0, 8, 0, 0 },
    };

    // Solution counter
    int solutionCounter = 0;

    /**
     * Prints this Sudoku.
     */
    static void print() {
        // TODO 1
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            // horizontal lines
            if ((i + 3) % 3 == 0) {
                System.out.println("+-----------------+");
            }
            for (int j = 0; j < SUDOKU_SIZE; j++) {
                // vertical lines
                if ((j + 3) % 3 == 0) {
                    System.out.print("|");
                } else {
                    // indications of asterisks
                    if (i == 4 && j == 1) {
                        System.out.print(">");
                    } else if (i == 2 && j == 2) {
                        System.out.print(">");
                    } else if (i == 4 && j == 2) {
                        System.out.print("<");
                    } else if (i == 1 && j == 4) {
                        System.out.print(">");
                    } else if (i == 1 && j == 5) {
                        System.out.print("<");
                    } else if (i == 2 && j == 7) {
                        System.out.print("<");
                    } else if (i == 4 && j == 4) {
                        System.out.print(">");
                    } else if (i == 4 && j == 5) {
                        System.out.print("<");
                    } else if (i == 4 && j == 7) {
                        System.out.print(">");
                    } else if (i == 4 && j == 8) {
                        System.out.print("<");
                    } else if (i == 6 && j == 2) {
                        System.out.print(">");
                    } else if (i == 6 && j == 7) {
                        System.out.print("<");
                    } else if (i == 7 && j == 4) {
                        System.out.print(">");
                    } else if (i == 7 && j == 5) {
                        System.out.print("<");
                    } else {
                        System.out.print(" ");
                    }
                }
                // number
                if (grid[i][j] != 0) {
                    System.out.print(grid[i][j]);
                } else {
                    System.out.print(" ");
                }
            }
            // last vertical line
            System.out.print("|");
            System.out.println("");
        }
        // last horizontal line
        System.out.println("+-----------------+");
        System.out.println();
    }

    /**
     * Determine if there's a conflict when we fill in d at position (r, c).
     * 
     * @param r row index
     * @param c column index
     * @param d value
     * @return true if there's a conflict, false otherwise
     */
    boolean givesConflict(int r, int c, int d) {
        // TODO 2
        return !(!rowConflict(r, d) && !colConflict(c, d) 
            && !boxConflict(r, c, d) && !asteriskConflict(r, c, d));
    }

    /**
     * Determine if there's a conflict when we fill in d in row r.
     * 
     * @param r row index
     * @param d value
     * @return true if there's a conflict, false otherwise
     */
    boolean rowConflict(int r, int d) {
        // TODO 2
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            if (grid[r][i] == d) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if there's a conflict when we fill d in column c.
     * 
     * @param c column index
     * @param d value
     * @return true if there's a conflict, false otherwise
     */
    boolean colConflict(int c, int d) {
        // TODO 2
        for (int i = 0; i < SUDOKU_SIZE; i++) {
            if (grid[i][c] == d) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if there's a conflict when we fill d in box at (r, c).
     * 
     * @param r row index
     * @param c column index
     * @param d value
     * @return true if there's a conflict, false otherwise
     */
    boolean boxConflict(int r, int c, int d) {
        // TODO 2
        for (int row = r - r % 3; row < r - r % 3 + 3; row++) {
            for (int col = c - c % 3; col < c - c % 3 + 3; col++) {
                if (grid[row][col] == d) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determine if there's a conflict in the asterisk when we fill in d.
     * 
     * @param row row index
     * @param col col index
     * @param d   value
     * @return true if there's a conflict, false otherwise
     */
    boolean asteriskConflict(int row, int col, int d) {
        // TODO 2
        // asterisk squares
        Integer[][] a = { { 2, 2 }, { 1, 4 }, { 2, 6 }, 
            { 4, 1 }, { 4, 4 }, { 4, 7 }, 
            { 6, 2 }, { 7, 4 }, { 6, 6 } };
        // check if current square is an asterisk square
        for (int i = 0; i < 9; i++) {
            if (row == a[i][0] && col == a[i][1]) {
                // check if value matches
                for (int j = 0; j < 9; j++) {
                    if (d == grid[a[j][0]][a[j][1]]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determine if sudoku is filled in completely or not.
     * 
     * @return true if there are no empty cells left.
     */
    boolean filledSudoku() {
        // stays true if the for loops never changes it to false
        boolean testFull = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    testFull = false;
                }
            }
        }
        return testFull;
    }
    
    int[] lastSquare = { 0, 0 };

    /**
     * Find the next empty square in "reading order".
     * 
     * @return coordinates of the next empty square
     */
    int[] findEmptySquare() {
        // TODO 3
        // gets the first cell that equals zero (is empty)
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == 0) {
                    lastSquare[0] = i;
                    lastSquare[1] = j;

                    break;
                }
            }
        }
        
        // returns null if the sudoku doesn't have any zeroes, otherwise returns 
        if (filledSudoku()) {
            return null;
        } else {
            return lastSquare;
        }
    }

    

    /**
     * Find all solutions for the grid.
     * 
     * Stores the final solution.
     */
    void solve() {
        if (solveRecursive(grid)) {
            print();
        } else {
            System.out.println("0");
        }
    }

    /**
     * Uses a recursive backtracking method to fill up the grid with non-conflicting values.
     */
    boolean solveRecursive(int[][] grid) {
        // TODO 4

        // stores the cell that is empty
        int[] nextEmpty = findEmptySquare();

        // if the grid doesn't have any zeroes anymore, 
        // it will stop the recursion by making the boolean true
        if (filledSudoku()) {
            return true;
        }

        int r = nextEmpty[0];
        int c = nextEmpty[1];

        // checks for every number if it conflicts
        for (int d = 1; d < 10; d++) {
            if (!givesConflict(r, c, d)) {
                // stores the non-conflicting value in the grid to check for conflicts later
                grid[r][c] = d;
                if (solveRecursive(grid)) {
                    return true;
                } else {
                    // if the called solve function returns false 
                    // (meaning it didn't find any values that didn't have any conflicts), 
                    // this function will make the edited value zero again and return false itself
                    grid[r][c] = 0;
                }
            } 
        }
        // if all numbers for a cell conflict, it returns false and the 
        // previous function that called this one will have to backtrack
        return false;
    }

    /**
     * Run the solver and output the results.
     */
    void solveIt() {
        // TODO 5
        solve();
    }

    public static void main(String[] args) {
        (new SudokuSolver()).solveIt();
    }
}
