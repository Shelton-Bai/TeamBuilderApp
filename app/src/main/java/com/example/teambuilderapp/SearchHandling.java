package com.example.teambuilderapp;


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchHandling {
	public SearchHandling(){
	
	}
	
	public static ArrayList<String> pokemonSearch(String s){
		s = s.toLowerCase();
		
		String[] testing = {"bulbasaur", "charmander", "squirtle", "ivysaur", "charmeleon", "charizard"};
		
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
}
