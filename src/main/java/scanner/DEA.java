/**
 * The type Dea.
 *
 * @author jan on 21.03.2020
 * @project compilerbau
 */
public class DEA {
    /**
     * The Transitions.
     */
    char transitions[][][];
    /**
     * The States.
     */
    byte states[];

    /**
     * Instantiates a new Dea.
     *
     * @param transitions the transitions
     * @param states      the states
     */
    DEA(char transitions[][][], byte states[]) {
        this.transitions = transitions;
        this.states = states;
    }
}
