package com.jyg.states;
/**
 * created by jiayaoguang at 2017年12月16日
 */
public class StateMachine {

	State state;
	
	void setState(State state) {
		this.state = state;
	}
	
	State getState() {
		
		return state;
		
	}
	
}

interface State{
	
}

