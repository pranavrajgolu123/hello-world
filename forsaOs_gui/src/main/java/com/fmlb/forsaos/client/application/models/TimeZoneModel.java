package com.fmlb.forsaos.client.application.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.aria.client.State;


public class TimeZoneModel implements Serializable,TimeZoneComboBoxModel {

	private static final long serialVersionUID = 1L;
    private String name;
    private List<State> states = new ArrayList<>();

    public TimeZoneModel()
	{
		super();
	}

    public TimeZoneModel(String name, List<State> states) {
    	super();
        this.name = name;
        this.states = states;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public void addCountry(State state) {
        states.add(state);
    }
    
    @Override
	public String getDisplayStateName()
	{
		return this.name;
	}

	@Override
	public List<State> getDisplayStates()
	{
		return this.states;
	}

}