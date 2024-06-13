package com.example.teambuilderapp;

import java.io.Serializable;
import java.util.ArrayList;

public class PokemonTeam implements Serializable {
	//use serializable interface, might be slower than parcelable, so might switch later
	public String name;
	public String format;
	public ArrayList<PokemonSet> roster;
	
	public PokemonTeam(String name){
		this.name = name;
		this.format = "OU";
		this.roster = new ArrayList<PokemonSet>();
	}
	
	public boolean isEmpty(){
		return roster.isEmpty();
	}
	
	public void addPokemon(PokemonSet p){
		roster.add(p);
	}
	
	@Override
	public String toString(){
		String export = "";
		for(PokemonSet mon : roster){
			export += mon.toString() + "\n\n";
		}
		return export;
	}
}
