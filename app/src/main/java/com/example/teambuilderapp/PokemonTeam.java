package com.example.teambuilderapp;

import java.io.Serializable;
import java.util.ArrayList;

public class PokemonTeam implements Serializable {
	//use serializable interface, might be slower than parcelable, so might switch later
	public String name;
	public String format;
	public ArrayList<Pokemon> roster;
	
	public PokemonTeam(String name){
		this.name = name;
		this.format = "OU";
		this.roster = new ArrayList<Pokemon>();
	}
	
	public boolean isEmpty(){
		return roster.isEmpty();
	}
	
	public void addPokemon(Pokemon p){
		roster.add(p);
	}
	
	@Override
	public String toString(){
		String export = "";
		for(Pokemon mon : roster){
			export += mon.toString() + "\n\n";
		}
		return export;
	}
}
