package com.example.teambuilderapp;

import android.util.Log;
import android.content.Context;

import com.example.teambuilderapp.database.AbilityDao;
import com.example.teambuilderapp.database.AbilityEntity;
import com.example.teambuilderapp.database.FormatDao;
import com.example.teambuilderapp.database.FormatEntity;
import com.example.teambuilderapp.database.ItemDao;
import com.example.teambuilderapp.database.ItemDescription;
import com.example.teambuilderapp.database.ItemDescriptionDao;
import com.example.teambuilderapp.database.ItemEntity;
import com.example.teambuilderapp.database.LearnsetDao;
import com.example.teambuilderapp.database.LearnsetEntity;
import com.example.teambuilderapp.database.MoveDao;
import com.example.teambuilderapp.database.MoveDescription;
import com.example.teambuilderapp.database.MoveDescriptionDao;
import com.example.teambuilderapp.database.MoveEntity;
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
					//process each field
//					Log.d(pokemonName, fieldName);
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
							break;
						
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
		new Thread(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				PokemonDatabase db = PokemonDatabase.getDatabase(context);
				PokemonDao dao = db.pokemonDao();
				
				List<PokemonEntity> list = parsePokedex(context);
				PokemonEntity[] array = list.toArray(new PokemonEntity[list.size()]);
				
				dao.insertPokemon(array);
				
				long duration = System.currentTimeMillis() - startTime; // Calculate duration
				Log.d("PrePop", String.format("Populated Pokemon in %d milliseconds", duration));
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
				Log.d("PrePop", "Populated Abilities");
			}
		}).start();
	}
	public static List<ItemEntity> parseItems(Context context){
		List<ItemEntity> items = new ArrayList<>();
		
		try{
			InputStream is = context.getAssets().open("items.json");
			JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
			
			reader.beginObject();
			while(reader.hasNext()){
				String itemName = reader.nextName();
				ItemEntity item = new ItemEntity(itemName);
				
				reader.beginObject();
				while(reader.hasNext()){
					String fieldName = reader.nextName();
//					Log.d(itemName, fieldName);
					switch(fieldName){
						case "name":
							item.name = reader.nextString();
							break;
						case "isNonStandard":
							item.isNonstandard = reader.nextString();
							break;
						case "itemUser":
							String userArray = "[";
							reader.beginArray();
							while(reader.hasNext()){
								userArray = userArray + String.format("\"%s\",", reader.nextString());
							}
							reader.endArray();
							userArray = userArray.substring(0, userArray.length() - 1) + "]";
							item.user = userArray;
							break;
						default:
							reader.skipValue();
					}
				}
				items.add(item);
				reader.endObject();
			}
			reader.endObject();
			reader.close();
			is.close();
			
		} catch (IOException e){
			throw new RuntimeException(e);
		}
		
		return items;
	}
	public static void prePopItems(Context context){
		PokemonDatabase db = PokemonDatabase.getDatabase(context);
		ItemDao dao = db.itemDao();
		
		List<ItemEntity> list = parseItems(context);
		ItemEntity[] itemArray = list.toArray(new ItemEntity[list.size()]);
		new Thread(new Runnable() {
			@Override
			public void run() {
				dao.insert(itemArray);
				Log.d("PrePop", "Populated Items");
			}
		}).start();
	}
	public static List<ItemDescription> parseItemDescs(Context context){
		List<ItemDescription> descriptions = new ArrayList<>();
		
		try {
			InputStream is = context.getAssets().open("itemText.json");
			JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
			
			reader.beginObject();
			while (reader.hasNext()) {
				String itemName = reader.nextName();
				ItemDescription item = new ItemDescription(itemName);
				
				reader.beginObject();
				while(reader.hasNext()){
					String fieldName = reader.nextName();
					switch (fieldName){
						case "name":
							item.item = reader.nextString();
							break;
						case "desc":
							item.desc = reader.nextString();
							break;
						default:
							reader.skipValue();
					}
				}
				descriptions.add(item);
				reader.endObject();
			}
			reader.endObject();
			reader.close();
			is.close();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return descriptions;
	}
	public static void prePopItemDescriptions(Context context){
		PokemonDatabase db = PokemonDatabase.getDatabase(context);
		ItemDescriptionDao dao = db.itemDescriptionDao();
		
		List<ItemDescription> list = parseItemDescs(context);
		ItemDescription[] array = list.toArray(new ItemDescription[list.size()]);
		new Thread(new Runnable() {
			@Override
			public void run() {
				dao.insert(array);
				Log.d("PrePop", "Populated Item Descriptions");
			}
		}).start();
	}
	public static List<MoveEntity> parseMoves(Context context){
		List<MoveEntity> moves = new ArrayList<>();
		
		try {
			InputStream is = context.getAssets().open("moves.json");
			JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
			
			reader.beginObject();
			while (reader.hasNext()) {
				String moveName = reader.nextName();
				MoveEntity move = new MoveEntity(moveName);
				
				reader.beginObject();
				while(reader.hasNext()){
					String fieldName = reader.nextName();
					switch (fieldName){
						case "name":
							move.name = reader.nextString();
							break;
						case "num":
							move.num = reader.nextInt();
							break;
						case "accuracy":
							String acc = reader.nextString();
							if(acc.equals("true")){
								move.accuracy = -1;
							} else {
								move.accuracy = Integer.parseInt(acc);
							}
							break;
						case "basePower":
							move.power = reader.nextInt();
							break;
						case "category":
							move.category = reader.nextString();
							break;
						case "pp":
							move.pp = reader.nextInt();
							break;
						case "priority":
							move.priority = reader.nextInt();
							break;
						case "target":
							move.target = reader.nextString();
							break;
						case "type":
							move.type = reader.nextString();
							break;
						default:
							reader.skipValue();
					}
				}
				moves.add(move);
				reader.endObject();
			}
			reader.endObject();
			reader.close();
			is.close();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return moves;
	}
	public static void prePopMoves(Context context){
		new Thread(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				PokemonDatabase db = PokemonDatabase.getDatabase(context);
				MoveDao dao = db.moveDao();
				
				List<MoveEntity> list = parseMoves(context);
				MoveEntity[] array = list.toArray(new MoveEntity[list.size()]);
				
				dao.insert(array);
				
				long duration = System.currentTimeMillis() - startTime; // Calculate duration
				Log.d("PrePop", String.format("Populated Moves in %d milliseconds", duration));
			}
		}).start();
	}
	public static List<MoveDescription> parseMoveDescs(Context context){
		List<MoveDescription> moves = new ArrayList<>();
		
		try {
			InputStream is = context.getAssets().open("moveText.json");
			JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
			
			reader.beginObject();
			while (reader.hasNext()) {
				String moveName = reader.nextName();
				MoveDescription move = new MoveDescription(moveName);
				
				reader.beginObject();
				while(reader.hasNext()){
					String fieldName = reader.nextName();
					switch (fieldName){
						case "name":
							move.name = reader.nextString();
							break;
						case "desc":
							move.desc = reader.nextString();
							break;
						case "shortDesc":
							move.shortDesc = reader.nextString();
							break;
						default:
							reader.skipValue();
					}
				}
				moves.add(move);
				reader.endObject();
			}
			reader.endObject();
			reader.close();
			is.close();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return moves;
	}
	public static void prePopMoveDescriptions(Context context){
		PokemonDatabase db = PokemonDatabase.getDatabase(context);
		MoveDescriptionDao dao = db.moveDescriptionDao();
		
		List<MoveDescription> list = parseMoveDescs(context);
		MoveDescription[] array = list.toArray(new MoveDescription[list.size()]);
		new Thread(new Runnable() {
			@Override
			public void run() {
				dao.insert(array);
				Log.d("PrePop", "Populated Move Descriptions");
			}
		}).start();
	}
	public static List<FormatEntity> parseFormats(Context context){
		List<FormatEntity> formats = new ArrayList<>();
		
		try {
			InputStream is = context.getAssets().open("formats.json");
			JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
			
			reader.beginObject();
			while (reader.hasNext()) {
				String pokemonName = reader.nextName();
				FormatEntity format = new FormatEntity(pokemonName);
				
				reader.beginObject();
				while(reader.hasNext()){
					String fieldName = reader.nextName();
//					Log.d(pokemonName, fieldName);
					switch (fieldName){
						case "tier":
							format.tier = reader.nextString();
							break;
						case "isNonstandard":
							format.isNonstandard = reader.nextString();
							break;
						case "doublesTier":
							format.doublesTier = reader.nextString();
							break;
						case "natDexTier":
							format.natDexTier = reader.nextString();
							break;
						default:
							reader.skipValue();
							break;
					}
				}
				formats.add(format);
				reader.endObject();
			}
			reader.endObject();
			reader.close();
			is.close();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return formats;
	}
	public static void prePopFormats(Context context){
		new Thread(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				PokemonDatabase db = PokemonDatabase.getDatabase(context);
				FormatDao dao = db.formatDao();
				
				List<FormatEntity> list = parseFormats(context);
				FormatEntity[] array = list.toArray(new FormatEntity[list.size()]);
				
				dao.insert(array);
				
				long duration = System.currentTimeMillis() - startTime; // Calculate duration
				Log.d("PrePop", String.format("Populated Formats in %d milliseconds", duration));
			}
		}).start();
	}
	public static List<LearnsetEntity> parseLearnset(Context context){
		List<LearnsetEntity> learnsets = new ArrayList<>();
		
		try {
			InputStream is = context.getAssets().open("learnsets.json");
			JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
			
			reader.beginObject();
			while (reader.hasNext()) {
				String pokemonName = reader.nextName();
				
				
				reader.beginObject();
				while(reader.hasNext()){
					String fieldName = reader.nextName();
					switch (fieldName){
						case "learnset": //get to learnset field of pokemon
							reader.beginObject();
							while(reader.hasNext()){ //for each move in learnset
								LearnsetEntity learnset = new LearnsetEntity(pokemonName);
								String moveName = reader.nextName();
								learnset.move = moveName;
								boolean added = false;
								reader.beginArray();
								while(reader.hasNext()){ //check each learn method for move
									String moveCode = reader.nextString();
									if(moveCode.charAt(0) == '9' && added == false){ //if learned in gen 9, add to list
										learnsets.add(learnset);
										added = true;
									}
								}
								reader.endArray();
							}
							reader.endObject();
							break;
						default:
							reader.skipValue();
							break;
					}
				}
				reader.endObject();
			}
			reader.endObject();
			reader.close();
			is.close();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return learnsets;
	}
	public static void prePopLearnsets(Context context){
		new Thread(new Runnable() {
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				PokemonDatabase db = PokemonDatabase.getDatabase(context);
				LearnsetDao dao = db.learnsetDao();
				
				List<LearnsetEntity> list = parseLearnset(context);
				LearnsetEntity[] array = list.toArray(new LearnsetEntity[list.size()]);
				
				dao.insert(array);
				
				long duration = System.currentTimeMillis() - startTime; // Calculate duration
				Log.d("PrePop", String.format("Populated Learnsets in %d milliseconds", duration));
			}
		}).start();
	}
	
	public static ArrayList<PokemonSet> getDefaultTeam(Context context) {
		PokemonDatabase db = PokemonDatabase.getDatabase(context);
		PokemonDao dao = db.pokemonDao();
		MoveDao dao2 = db.moveDao();
		
		ArrayList<PokemonSet> sets = new ArrayList<>();
		ArrayList<PokemonEntity> mons = (ArrayList<PokemonEntity>) dao.getPokemonLimit(6);
		ArrayList<MoveEntity> moves = (ArrayList<MoveEntity>) dao2.getMovesLimit(3);
		for(PokemonEntity mon : mons){
			PokemonSet set = new PokemonSet(mon);
			set.moves = moves;
			sets.add(set);
		}
		return sets;
	}
}
