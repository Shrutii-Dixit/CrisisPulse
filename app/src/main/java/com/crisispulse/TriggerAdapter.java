package com.crisispulse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.crisispulse.model.Crisis;
import java.util.List;

public class TriggerAdapter extends RecyclerView.Adapter<TriggerAdapter.ViewHolder> {
    private List<Crisis> crisisList;

    public TriggerAdapter(List<Crisis> crisisList) {
        this.crisisList = crisisList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trigger, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Crisis crisis = crisisList.get(position);
        holder.tvType.setText(crisis.type.toUpperCase());
        holder.tvDescription.setText(crisis.description);
        holder.tvStatus.setText(crisis.status.toUpperCase());
        holder.tvConfidence.setText("AI Confidence: " + crisis.final_confidence_score + "%");

        if ("confirmed".equals(crisis.status)) holder.tvStatus.setTextColor(0xFF4CAF50);
        else if ("suspected".equals(crisis.status)) holder.tvStatus.setTextColor(0xFFFF9800);
        else holder.tvStatus.setTextColor(0xFFF44336);
    }

    @Override
    public int getItemCount() {
        return crisisList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvType, tvDescription, tvStatus, tvConfidence;

        public ViewHolder(View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tvType);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvConfidence = itemView.findViewById(R.id.tvConfidence);
        }
    }
}
