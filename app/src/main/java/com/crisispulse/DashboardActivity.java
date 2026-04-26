package com.crisispulse;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.crisispulse.databinding.ActivityDashboardBinding;
import com.crisispulse.model.Crisis;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding binding;
    private DatabaseReference mDatabase;
    private TriggerAdapter adapter;
    private List<Crisis> crisisList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        crisisList = new ArrayList<>();
        adapter = new TriggerAdapter(crisisList);
        binding.rvTriggers.setLayoutManager(new LinearLayoutManager(this));
        binding.rvTriggers.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("triggers");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                crisisList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Crisis crisis = postSnapshot.getValue(Crisis.class);
                    if (crisis != null) {
                        crisisList.add(0, crisis); // Show newest first
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
