package com.example.teambuilderapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.teambuilderapp.Pokemon;
import com.example.teambuilderapp.R;

public class ActivityStats extends AppCompatActivity {
	private String TAG = "oogabooga2";
	Intent intent;
	Pokemon current;
	int currstat;
	int usedEVs;
	int plusStat;
	int minusStat;
	
	//views
	View editStats;
	TextView statLabel;
	TextView remainingEVs;
	TextView baseStatLabel;
	TextView finalStatLabel;
	RadioButton plusNature;
	RadioButton minusNature;
	EditText evField;
	EditText ivField;
	SeekBar evBar;
	SeekBar ivBar;
	View[] displayViews = new View[6];
	TextView[] displayNames = new TextView[6];
	ProgressBar[] displayBars = new ProgressBar[6];
	Button backToPokemon;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_stats);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		
		intent = getIntent();
		
		current = (Pokemon) intent.getSerializableExtra("pokemon");
		
		if(current == null){
			finish();
		}
		
		//get the data from the pokemon
		readInPokemon();
		//set all the views to the right ids
		setAllViews();
		
		//start with hp
		switchCurrStat(displayViews[0], 0);
		
	}
	
	private void readInPokemon(){
		usedEVs = 0;
		for(int stat : current.evs){
			usedEVs += stat;
		}
		int[] natureStats = Pokemon.getStatsFromNature(current.nature);
		if(natureStats != null){
			plusStat = natureStats[0];
			minusStat = natureStats[1];
		} else { //set to quirky!
			plusStat = 4;
			minusStat = 4;
		}
	}
	
	private void setAllViews(){
		editStats = findViewById(R.id.editStatBlock);
		statLabel = editStats.findViewById(R.id.statLabel);
		remainingEVs = editStats.findViewById(R.id.remainingEVs);
		baseStatLabel = editStats.findViewById(R.id.baseStatLabel);
		finalStatLabel = editStats.findViewById(R.id.statValueLabel);
		plusNature = editStats.findViewById(R.id.naturePlus);
		minusNature = editStats.findViewById(R.id.natureMinus);
		evField = editStats.findViewById(R.id.evField);
		ivField = editStats.findViewById(R.id.ivField);
		evBar = editStats.findViewById(R.id.evBar);
		ivBar = editStats.findViewById(R.id.ivBar);
		
		displayViews[0] = findViewById(R.id.hpDisplay);
		displayNames[0] = displayViews[0].findViewById(R.id.statName);
		displayBars[0] = displayViews[0].findViewById(R.id.statBar);
		displayViews[1] = findViewById(R.id.atkDisplay);
		displayNames[1] = displayViews[1].findViewById(R.id.statName);
		displayBars[1] = displayViews[1].findViewById(R.id.statBar);
		displayViews[2] = findViewById(R.id.defDisplay);
		displayNames[2] = displayViews[2].findViewById(R.id.statName);
		displayBars[2] = displayViews[2].findViewById(R.id.statBar);
		displayViews[3] = findViewById(R.id.spaDisplay);
		displayNames[3] = displayViews[3].findViewById(R.id.statName);
		displayBars[3] = displayViews[3].findViewById(R.id.statBar);
		displayViews[4] = findViewById(R.id.spdDisplay);
		displayNames[4] = displayViews[4].findViewById(R.id.statName);
		displayBars[4] = displayViews[4].findViewById(R.id.statBar);
		displayViews[5] = findViewById(R.id.speDisplay);
		displayNames[5] = displayViews[5].findViewById(R.id.statName);
		displayBars[5] = displayViews[5].findViewById(R.id.statBar);
		
		backToPokemon = findViewById(R.id.backToPokemonFromStats);
		
		//set text
		remainingEVs.setText(String.format("EVs Left: %d", 510 - usedEVs));
		
		displayNames[0].setText("HP");
		displayNames[1].setText("Attack");
		displayNames[2].setText("Defense");
		displayNames[3].setText("Special Attack");
		displayNames[4].setText("Special Defense");
		displayNames[5].setText("Speed");
		
		//set on click function to switch stats
		displayViews[0].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switchCurrStat(v, 0);
			}
		});
		displayViews[1].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switchCurrStat(v, 1);
			}
		});
		displayViews[2].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switchCurrStat(v, 2);
			}
		});
		displayViews[3].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switchCurrStat(v, 3);
			}
		});
		displayViews[4].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switchCurrStat(v, 4);
			}
		});
		displayViews[5].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switchCurrStat(v, 5);
			}
		});
		
		evBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if(fromUser){
					evField.setText(progress + "");
				}
				current.evs[currstat] = progress;
				calcEVsLeft();
				calcStatBar();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			
			}
		});
		evField.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				int ev;
				if(s.toString().equals("")){
					ev = 0;
				} else {
					ev = Integer.parseInt(s.toString());
				}
				
				if(ev >= 0 && ev <= 252){
					evBar.setProgress(ev);
				}
				if(ev > 252){
					evField.setText("252");
				}
				calcStatBar();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			
			}
		});
		
		ivBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if(fromUser){
					ivField.setText(progress + "");
				}
				current.ivs[currstat] = progress;
				calcStatBar();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			
			}
		});
		ivField.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				int iv;
				if(s.toString().equals("")){
					iv = 0;
				} else {
					iv = Integer.parseInt(s.toString());
				}
				
				if(iv <= 31 && iv >= 0){
					ivBar.setProgress(iv);
				}
				if(iv > 31){
					ivField.setText("31");
				}
				calcStatBar();
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			
			}
		});
		
		plusNature.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				plusStat = currstat;
				calcStatBar();
			}
		});
		minusNature.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				minusStat = currstat;
				calcStatBar();
			}
		});
		
		calcStatBar();
		
	}
	
	public void switchCurrStat(View v, int stat){
		
		currstat = stat;
		
		statLabel.setText(displayNames[stat].getText());
		
		if(stat == 0){
			plusNature.setVisibility(View.GONE);
			minusNature.setVisibility(View.GONE);
		} else {
			plusNature.setVisibility(View.VISIBLE);
			minusNature.setVisibility(View.VISIBLE);
		}
		
		plusNature.setChecked(stat == plusStat);
		minusNature.setChecked(stat == minusStat);
		
		if(current != null) {
			evField.setText(current.evs[stat] + "");
			evBar.setProgress(current.evs[stat]);
			ivField.setText(current.ivs[stat] + "");
			ivBar.setProgress(current.ivs[stat]);
		}
	}
	
	public void calcEVsLeft(){
		usedEVs = 0;
		for(int stat : current.evs){
			usedEVs += stat;
		}
		remainingEVs.setText(String.format("EVs Left: %d", 510 - usedEVs));
	}
	
	public void calcStatBar(){
		Log.d(TAG, "calcStatBar: finish this later i guess");
	}
	
	public void backToPokemon(View v){
		current.nature = Pokemon.getNatureFromStats(plusStat, minusStat);
		intent.putExtra("pokemon", current);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
	
	
}