package com.example.teambuilderapp.activities;

import static com.example.teambuilderapp.SearchHandling.pokemonSearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teambuilderapp.PokemonSet;
import com.example.teambuilderapp.PokemonViewAdapter;
import com.example.teambuilderapp.R;
import com.example.teambuilderapp.database.PokemonEntity;
import com.example.teambuilderapp.pokemonListViewInterface;

import java.util.ArrayList;

public class ActivityPokemonSelect extends AppCompatActivity implements pokemonListViewInterface {
	private String TAG = "oogabooga";
	Intent intent;
	PokemonSet currentPokemon;
	
	EditText pokemonSearchBar;
	Button donePokemonSelect;
	Button filterPokemon;
	RecyclerView pokemonResults;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_pokemon_select);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		
		pokemonSearchBar = findViewById(R.id.pokemonSearchBar);
		pokemonResults = findViewById(R.id.pokemonResults);
		donePokemonSelect = findViewById(R.id.donePokemonSelect);
		filterPokemon = findViewById(R.id.filterPokemon);
		
		intent = getIntent();
		currentPokemon = (PokemonSet) intent.getSerializableExtra("pokemon");
		if(currentPokemon != null){
			pokemonSearchBar.setText(currentPokemon.species);
			Log.d(TAG, currentPokemon.toString());
		}
		
		ArrayList<PokemonEntity> mons = pokemonSearch("", getApplicationContext());
		PokemonViewAdapter pokemonAdapter = new PokemonViewAdapter(this, mons, this);
		pokemonResults.setAdapter(pokemonAdapter);
		pokemonResults.setLayoutManager(new LinearLayoutManager(this));
		
	}
	
	@Override
	public void onResultClick(int position) {
		PokemonViewAdapter adapter = (PokemonViewAdapter) pokemonResults.getAdapter();
		if (adapter != null) {
			PokemonEntity mon = adapter.mons.get(position);
			currentPokemon = new PokemonSet(mon);
			currentPokemon.ability = mon.ability0;
			Log.d(TAG, currentPokemon.toString());
			pokemonSearchBar.setText(mon.name);
			intent.putExtra("pokemon", currentPokemon);
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	}
	
	public void setDonePokemonSelect(View v){
		intent.putExtra("pokemon", currentPokemon);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
}