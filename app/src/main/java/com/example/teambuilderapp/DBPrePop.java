package com.example.teambuilderapp;

import android.util.Log;
import android.content.Context;

import com.example.teambuilderapp.database.AbilityDao;
import com.example.teambuilderapp.database.AbilityEntity;
import com.example.teambuilderapp.database.ItemEntity;
import com.example.teambuilderapp.database.PokemonDao;
import com.example.teambuilderapp.database.PokemonDatabase;
import com.example.teambuilderapp.database.PokemonEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.util.JsonReader;

public class DBPrePop {

	public static List<PokemonEntity> parsePokedex(Context context){
		List<PokemonEntity> pokemonList = new ArrayList<>();
		try{
			InputStream is = context.getAssets().open("pokedex.json");
			JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
			
			//each pokemon
			reader.beginObject();
			while(reader.hasNext()){
				String pokemonName = reader.nextName();
				
				PokemonEntity p = new PokemonEntity(pokemonName);
				
				//each field for each pokemon
				reader.beginObject();
				while(reader.hasNext()){
					String fieldName = reader.nextName();
					Log.d(pokemonName + " field:", fieldName);
					//process each field
					switch (fieldName){
						case "num":
							int num = reader.nextInt();
							p.num = num;
							break;
							
						case "name":
							String name = reader.nextString();
							p.name = name;
							break;
							
						case "types":
							reader.beginArray();
							String type = reader.nextString();
							p.type1 = type;
							if(reader.hasNext()){
								p.type2 = reader.nextString();
							}
							reader.endArray();
							break;
						
						case "genderRatio":
							reader.skipValue();
							p.gender = "B";
							break;
						
						case "gender":
							p.gender = reader.nextString();
						
						case "baseStats":
							reader.beginObject();
							while (reader.hasNext()) {
								String statName = reader.nextName();
								int statValue = reader.nextInt();
								switch (statName){
									case "hp":
										p.hp = statValue;
										break;
									
									case "atk":
										p.atk = statValue;
										break;
									
									case "def":
										p.def = statValue;
										break;
									
									case "spa":
										p.spa = statValue;
										break;
									
									case "spd":
										p.spd = statValue;
										break;
									
									case "spe":
										p.spe = statValue;
										break;
								}
							}
							reader.endObject();
							break;
						
						case "abilities":
							reader.beginObject();
							while (reader.hasNext()) {
								String abilitySlot = reader.nextName();
								String ability = reader.nextString();
								switch (abilitySlot){
									case "0":
										p.ability0 = ability;
										break;
										
									case "1":
										p.ability1 = ability;
										break;
										
									case "H":
										p.abilityh = ability;
										break;
								}
							}
							reader.endObject();
							break;
						
						case "weightkg":
							double weight = reader.nextDouble();
							p.weight = weight;
							break;
							
						default:
							reader.skipValue();
							break;
					}
				}
				pokemonList.add(p);
				reader.endObject();
				
			}
			reader.endObject();
			reader.close();
			is.close();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return pokemonList;
	}
	
	public static void prePopPokemon(Context context){
		
		PokemonDatabase db = PokemonDatabase.getDatabase(context);
		PokemonDao dao = db.pokemonDao();
		
		List<PokemonEntity> list = parsePokedex(context);
		PokemonEntity[] pokemonArray = list.toArray(new PokemonEntity[list.size()]);
		new Thread(new Runnable() {
			@Override
			public void run() {
				dao.insertPokemon(pokemonArray);
			}
		}).start();
	}
	
	public static List<AbilityEntity> parseAbilities(Context context){
		List<AbilityEntity> abilities = new ArrayList<>();
		
		try{
			InputStream is = context.getAssets().open("abilities.json");
			JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
			
			reader.beginObject();
			while(reader.hasNext()){
				String ability = reader.nextName();
				
				AbilityEntity a = new AbilityEntity(ability);
				
				reader.beginObject();
				while(reader.hasNext()){
					String fieldName = reader.nextName();
					switch(fieldName){
						case "shortDesc":
							a.shortDesc = reader.nextString();
							break;
						
						case "desc":
							a.desc = reader.nextString();
							break;
							
						case "name":
							a.ability = reader.nextString();
							break;
						
						default:
							reader.skipValue();
					}
				}
				abilities.add(a);
				reader.endObject();
				
			}
			reader.endObject();
			reader.close();
			is.close();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return abilities;
	}
	
	public static void prePopAbilities(Context context){
		PokemonDatabase db = PokemonDatabase.getDatabase(context);
		AbilityDao dao = db.abilityDao();
		
		List<AbilityEntity> list = parseAbilities(context);
		AbilityEntity[] abilityArray = list.toArray(new AbilityEntity[list.size()]);
		new Thread(new Runnable() {
			@Override
			public void run() {
				dao.insert(abilityArray);
			}
		}).start();
	}
	
	public static List<ItemEntity> parseItems(Context context){
		List<ItemEntity> items = new ArrayList<>();
		
		try{
			InputStream is = context.getAssets().open("abilities.json");
			JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
			
			reader.beginObject();
			while(reader.hasNext()){
				String itemName = reader.nextName();
				
				ItemEntity item = new ItemEntity(itemName);
				
				reader.beginObject();
				while(reader.hasNext()){
					
				}
				
			}
			
		} catch (IOException e){
		
		}
		
		return items;
	}

}
