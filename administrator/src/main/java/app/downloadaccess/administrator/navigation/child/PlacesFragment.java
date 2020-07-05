package app.downloadaccess.administrator.navigation.child;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.downloadaccess.administrator.R;
import app.downloadaccess.administrator.adapters.PlacesAdapter;
import app.downloadaccess.resources.models.Place;
import app.downloadaccess.resources.network.RetrofitClientInstance;
import app.downloadaccess.resources.network.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesFragment extends Fragment implements PlacesAdapter.PlacesCallback {

    private static final String TAG = PlacesFragment.class.getSimpleName();
    private FragmentCallback callback;
    private ChipGroup chipGroup;
    private RetrofitService retrofitService;
    private RecyclerView recyclerView;
    private PlacesAdapter adapter;
    private List<Place> places;
    private SharedPreferences prefs;
    private RelativeLayout loadingPanel;
    private int currentPage = 0;
    private int visibleThreshold = 7;
    private boolean isLoading = false;
    private boolean reachedEnd = false;

    public PlacesFragment() {
        // Required empty public constructor
    }

    public static PlacesFragment newInstance() {
        PlacesFragment fragment = new PlacesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d(TAG, "Attached Places Fragment");
    }

    public void setListener(FragmentCallback callback) {
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_places, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingPanel = view.findViewById(R.id.loadingPanel);
        chipGroup = view.findViewById(R.id.chip_group);
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = group.findViewById(checkedId);
            if (chip.getText().equals("Approved")) {
                getAllPlaces(true);
            } else if (chip.getText().equals("Non-approved")) {
                getAllPlaces(false);
            } else if (chip.getText().equals("All")) {
                getAllPlaces(null);
            }
        });

        places = new ArrayList<>();
        retrofitService = RetrofitClientInstance.INSTANCE.buildService(RetrofitService.class);
        recyclerView = view.findViewById(R.id.placesRecyclerView);
        adapter = new PlacesAdapter(getContext(), places);
        adapter.setAdapterCallback(this);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        prefs.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
            if (key.equals("userId") && places.isEmpty()) {
                getAllPlaces(null);
            }
        });
        if (prefs.getString("userId", null) != null) {
            getAllPlaces(null);
        }
    }

    @Override
    public void onPlaceClick(int position) {
        callback.onPlaceClicked(places.get(position));
    }

    @Override
    public void onWebsiteClick(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(places.get(position).getWww()));
        startActivity(intent);
    }

    void getAllPlaces(Boolean isApproved) {
        isLoading = true;
        loadingPanel.setVisibility(View.GONE);
        HashMap<String, Object> map = new HashMap<>();
        if (isApproved != null) {
            map.put("approved", isApproved);
        }
        retrofitService.getAllPlaces(prefs.getString("userId", null), map).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Gson gson = new Gson();
                if (response.body().get("places").getAsJsonArray() != null) {
                    ArrayList<Place> newPlaces = gson.fromJson(response.body().get("places"), new TypeToken<ArrayList<Place>>() {
                    }.getType());

                    if (newPlaces.isEmpty()) {
                        reachedEnd = true;
                        return;
                    }

                    places.clear();
                    places.addAll(newPlaces);
                    adapter.notifyDataSetChanged();
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, t.getLocalizedMessage());
                isLoading = false;
            }
        });

    }

}
