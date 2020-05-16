package engine.ia.states;

public interface State {

	public String getName();

	public boolean evaluate (FSM fsm);
 
	public void update(float diffTiime);
}
