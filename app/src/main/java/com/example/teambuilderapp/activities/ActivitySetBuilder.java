package com.example.teambuilderapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.teambuilderapp.DBPrePop;
import com.example.teambuilderapp.PokemonSet;
import com.example.teambuilderapp.R;
import com.example.teambuilderapp.RosterViewAdapter;

public class ActivitySetBuilder extends AppCompatActivity {
	private String TAG = "oogabooga";
	
	//stuff from intent
	Intent intent;
	PokemonSet currSet;
	int position;
	
	//view components
	TextView psetSpecies;
	TextView psetAbility;
	TextView psetItem;
	ImageView psetSprite;
	ImageView psetType1;
	ImageView psetType2;
	TextView[] psetMoves = new TextView[4];
	ImageView[] psetMoveTypes = new ImageView[4];
	View[] statViews = new View[6];
	TextView[] statNames = new TextView[6];
	ProgressBar[] statBars = new ProgressBar[6];
	Button choosePokemon;
	Button chooseAbility;
	Button chooseItem;
	Button editDetails;
	Button editMoves;
	Button editStats;
	
	ActivityResultLauncher<Intent> pokemonEditLauncher = registerForActivityResult(
			//launcher for editing moves, stats and details, just sets the pokemon to the new pokemon made in the views
			new ActivityResultContracts.StartActivityForResult(),
			result -> {
				if (result.getResultCode() == Activity.RESULT_OK){
					Intent data = result.getData();
					currSet = (PokemonSet) data.getSerializableExtra("pokemon");
					if (currSet != null) {
						Log.d(TAG, currSet.toString());
					}
					setPokemonInfo();
				}
			}
	);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_set_builder);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		
		intent = getIntent();
		currSet = (PokemonSet) intent.getSerializableExtra("set");
		position = intent.getIntExtra("position", -1);
		
		setAllViews();
		
		setPokemonInfo();
	}
	
	public void doneEditingPokemon(View v){
		intent.putExtra("set", currSet);
		intent.putExtra("position", position);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
	
	public void onChoosePokemonClick(View v){
		Intent choosePokemon = new Intent(this, ActivityPokemonSelect.class);
		choosePokemon.putExtra("pokemon", currSet);
		pokemonEditLauncher.launch(choosePokemon);
	}
	public void onStatsButtonClick(View v){
		if(currSet == null){
			Toast.makeText(this, "Select A Pokemon First!", Toast.LENGTH_LONG).show();
		} else {
			Intent editStats = new Intent(this, ActivityStats.class);
			editStats.putExtra("pokemon", currSet);
			pokemonEditLauncher.launch(editStats);
		}
	}
	
	public void onItemButtonClick(View v){
		if(currSet == null){
			Toast.makeText(this, "Select A Pokemon First!", Toast.LENGTH_LONG).show();
		} else {
			Intent editItem = new Intent(this, ActivityItemSelect.class);
			editItem.putExtra("pokemon", currSet);
			pokemonEditLauncher.launch(editItem);
		}
	}
	
	public void onAbilityButtonClick(View v){
		if(currSet == null){
			Toast.makeText(this, "Select A Pokemon First!", Toast.LENGTH_LONG).show();
		} else {
			Intent editAbility = new Intent(this, ActivityAbilitySelect.class);
			editAbility.putExtra("pokemon", currSet);
			pokemonEditLauncher.launch(editAbility);
		}
	}
	
	public void onMovesButtonClick(View v){
		if(currSet == null){
			Toast.makeText(this, "Select A Pokemon First!", Toast.LENGTH_LONG).show();
		} else {
			Intent editMoves = new Intent(this, ActivityMoveSelect.class);
			editMoves.putExtra("pokemon", currSet);
			pokemonEditLauncher.launch(editMoves);
		}
	}
	
	private void setPokemonInfo(){
		if(currSet != null){
			psetSpecies.setText(currSet.species);
			psetAbility.setText(currSet.ability);
			psetItem.setText(currSet.item == null ? "No Item" : currSet.item);
			RosterViewAdapter.configTypeImage(psetType1, currSet.speciesData.type1);
			RosterViewAdapter.configTypeImage(psetType2, currSet.speciesData.type2);
			for(int i = 0; i < 4; i++){
				if(i < currSet.moves.size()){
					Log.d(TAG, currSet.moves.get(i).name);
					psetMoves[i].setText(currSet.moves.get(i).name);
					psetMoves[i].setVisibility(View.VISIBLE);
					RosterViewAdapter.configTypeImage(psetMoveTypes[i], currSet.moves.get(i).type);
				} else {
					psetMoves[i].setVisibility(View.INVISIBLE);
					psetMoveTypes[i].setVisibility(View.INVISIBLE);
				}
			}
			for(int i = 0; i < 6; i++){
				statBars[i].setProgress(currSet.evs[i]);
				Log.d(TAG, "" + statBars[i].getProgress());
			}
		} else {
			psetSpecies.setText("Select Pokemon");
			psetAbility.setText("Select Pokemon");
			psetItem.setText("Select Pokemon");
			psetType1.setVisibility(View.INVISIBLE);
			psetType2.setVisibility(View.INVISIBLE);
			psetSprite.setVisibility(View.INVISIBLE);
			for(int i = 0; i < 4; i++){
				psetMoves[i].setVisibility(View.INVISIBLE);
				psetMoveTypes[i].setVisibility(View.INVISIBLE);
			}
			for(int i = 0; i < 6; i++){
				statBars[i].setProgress(0);
			}
		}
	}
	private void setAllViews(){
		psetSpecies = findViewById(R.id.psetSpecies);
		psetAbility = findViewById(R.id.psetAbility);
		psetItem = findViewById(R.id.psetItem);
		psetSprite = findViewById(R.id.psetSprite);
		psetType1 = findViewById(R.id.psetType1);
		psetType2 = findViewById(R.id.psetType2);
		psetMoves[0] = findViewById(R.id.psetMove1);
		psetMoves[1] = findViewById(R.id.psetMove2);
		psetMoves[2] = findViewById(R.id.psetMove3);
		psetMoves[3] = findViewById(R.id.psetMove4);
		psetMoveTypes[0] = findViewById(R.id.psetMove1Type);
		psetMoveTypes[1] = findViewById(R.id.psetMove2Type);
		psetMoveTypes[2] = findViewById(R.id.psetMove3Type);
		psetMoveTypes[3] = findViewById(R.id.psetMove4Type);
		statViews[0] = findViewById(R.id.hpStatBar);
		statNames[0] = statViews[0].findViewById(R.id.statNameSmall);
		statBars[0] = statViews[0].findViewById(R.id.statBarSmall);
		statViews[1] = findViewById(R.id.atkStatBar);
		statNames[1] = statViews[1].findViewById(R.id.statNameSmall);
		statBars[1] = statViews[1].findViewById(R.id.statBarSmall);
		statViews[2] = findViewById(R.id.defStatBar);
		statNames[2] = statViews[2].findViewById(R.id.statNameSmall);
		statBars[2] = statViews[2].findViewById(R.id.statBarSmall);
		statViews[3] = findViewById(R.id.spaStatBar);
		statNames[3] = statViews[3].findViewById(R.id.statNameSmall);
		statBars[3] = statViews[3].findViewById(R.id.statBarSmall);
		statViews[4] = findViewById(R.id.spdStatBar);
		statNames[4] = statViews[4].findViewById(R.id.statNameSmall);
		statBars[4] = statViews[4].findViewById(R.id.statBarSmall);
		statViews[5] = findViewById(R.id.speStatBar);
		statNames[5] = statViews[5].findViewById(R.id.statNameSmall);
		statBars[5] = statViews[5].findViewById(R.id.statBarSmall);
		choosePokemon = findViewById(R.id.choosePokemon);
		chooseAbility = findViewById(R.id.chooseAbility);
		chooseItem = findViewById(R.id.chooseItem);
		editDetails = findViewById(R.id.editDetails);
		editMoves = findViewById(R.id.editMoves);
		editStats = findViewById(R.id.editStats);
		statNames[0].setText("HP");
		statNames[1].setText("Attack");
		statNames[2].setText("Defense");
		statNames[3].setText("Special Attack");
		statNames[4].setText("Special Defense");
		statNames[5].setText("Speed");
	}
}