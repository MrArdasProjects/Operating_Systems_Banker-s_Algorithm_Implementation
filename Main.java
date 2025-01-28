import java.util.Arrays;

public class Main {
    
    static final int PROCESSES = 3; 
    static final int RESOURCES = 4; 

    static int[] available = {4, 6, 2, 2}; 
    static int[][] maximum = { 
        {8, 5, 3, 5},
        {3, 2, 2, 3},
        {9, 0, 2, 4}
    };
    static int[][] allocation = { 
        {0, 1, 0, 2},
        {2, 0, 0, 1},
        {3, 0, 2, 3}
    };

    static int[][] need = new int[PROCESSES][RESOURCES]; 

    public static void main(String[] args) {
        // Calculate the need matrix
        calculateNeed();

        
        boolean safe = isSafe();

        
        if (safe) {
            System.out.println("The system is in a safe state.");
        } else {
            System.out.println("The system is in an unsafe state!");
        }
    }

    
    static void calculateNeed() {
        for (int i = 0; i < PROCESSES; i++) {
            for (int j = 0; j < RESOURCES; j++) {
                need[i][j] = maximum[i][j] - allocation[i][j];
            }
        }
    }

    
    static boolean isSafe() {
        boolean[] finish = new boolean[PROCESSES]; 
        int[] work = Arrays.copyOf(available, RESOURCES); 
        int[] safeSequence = new int[PROCESSES]; // Safe sequence
        int count = 0;

        while (count < PROCESSES) {
            boolean found = false;
            for (int i = 0; i < PROCESSES; i++) {
                if (!finish[i]) { 
                    int j;
                    for (j = 0; j < RESOURCES; j++) {
                        if (need[i][j] > work[j]) {
                            break;
                        }
                    }
                    if (j == RESOURCES) { 
                        for (int k = 0; k < RESOURCES; k++) {
                            work[k] += allocation[i][k];
                        }
                        safeSequence[count++] = i;
                        finish[i] = true;
                        found = true;
                    }
                }
            }
            if (!found) { 
                return false;
            }
        }

        
        System.out.println("Safe sequence: " + Arrays.toString(safeSequence));
        System.out.print("Safe sequence: ");
        for (int i = 0; i < safeSequence.length; i++) {
            System.out.print("P" + safeSequence[i]);
            if (i != safeSequence.length - 1) {
            System.out.print(" -> ");
            }
        }
        System.out.println();
        return true;
    }
}
