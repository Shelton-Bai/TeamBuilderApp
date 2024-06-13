package com.example.teambuilderapp.activities;

import static com.example.teambuilderapp.SearchHandling.pokemonSearch;
import static com.example.teambuilderapp.SearchHandling.itemSearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teambuilderapp.DBPrePop;
import com.example.teambuilderapp.PokemonSet;
import com.example.teambuilderapp.database.PokemonDao;
import com.example.teambuilderapp.database.PokemonDatabase;
import com.example.teambuilderapp.database.PokemonEntity;
import com.example.teambuilderapp.PokemonViewAdapter;
import com.example.teambuilderapp.R;
import com.example.teambuilderapp.SearchResultViewInterface;

import java.util.ArrayList;

public class ActivityPokemonBuilder extends AppCompatActivity implements SearchResultViewInterface {
	private String TAG = "oogabooga";
	enum listType {
		POKEMON,
		ITEM,
		ABILITY
	}
	
	PokemonSet currentPokemon;
	String format;
	listType currList;
	RecyclerView searchResults;
	EditText pokemonSearch;
	EditText itemSearch;
	EditText abilitySearch;
	Spinner sortDropdown;
	CheckBox reverseSort;
	
	//temp
	PokemonDatabase db;
	PokemonDao dao;
	
	ActivityResultLauncher<Intent> pokemonEditLauncher = registerForActivityResult(
			//launcher for editing moves, stats and details, just sets the pokemon to the new pokemon made in the views
			new ActivityResultContracts.StartActivityForResult(),
			result -> {
				if (result.getResultCode() == Activity.RESULT_OK){
					Intent data = result.getData();
					currentPokemon = (PokemonSet) data.getSerializableExtra("pokemon");
					Log.d(TAG, currentPokemon.toString());
				}
			}
	);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_pokemon_builder);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		
		//temp
		db = PokemonDatabase.getDatabase(getApplicationContext());
		dao = db.pokemonDao();
		DBPrePop.prePopPokemon(getApplicationContext());
		
		searchResults = findViewById(R.id.searchResults);
		pokemonSearch = findViewById(R.id.pokemonSearchBar);
		itemSearch = findViewById(R.id.itemSearchBar);
		abilitySearch = findViewById(R.id.abilitySearchBar);
		sortDropdown = findViewById(R.id.sortDropdown);
		reverseSort = findViewById(R.id.reverseSort);
		
		//set items in drop down
		String[] sortMethods = new String[]{"alpha", "bst", "stats"};
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sortMethods);
		sortDropdown.setAdapter(adapter);
		
		//set focus listeners for search fields
		pokemonSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					onPokemonSearchClick(pokemonSearch);
				}
			}
		});
		itemSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					onItemSearchClick(itemSearch);
				}
			}
		});
		
		currList = listType.POKEMON;
		ArrayList<PokemonEntity> mons = pokemonSearch("", getApplicationContext());
		PokemonViewAdapter pokemonAdapter = new PokemonViewAdapter(this, mons, this);
		searchResults.setAdapter(pokemonAdapter);
		searchResults.setLayoutManager(new LinearLayoutManager(this));
		
	}
	
	
	public void onPokemonSearchClick(View v){
		currList = listType.POKEMON;
		ArrayList<PokemonEntity> mons = pokemonSearch("", getApplicationContext());
		PokemonViewAdapter adapter = new PokemonViewAdapter(this, mons, this);
		searchResults.setAdapter(adapter);
		searchResults.setLayoutManager(new LinearLayoutManager(this));
	}
	
	public void onItemSearchClick(View v){
		
		ArrayList<String> items = itemSearch("");
		
	}
	
	public void onAbilitySearchClick(View v){
	
	}
	
	public void onMovesButtonClick(View v){
		if(currentPokemon == null){
			Toast.makeText(this, "Select A Pokemon First!", Toast.LENGTH_LONG).show();
		} else {
			Intent editMoves = new Intent(this, ActivityMoves.class);
			editMoves.putExtra("pokemon", currentPokemon);
			pokemonEditLauncher.launch(editMoves);
		}
	}
	public void onStatsButtonClick(View v){
		if(currentPokemon == null){
			Toast.makeText(this, "Select A Pokemon First!", Toast.LENGTH_LONG).show();
		} else {
			Intent editStats = new Intent(this, ActivityStats.class);
			editStats.putExtra("pokemon", currentPokemon);
			pokemonEditLauncher.launch(editStats);
		}
	}
	public void onDetailsButtonClick(View v){
		if(currentPokemon == null){
			Toast.makeText(this, "Select A Pokemon First!", Toast.LENGTH_LONG).show();
		} else {
			Intent editDetails = new Intent(this, ActivityDetails.class);
			editDetails.putExtra("pokemon", currentPokemon);
			pokemonEditLauncher.launch(editDetails);
		}
	}
	public void onFilterButtonClick(View v){
		
		PokemonEntity mon1 = new PokemonEntity("shabingus");
		mon1.type1 = "Fire";
		mon1.type2 = "Grass";
		mon1.hp = 69;
		mon1.atk = 69;
		mon1.def = 69;
		mon1.spa = 69;
		mon1.spd = 69;
		mon1.spe = 69;
		mon1.ability0 = "fuck u";
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				dao.insertPokemon(mon1);
				Log.d(TAG, "new mon added");
			}
		}).start();
		
		if(currentPokemon != null){
			Log.d(TAG, currentPokemon.toString());
			
		}
	}
	
	@Override
	public void onResultClick(int position) {
		Log.d(TAG, "search result: item clicked");
		switch (currList){
			case POKEMON:
				PokemonViewAdapter adapter = (PokemonViewAdapter) searchResults.getAdapter();
				if (adapter != null) {
					PokemonEntity mon = adapter.mons.get(position);
					currentPokemon = new PokemonSet(mon.name);
					currentPokemon.ability = mon.ability0;
					Log.d(TAG, currentPokemon.toString());
					pokemonSearch.setText(mon.name);
				}
				
				break;
			case ITEM:
				Log.d(TAG, "onItemClick: item clicked");
				break;
			case ABILITY:
				break;
		}
	}
	
	
}