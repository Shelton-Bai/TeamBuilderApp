package com.example.teambuilderapp.activities;

import static com.example.teambuilderapp.SearchHandling.itemSearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teambuilderapp.ItemViewAdapter;
import com.example.teambuilderapp.PokemonSet;
import com.example.teambuilderapp.PokemonTeam;
import com.example.teambuilderapp.R;
import com.example.teambuilderapp.RecyclerViewInterface;
import com.example.teambuilderapp.RosterViewAdapter;
import com.example.teambuilderapp.TeamViewAdapter;
import com.example.teambuilderapp.database.ItemDescription;

import java.util.ArrayList;

public class ActivityTeams extends AppCompatActivity implements RecyclerViewInterface {
	private String TAG = "oogabooga";
	public final int EDIT_TEAM = 1;
	public final int MAKE_TEAM = 0;
	
	ArrayList<PokemonTeam> teams = new ArrayList<>();
	
	RecyclerView teamList;
	
	ActivityResultLauncher<Intent> teambuilderLauncher = registerForActivityResult(
			new ActivityResultContracts.StartActivityForResult(),
			result -> {
				if (result.getResultCode() == Activity.RESULT_OK){
					Intent data = result.getData();
					PokemonTeam team;
					if(data.getSerializableExtra("team") != null){
						team = (PokemonTeam) data.getSerializableExtra("team");
						
					} else {
						team = null;
					}
					
					if(data.getIntExtra("position", -1) != -1){
						int position = data.getIntExtra("position", -1);
						if(team != null){
							teams.set(position, team);
							inflateAdapter("");
							Log.d(TAG, "done: ");
						}
					}
					
//					if(team != null){
//						Log.d(TAG, "team: " + team.toString());
//					} else {
//						Log.d(TAG, "team is null");
//					}
				
				}
			}
	);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_teams);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		
		teamList = findViewById(R.id.teamList);
		
		inflateAdapter("");
	}
	
	public void inflateAdapter(String s){
		TeamViewAdapter adapter = new TeamViewAdapter(this, teams, this);
		teamList.setAdapter(adapter);
		teamList.setLayoutManager(new LinearLayoutManager(this));
	}
	
	public void newTeam(View v){
		PokemonTeam newTeam = new PokemonTeam("New Team");
		teams.add(newTeam);
		Intent intent = new Intent(this, ActivityTeambuilder.class);
		intent.putExtra("team", newTeam);
		intent.putExtra("position", teams.size() - 1);
		teambuilderLauncher.launch(intent);
	}
	
	public void editTeam(PokemonTeam team, int index){
		Log.d(TAG, "onClick: editteam");
		Intent intent = new Intent(getApplicationContext(), ActivityTeambuilder.class);
		intent.putExtra("action", "edit");
		intent.putExtra("index", index);
		intent.putExtra("team", team);
		teambuilderLauncher.launch(intent);
	}
	
	@Override
	public void onSetClick(int position) {
		
		TeamViewAdapter adapter = (TeamViewAdapter) teamList.getAdapter();
		if (adapter != null) {
			PokemonTeam team = adapter.teams.get(position);
			
			Intent intent = new Intent(getApplicationContext(), ActivityTeambuilder.class);
			intent.putExtra("team", team);
			intent.putExtra("position", position);
			teambuilderLauncher.launch(intent);
			
		}
		
	}
}