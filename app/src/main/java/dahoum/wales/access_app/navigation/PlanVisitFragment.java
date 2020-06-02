package dahoum.wales.access_app.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dahoum.wales.access_app.ProfileActivity;
import dahoum.wales.access_app.R;
import dahoum.wales.access_app.adapters.PlanVisitAdapter;
import dahoum.wales.access_app.models.Slot;

public class PlanVisitFragment extends Fragment {

    private ImageView goBackButton;
    private RecyclerView recyclerView;
    private PlanVisitAdapter adapter;
    private List<Slot> slots = new ArrayList<>();

    public PlanVisitFragment() {
        // Required empty public constructor
    }

    public static PlanVisitFragment newInstance() {
        PlanVisitFragment fragment = new PlanVisitFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan_visit, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        goBackButton = view.findViewById(R.id.goBack);
        goBackButton.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });
        view.findViewById(R.id.openProfile).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ProfileActivity.class));
        });

        recyclerView = view.findViewById(R.id.recyclerPlanVisit);
        getData();
        adapter = new PlanVisitAdapter();
        adapter.setDataList(slots);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

    }

    private void getData() {
        slots.add(new Slot(null, null, null, "MON", "27th May", 1));
        slots.add(new Slot("10:00 - 11:00", "Priority", "11/20",null, null, 0));
        slots.add(new Slot("10:00 - 11:00", "Priority", "11/20",null, null, 0));
        slots.add(new Slot("10:00 - 11:00", "Priority", "11/20",null, null, 0));
        slots.add(new Slot("10:00 - 11:00", "Priority", "11/20",null, null, 0));
        slots.add(new Slot("10:00 - 11:00", "Priority", "11/20",null, null, 0));
        slots.add(new Slot(null, null, null, "TUE", "28th May", 1));
        slots.add(new Slot("10:00 - 11:00", "Priority", "11/20",null, null, 0));
        slots.add(new Slot("10:00 - 11:00", "Priority", "11/20",null, null, 0));
        slots.add(new Slot("10:00 - 11:00", "Priority", "11/20",null, null, 0));
        slots.add(new Slot("10:00 - 11:00", "Priority", "11/20",null, null, 0));
        slots.add(new Slot("10:00 - 11:00", "Priority", "11/20",null, null, 0));
        slots.add(new Slot(null, null, null, "WED", "29th May", 1));
        slots.add(new Slot("10:00 - 11:00", "Priority", "11/20",null, null, 0));
        slots.add(new Slot("10:00 - 11:00", "Priority", "11/20",null, null, 0));
        slots.add(new Slot("10:00 - 11:00", "Priority", "11/20",null, null, 0));
        slots.add(new Slot("10:00 - 11:00", "Priority", "11/20",null, null, 0));
        slots.add(new Slot("10:00 - 11:00", "Priority", "11/20",null, null, 0));
    }
}
