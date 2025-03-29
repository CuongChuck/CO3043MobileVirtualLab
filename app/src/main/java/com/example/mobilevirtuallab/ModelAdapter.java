package com.example.mobilevirtuallab;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ModelViewHolder> {
    public static class ModelViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout modelView;
        public TextView textView;
        public ImageView imageView;
        public boolean clicked = false;

        ModelViewHolder(View view) {
            super(view);
            modelView = view.findViewById(R.id.model_view);
            textView = view.findViewById(R.id.model_name);
            imageView = view.findViewById(R.id.model_image);

            imageView.setVisibility(View.INVISIBLE);
            modelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!clicked) {
                        modelView.setBackgroundResource(R.color.clicked);
                        imageView.setVisibility(View.VISIBLE);
                        clicked = true;
                    }
                    else {
                        modelView.setBackgroundResource(R.color.white);
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
            new Model()
    );
}
