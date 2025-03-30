package com.example.mobilevirtuallab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView imageView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        recyclerView = findViewById(R.id.models);
        imageView = findViewById(R.id.model_image);
        adapter = new ModelAdapter(imageView);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
    }

    class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ModelViewHolder> {
        private ImageView imageView;
        private int selectedPosition = -1;

        ModelAdapter(ImageView view) {
            this.imageView = view;
        }

        class ModelViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout modelView;
            public TextView textView;

            ModelViewHolder(View view) {
                super(view);
                modelView = view.findViewById(R.id.model_view);
                textView = view.findViewById(R.id.model_name);

                modelView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAbsoluteAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            if (selectedPosition != -1) {
                                notifyItemChanged(selectedPosition);
                            }
                            selectedPosition = position;
                            notifyItemChanged(position);
                            imageView.setImageResource(models.get(position).getImage());
                            imageView.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }

        private List<Model> models = Arrays.asList(
                new Model("Cân điện tử", "", R.drawable.balance),
                new Model("Máy đo pH", "", R.drawable.phmeter),
                new Model("Máy đo quang phổ", "", R.drawable.spectrophotometer),
                new Model("Máy đo độ đục", "", R.drawable.turbidity_meter),
                new Model("Máy đo độ dẫn điện", "", R.drawable.conductivity_meter),
                new Model("Đèn bunsen", "", R.drawable.bunsen_burner),
                new Model("Bếp điện", "", R.drawable.hot_plate),
                new Model("Máy li tâm", "", R.drawable.centrifuge),
                new Model("Nồi hấp tiệt trùng", "", R.drawable.autoclave),
                new Model("Bộ chuẩn độ", "", R.drawable.titration)
        );

        @NonNull
        @Override
        public ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_view, parent, false);
            return new ModelViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ModelViewHolder holder, int position) {
            Model current = models.get(position);
            holder.textView.setText(current.getName());
            holder.modelView.setBackgroundResource(position == selectedPosition ? R.drawable.mainview_button_clicked : R.drawable.mainview_button);
        }

        @Override
        public int getItemCount() {
            return models.size();
        }
    }
}