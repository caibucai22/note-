package cn.henu.cs.note.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.henu.cs.note.R;
import cn.henu.cs.note.entity.NoteEntity;

public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<NoteEntity> datas;

    public NoteAdapter(Context context, List<NoteEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_note_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        NoteEntity noteEntity = datas.get(position);
        vh.picId.setImageResource(noteEntity.getPicId());
        vh.tvTitle.setText(noteEntity.getTitle());
        vh.tvContent.setText(noteEntity.getContent());
        vh.tvTime.setText(noteEntity.getTime());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvTime;
        private ImageView picId;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvTitle=view.findViewById(R.id.item_note_tile);
            tvContent = view.findViewById(R.id.item_note_content);
            tvTime = view.findViewById(R.id.item_note_time);
            picId=view.findViewById(R.id.item_note_iv);

        }
    }
}
