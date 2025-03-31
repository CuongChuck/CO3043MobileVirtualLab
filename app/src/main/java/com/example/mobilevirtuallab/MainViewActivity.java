package com.example.mobilevirtuallab;

import android.content.Intent;
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
    private ModelAdapter adapter;
    private TextView backward;
    private LinearLayout infoButton;
    private LinearLayout practice;
    private TextView info;
    private boolean infoVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        recyclerView = findViewById(R.id.models);
        imageView = findViewById(R.id.model_image);
        infoButton = findViewById(R.id.info_button);
        practice = findViewById(R.id.practice_button);
        info = findViewById(R.id.info);
        adapter = new ModelAdapter(imageView, infoButton, practice, info);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        backward = findViewById(R.id.backward);
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToMode = new Intent(MainViewActivity.this, ChildActivity.class);
                startActivity(backToMode);
            }
        });

        info.setVisibility(View.GONE);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getItemCount() > 0 && adapter.getSelectedPosition() != -1) {
                    if (!infoVisible) {
                        info.setText(adapter.getModels().get(adapter.getSelectedPosition()).getInfo());
                        info.setVisibility(View.VISIBLE);
                        infoButton.setBackgroundResource(R.drawable.mainview_button_clicked);
                        infoVisible = true;
                    }
                    else {
                        info.setVisibility(View.GONE);
                        infoButton.setBackgroundResource(R.drawable.mainview_button);
                        infoVisible = false;
                    }
                }
            }
        });
    }

    class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ModelViewHolder> {
        private ImageView imageView;
        private LinearLayout infoButton;
        private LinearLayout practiceButton;
        private TextView info;
        private int selectedPosition = -1;

        ModelAdapter(ImageView view, LinearLayout infoButton, LinearLayout practice, TextView info) {
            this.imageView = view;
            this.infoButton = infoButton;
            this.practiceButton = practice;
            this.info = info;
        }

        public int getSelectedPosition() {
            return selectedPosition;
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
                            info.setVisibility(View.GONE);
                            infoButton.setBackgroundResource(R.drawable.mainview_button);
                            infoVisible = false;
                        }
                    }
                });
            }
        }

        private List<Model> models = Arrays.asList(
                new Model(
                        "Cân điện tử",
                        "Một thiết bị cân có độ chính xác cao được sử dụng trong phòng thí nghiệm để đo khối lượng nhỏ một cách chính xác. Nó cung cấp kết quả kỹ thuật số và rất quan trọng trong việc chuẩn bị dung dịch hóa học cũng như thực hiện các thí nghiệm",
                        R.drawable.balance
                ),
                new Model(
                        "Máy đo pH",
                        "Một thiết bị dùng để đo độ axit hoặc bazơ của dung dịch bằng cách xác định giá trị pH. Nó cho kết quả chính xác hơn giấy quỳ và được sử dụng rộng rãi trong hóa học, sinh học và khoa học môi trường",
                        R.drawable.phmeter
                ),
                new Model(
                        "Máy đo quang phổ",
                        "Một thiết bị phân tích đo lượng ánh sáng mà một chất hấp thụ ở các bước sóng khác nhau. Nó thường được sử dụng trong nghiên cứu hóa học và sinh học để xác định nồng độ chất hòa tan trong dung dịch",
                        R.drawable.spectrophotometer
                ),
                new Model(
                        "Máy đo độ đục",
                        "Một thiết bị đo độ trong suốt hoặc mức độ đục của chất lỏng, thường được sử dụng để xác định nồng độ hạt lơ lửng. Nó rất quan trọng trong kiểm tra chất lượng nước và giám sát môi trường",
                        R.drawable.turbidity_meter
                ),
                new Model(
                        "Máy đo độ dẫn điện",
                        "Một thiết bị đo khả năng dẫn điện của dung dịch, cho biết sự hiện diện của các ion hòa tan. Nó được sử dụng rộng rãi trong hóa học, phân tích nước và ứng dụng công nghiệp",
                        R.drawable.conductivity_meter
                ),
                new Model(
                        "Đèn bunsen",
                        "Một loại đèn khí nhỏ được sử dụng trong phòng thí nghiệm để tạo ra ngọn lửa có thể điều chỉnh được, dùng để đốt nóng, khử trùng và đốt cháy các chất. Đây là dụng cụ quan trọng trong các thí nghiệm hóa học",
                        R.drawable.bunsen_burner
                ),
                new Model(
                        "Bếp điện",
                        "Một thiết bị gia nhiệt phẳng được sử dụng để đun nóng mẫu và dung dịch mà không cần ngọn lửa trực tiếp. Nó thường được sử dụng kết hợp với máy khuấy từ để làm nóng và trộn đều",
                        R.drawable.hot_plate
                ),
                new Model(
                        "Máy li tâm",
                        "Một thiết bị phòng thí nghiệm quay mẫu ở tốc độ cao để tách các chất dựa trên mật độ của chúng, thường được sử dụng để phân tách thành phần máu hoặc cô lập vật liệu tế bào",
                        R.drawable.centrifuge
                ),
                new Model(
                        "Nồi hấp tiệt trùng",
                        "Một thiết bị khử trùng bằng hơi nước áp suất cao dùng để tiêu diệt vi khuẩn, virus và các vi sinh vật khác trên dụng cụ thí nghiệm và mẫu sinh học. Nó rất cần thiết trong vi sinh học và phòng thí nghiệm y tế",
                        R.drawable.autoclave
                ),
                new Model(
                        "Bộ chuẩn độ",
                        "Một bộ dụng cụ trong phòng thí nghiệm bao gồm buret, bình tam giác và chất chỉ thị, dùng để xác định nồng độ của một chất trong dung dịch thông qua phản ứng hóa học có kiểm soát. Nó được sử dụng phổ biến trong hóa phân tích",
                        R.drawable.titration
                )
        );

        public List<Model> getModels() {
            return models;
        }

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