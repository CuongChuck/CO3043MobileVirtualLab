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
        class ModelViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout modelView;
            public TextView textView;
            public ImageView imageView;
            public boolean clicked = false;

            ModelViewHolder(View view, ImageView image) {
                super(view);
                imageView = image;
                modelView = view.findViewById(R.id.model_view);
                textView = view.findViewById(R.id.model_name);

                imageView.setVisibility(View.INVISIBLE);
                modelView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!clicked) {
                            modelView.setBackgroundResource(R.drawable.mainview_button_clicked);
                            imageView.setVisibility(View.VISIBLE);
                            clicked = true;
                        }
                        else {
                            modelView.setBackgroundResource(R.drawable.mainview_button);
                            imageView.setVisibility(View.INVISIBLE);
                            clicked = false;
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

        private ImageView imageView;

        ModelAdapter(ImageView view) {
            this.imageView = view;
        }

        @NonNull
        @Override
        public ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_view, parent, false);
            return new ModelViewHolder(view, imageView);
        }

        @Override
        public void onBindViewHolder(@NonNull ModelViewHolder holder, int position) {
            Model current = models.get(position);
            holder.textView.setText(current.getName());
            holder.modelView.setTag(current);
            holder.imageView.setImageResource(current.getImage());
        }

        @Override
        public int getItemCount() {
            return models.size();
        }
    }
}