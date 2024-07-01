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

public class ItemViewAdapter extends RecyclerView.Adapter<ItemViewAdapter.RecyclerViewHolder>{
	private final RecyclerViewInterface recyclerInterface;
	Context context;
	public ArrayList<ItemDescription> items;
	
	public ItemViewAdapter(Context context, ArrayList<ItemDescription> items, RecyclerViewInterface recyclerInterface){
		this.context = context;
		this.items = items;
		this.recyclerInterface = recyclerInterface;
	}
	
	@NonNull
	@Override
	public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.ability_display_layout, parent, false);
		
		return new ItemViewAdapter.RecyclerViewHolder(view, recyclerInterface);
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
		//set values of views in pokemon_teamsheet_layout (defined in the rosterviewholder class)
		ItemDescription item = items.get(position);
		
		if(item == null){
			holder.elementName.setText("No Item");
			holder.elementDescription.setText("No Description");
			return;
		}
		Log.d(item.name, item.item);
		holder.elementName.setText(item.name);
		holder.elementDescription.setText(item.desc);
		
		//set sprites
		//TODO
		
		
	}
	
	
	@Override
	public int getItemCount() {
		return items.size();
	}
	
	public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
		
		TextView elementName;
		TextView elementDescription;
		
		public RecyclerViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerInterface) {
			super(itemView);
			
			elementName = itemView.findViewById(R.id.elementName);
			elementDescription = itemView.findViewById(R.id.elementDescription);
			
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
