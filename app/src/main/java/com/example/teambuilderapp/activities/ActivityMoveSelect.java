package com.example.teambuilderapp.activities;

import static com.example.teambuilderapp.SearchHandling.getLearnset;
import static com.example.teambuilderapp.SearchHandling.itemSearch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.teambuilderapp.MoveViewAdapter;
import com.example.teambuilderapp.PokemonSet;
import com.example.teambuilderapp.R;
import com.example.teambuilderapp.RecyclerViewInterface;
import com.example.teambuilderapp.RosterViewAdapter;
import com.example.teambuilderapp.database.ItemDescription;
import com.example.teambuilderapp.database.MoveEntity;

import java.util.ArrayList;

public class ActivityMoveSelect extends AppCompatActivity implements RecyclerViewInterface {
	private String TAG = "oogabooga";
	Intent intent;
	PokemonSet currentPokemon;
	int currentSlot = 0;
	
	EditText[] moveSearchBars = new EditText[4];
	Button doneMoveSelect;
	Button filterMoves;
	RecyclerView moveResults;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_move_select);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		
		setViews();
		//set moves from intent
		intent = getIntent();
		currentPokemon = (PokemonSet) intent.getSerializableExtra("pokemon");
		if(currentPokemon != null){
			for(int i = 0; i < 4; i++){
				if(i < currentPokemon.moves.size()){
					moveSearchBars[i].setText(currentPokemon.moves.get(i).name);
				} else {
					moveSearchBars[i].setText("");
				}
			}
			Log.d(TAG, currentPokemon.toString());
		} else {
			finish();
		}
		
		//set adapter
		inflateAdapter("");
		//set edittext listeners
		for(int i = 0; i < 4; i++){
			int finalI = i;
			moveSearchBars[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if(hasFocus){
						currentSlot = finalI;
						setViews();
					}
				}
			});
		}
		
	}
	
	public void inflateAdapter(String s){
		ArrayList<MoveEntity> moves = getLearnset(currentPokemon.speciesData.pokemon, getApplicationContext());
		MoveViewAdapter adapter = new MoveViewAdapter(this, moves, this);
		moveResults.setAdapter(adapter);
		moveResults.setLayoutManager(new LinearLayoutManager(this));
	}
	
	private void setViews(){
		moveSearchBars[0] = findViewById(R.id.move1SearchBar);
		moveSearchBars[1] = findViewById(R.id.move2SearchBar);
		moveSearchBars[2] = findViewById(R.id.move3SearchBar);
		moveSearchBars[3] = findViewById(R.id.move4SearchBar);
		doneMoveSelect = findViewById(R.id.doneMoveSelect);
		filterMoves = findViewById(R.id.filterMoves);
		moveResults = findViewById(R.id.moveResults);
		moveSearchBars[0].setBackgroundColor(Color.WHITE);
		moveSearchBars[1].setBackgroundColor(Color.WHITE);
		moveSearchBars[2].setBackgroundColor(Color.WHITE);
		moveSearchBars[3].setBackgroundColor(Color.WHITE);
		moveSearchBars[currentSlot].setBackgroundColor(Color.GREEN);
	}
	
	public void setDoneMoveSelect(View v){
		intent.putExtra("pokemon", currentPokemon);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
	
	@Override
	public void onSetClick(int position) {
		MoveViewAdapter adapter = (MoveViewAdapter) moveResults.getAdapter();
		if (adapter != null) {
			MoveEntity move = adapter.moves.get(position);
			if(currentPokemon != null){
				if(currentSlot > currentPokemon.moves.size() - 1){
					currentSlot = currentPokemon.moves.size();
					currentPokemon.moves.add(move);
					moveSearchBars[currentSlot].setText(move.name);
					moveSearchBars[0].setBackgroundColor(Color.WHITE);
					moveSearchBars[1].setBackgroundColor(Color.WHITE);
					moveSearchBars[2].setBackgroundColor(Color.WHITE);
					moveSearchBars[3].setBackgroundColor(Color.WHITE);
					moveSearchBars[currentSlot].setBackgroundColor(Color.GREEN);
				} else {
					currentPokemon.moves.set(currentSlot, move);
					moveSearchBars[currentSlot].setText(move.name);
				}
				
			}
		}
	}
}