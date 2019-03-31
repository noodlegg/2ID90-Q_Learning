public class QLearner {  
    /**
     * This method performs Q-learning
     * 
     * @param rewards
     * @param paths
     * @param gamma
     * @param noIterations
     * @return 
     */
    public String execute(Integer[][] rewards, Integer[][] paths, Double gamma, Integer noIterations)
    {
        /**
         * Initialize Q
         * First index 's' is the state
         * Second index 'a' is the action
         */
        final Double[][] Q = new Double[rewards.length][rewards.length];
        
        for (int s = 0; s < rewards.length; s++) {
            for (int a = 0; a < Q[s].length; a++) {
                // Initialize only valid moves, else they remain null
                if (rewards[s][a] != null) {
                    Q[s][a] = 0.0;
                }
            }
        }
        
        // Do Q-learning
        for (int i = 0; i < noIterations; i++) {
            // Calculate Q for all paths
            for (Integer[] path : paths) {
                execute(Q, rewards, path, gamma);
            }
        }
        
        return policy(Q);
    }
    
    /** Do Q-learning for one path */
    private void execute(Double[][] Q, Integer[][] rewards, Integer[] path, Double gamma) {
        // Follow the moves along the path
        for (int i = 1; i < path.length; i++) {
            // Look for the best valid move
            Double bestReward = 0.0;
            
            for (int j = 0; j < Q.length; j++) {
                // If path is valid 
                // and expected reward is higher than current bestReward
                if (rewards[path[i]][j] != null && bestReward < Q[path[i]][j]) {
                    // Save new bestReward
                    bestReward = Q[path[i]][j];
                }
                // Else it is not a valid move and skip to next 'j' iteration
            }
            
            // Set new Q value
            Q[path[i - 1]][path[i]] = rewards[path[i - 1]][path[i]] + gamma * bestReward;
        }
    }

    /** Computes the policy */
    private String policy(Double[][] Q) {
        String result = "";
        int bestMoveIndex;
        
        for (Double[] state : Q) {
            // Set impossible values as lower bound
            Double bestReward = -1d;
            bestMoveIndex = -1;
            
            // Look for best valid move for current state
            for (int i = 0; i < state.length; i++) {
                // If state is valid and better than current bestReward
                if (state[i] != null && bestReward < state[i]) {
                    // Set new bestReward and its index
                    bestReward = state[i];
                    bestMoveIndex = i;
                }
                // Else state is invalid and skip to next 'i' iteration
            }
            
            // If there was no valid action, then append "n" to output string
            if (bestMoveIndex == -1) {
                result += "n ";
            } else {
                // Else, append index of bestReward which is bestMoveIndex
                result += bestMoveIndex + " ";
            }
        }
        
        // Removes the final space and returns result
        return result.trim();
    }
}