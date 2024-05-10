package com.example.teambuilderapp;


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchHandling {
	public SearchHandling(){
	
	}
	
	public static ArrayList<String> pokemonSearch(String s){
		s = s.toLowerCase();
		
		String[] testing = {"bulbasaur", "venusaur", "charmander",
				"squirtle", "ivysaur", "charmeleon", "charizard", "wartortle",
				"blastoise", "pidgey", "spearow", "mew", "mewtwo"};
		
		ArrayList<String> pokemon = new ArrayList<>(Arrays.asList(testing));
		ArrayList<String> results = new ArrayList<>();
		
		if(s.equals("")){
			return pokemon;
		}
		
		for(int i = 0; i < pokemon.size(); i++){
			if(pokemon.get(i).startsWith(s) ){
				results.add(pokemon.get(i));
			}
		}
		
		return results;
	}
	
	public static ArrayList<String> itemSearch(String s){
		s = s.toLowerCase();
		
		String[] testing = {"Life Orb", "Choice Scarf", "Leftovers"};
		
		ArrayList<String> items = new ArrayList<>(Arrays.asList(testing));
		ArrayList<String> results = new ArrayList<>();
		
		if(s.equals("")){
			return items;
		}
		
		for(int i = 0; i < items.size(); i++){
			if(items.get(i).startsWith(s) ){
				results.add(items.get(i));
			}
		}
		
		return results;
	}
	
	
}
