package com.example.teambuilderapp.activities;

import static com.example.teambuilderapp.SearchHandling.itemSearch;
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

import com.example.teambuilderapp.ItemViewAdapter;
import com.example.teambuilderapp.PokemonSet;
import com.example.teambuilderapp.PokemonViewAdapter;
import com.example.teambuilderapp.R;
import com.example.teambuilderapp.RecyclerViewInterface;
import com.example.teambuilderapp.database.ItemDescription;
import com.example.teambuilderapp.database.PokemonEntity;

import java.util.ArrayList;

public class ActivityItemSelect extends AppCompatActivity implements RecyclerViewInterface {
	private String TAG = "oogabooga";
	Intent intent;
	PokemonSet currentPokemon;
	
	EditText itemSearchBar;
	Button doneItemSelect;
	RecyclerView itemResults;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_item_select);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		
		itemSearchBar = findViewById(R.id.itemSearchBar);
		doneItemSelect = findViewById(R.id.doneItemSelect);
		itemResults = findViewById(R.id.itemResults);
		
		intent = getIntent();
		currentPokemon = (PokemonSet) intent.getSerializableExtra("pokemon");
		if(currentPokemon != null){
			itemSearchBar.setText(currentPokemon.item);
			Log.d(TAG, currentPokemon.toString());
		}
		
		inflateAdapter("");
	}
	
	public void inflateAdapter(String s){
		ArrayList<ItemDescription> items = itemSearch(s, getApplicationContext());
		ItemViewAdapter adapter = new ItemViewAdapter(this, items, this);
		itemResults.setAdapter(adapter);
		itemResults.setLayoutManager(new LinearLayoutManager(this));
	}
	
	@Override
	public void onSetClick(int position) {
		ItemViewAdapter adapter = (ItemViewAdapter) itemResults.getAdapter();
		if (adapter != null) {
			ItemDescription item = adapter.items.get(position);
			if(currentPokemon != null){
				currentPokemon.item = item.name;
				Log.d(TAG, currentPokemon.toString());
			}
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