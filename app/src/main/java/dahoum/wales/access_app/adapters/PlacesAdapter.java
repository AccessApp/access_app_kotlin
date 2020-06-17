package dahoum.wales.access_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dahoum.wales.access_app.R;
import dahoum.wales.access_app.models.Place;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {

    private List<Place> places;
    private Context context;
    private PlacesCallback callback;

    public PlacesAdapter(Context context, List<Place> places) {
        this.context = context;
        this.places = places;
    }

    public void setAdapterCallback(PlacesCallback callback) {
        this.callback = callback;
    }

    public void setDataList(List<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = places.get(position);
        holder.placeName.setText(place.getName());
        holder.placeDesc.setText(place.getDescription());
        holder.websiteTv.setText(place.getWww());
        Picasso.get().load("http://80.100.38.7:3001/api/image/" + place.getId()).into(holder.image);

        if (place.getIsFavourite()) {
            holder.placeFav.setImageResource(R.drawable.ic_heart_filled);
        } else {
            holder.placeFav.setImageResource(R.drawable.ic_heart);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_place_recycler, parent, false));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView placeName, placeDesc, websiteTv;
        private ImageView placeFav, image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.placeName);
            placeName.setOnClickListener(v -> {
                callback.onPlaceClick(getAdapterPosition());
            });
            placeDesc = itemView.findViewById(R.id.placeDesc);
            placeDesc.setOnClickListener(v -> {
                callback.onPlaceClick(getAdapterPosition());
            });
            placeFav = itemView.findViewById(R.id.placeFav);
            placeFav.setOnClickListener(v -> {
                callback.onFavouriteClick((ImageView) v, getAdapterPosition());
            });
            websiteTv = itemView.findViewById(R.id.websiteTv);
            websiteTv.setOnClickListener(v -> {
                callback.onWebsiteClick(getAdapterPosition());
            });
            image = itemView.findViewById(R.id.image);
            image.setOnClickListener(v -> {
                callback.onPlaceClick(getAdapterPosition());
            });
        }
    }

    public interface PlacesCallback {
        void onFavouriteClick(ImageView favImage, int position);

        void onPlaceClick(int position);

        void onWebsiteClick(int position);
    }
}
