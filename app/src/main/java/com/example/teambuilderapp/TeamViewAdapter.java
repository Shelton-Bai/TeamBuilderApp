package com.example.teambuilderapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teambuilderapp.database.ItemDescription;
import com.example.teambuilderapp.database.ItemEntity;
import com.example.teambuilderapp.database.PokemonEntity;

import java.util.ArrayList;

public class TeamViewAdapter extends RecyclerView.Adapter<TeamViewAdapter.RecyclerViewHolder>{
	private final RecyclerViewInterface recyclerInterface;
	Context context;
	public ArrayList<PokemonTeam> teams;
	
	public TeamViewAdapter(Context context, ArrayList<PokemonTeam> teams, RecyclerViewInterface recyclerInterface){
		this.context = context;
		this.teams = teams;
		this.recyclerInterface = recyclerInterface;
	}
	
	@NonNull
	@Override
	public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.team_view_layout, parent, false);
		
		return new TeamViewAdapter.RecyclerViewHolder(view, recyclerInterface);
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
		PokemonTeam team = teams.get(position);
		
		if(team != null){
			Log.d("soem", team.name);
			holder.teamNameDisplay.setText(team.name);
			for(int i = 0; i < 6; i++){
				if(i < team.roster.size()){
					RosterViewAdapter.configTypeImage(holder.members[i], team.roster.get(i).speciesData.type1);
				} else {
					holder.members[i].setVisibility(View.INVISIBLE);
				}
			}
		}
		
		//set sprites
		//TODO
		
		
	}
	
	
	@Override
	public int getItemCount() {
		return teams.size();
	}
	
	public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
		
		TextView teamNameDisplay;
		ImageView[] members = new ImageView[6];
		
		public RecyclerViewHolder(@NonNull View teamView, RecyclerViewInterface recyclerInterface) {
			super(teamView);
			
			teamNameDisplay = teamView.findViewById(R.id.teamNameDisplay);
			members[0] = teamView.findViewById(R.id.member1);
			members[1] = teamView.findViewById(R.id.member2);
			members[2] = teamView.findViewById(R.id.member3);
			members[3] = teamView.findViewById(R.id.member4);
			members[4] = teamView.findViewById(R.id.member5);
			members[5] = teamView.findViewById(R.id.member6);
			
			teamView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(recyclerInterface != null){
						int position = getAdapterPosition();
						if(position != RecyclerView.NO_POSITION){
							recyclerInterface.onSetClick(position);
						}
					}
				}
			});
			
		}
	}
	
}
