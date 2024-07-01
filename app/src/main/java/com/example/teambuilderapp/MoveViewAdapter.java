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
import com.example.teambuilderapp.database.MoveDescriptionDao;
import com.example.teambuilderapp.database.MoveEntity;
import com.example.teambuilderapp.database.PokemonDatabase;
import com.example.teambuilderapp.database.PokemonEntity;

import java.util.ArrayList;

public class MoveViewAdapter extends RecyclerView.Adapter<MoveViewAdapter.RecyclerViewHolder>{
	private final RecyclerViewInterface recyclerInterface;
	Context context;
	public ArrayList<MoveEntity> moves;
	PokemonDatabase db;
	MoveDescriptionDao dao;
	
	public MoveViewAdapter(Context context, ArrayList<MoveEntity> moves, RecyclerViewInterface recyclerInterface){
		this.context = context;
		this.moves = moves;
		this.recyclerInterface = recyclerInterface;
		this.db = PokemonDatabase.getDatabase(context);
		this.dao = db.moveDescriptionDao();
	}
	
	@NonNull
	@Override
	public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.move_display_layout, parent, false);
		
		return new MoveViewAdapter.RecyclerViewHolder(view, recyclerInterface);
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
		//set values of views in pokemon_teamsheet_layout (defined in the rosterviewholder class)
		MoveEntity move = moves.get(position);
		
		if(move == null){
			holder.moveName.setText("No move");
			holder.moveDescription.setText("No Description");
			return;
		}
		holder.moveName.setText(move.name);
		holder.moveDescription.setText(dao.getMoveDescription(move.move).shortDesc);
		holder.movePower.setText(String.format("Power: %d", move.power));
		holder.moveAccuracy.setText(String.format("Accuracy: %d", move.accuracy));
		holder.movePP.setText(String.format("PP: %d", move.pp));
		
		RosterViewAdapter.configTypeImage(holder.moveType, move.type);
		
	}
	
	
	@Override
	public int getItemCount() {
		return moves.size();
	}
	
	public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
		
		TextView moveName;
		TextView moveDescription;
		TextView movePower;
		TextView moveAccuracy;
		TextView movePP;
		ImageView moveType;
		ImageView moveCategory;
		
		public RecyclerViewHolder(@NonNull View moveView, RecyclerViewInterface recyclerInterface) {
			super(moveView);
			
			moveName = moveView.findViewById(R.id.moveName);
			moveDescription = moveView.findViewById(R.id.moveDescription);
			movePower = moveView.findViewById(R.id.movePower);
			moveAccuracy = moveView.findViewById(R.id.moveAccuracy);
			movePP = moveView.findViewById(R.id.movePP);
			moveType = moveView.findViewById(R.id.moveType);
			moveCategory = moveView.findViewById(R.id.moveCategory);
			
			
			itemView.setOnClickListener(new View.OnClickListener() {
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
