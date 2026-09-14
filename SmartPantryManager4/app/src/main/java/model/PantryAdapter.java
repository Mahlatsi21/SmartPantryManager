package model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.AddIngredientActivity;
import com.example.smartpantrymanager.R;

import java.util.List;

import database.PantryDAO;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private final List<PantryItem> pantryItems;

    public PantryAdapter(List<PantryItem> pantryItems) {
        this.pantryItems = pantryItems;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        PantryItem item = pantryItems.get(position);

        holder.txtName.setText(item.getName());
        holder.txtQuantity.setText(
                "Quantity: " + item.getQuantity() + " " + item.getUnit()
        );

        if (item.getExpiryDate() == null || item.getExpiryDate().isEmpty()) {
            holder.txtExpiry.setText("Expiry date: Not provided");
        } else {
            holder.txtExpiry.setText("Expiry date: " + item.getExpiryDate());
        }

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(
                    v.getContext(),
                    AddIngredientActivity.class
            );

            intent.putExtra("id", item.getId());
            intent.putExtra("name", item.getName());
            intent.putExtra("quantity", item.getQuantity());
            intent.putExtra("unit", item.getUnit());
            intent.putExtra("expiryDate", item.getExpiryDate());

            v.getContext().startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            PantryDAO pantryDAO = new PantryDAO(v.getContext());
            pantryDAO.deletePantryItem(item.getId());

            int currentPosition = holder.getAdapterPosition();

            if (currentPosition != RecyclerView.NO_POSITION) {
                pantryItems.remove(currentPosition);
                notifyItemRemoved(currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtQuantity;
        TextView txtExpiry;
        Button btnEdit;
        Button btnDelete;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtExpiry = itemView.findViewById(R.id.txtExpiry);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}