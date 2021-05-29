package cn.henu.cs.note.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.henu.cs.note.R;
import cn.henu.cs.note.entity.NoteEntity;

import static android.content.ContentValues.TAG;

public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private Context mContext;
    private List<NoteEntity> backList;//备份原来的数据
    private List<NoteEntity> noteList;//这个数据是会改变的，所以先保存一下原来的数据
    private MyFilter mFilter;


    public NoteAdapter(Context context, List<NoteEntity> datas) {
        this.mContext = context;
        this.noteList = datas;

        backList = noteList;
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
        NoteEntity noteEntity = noteList.get(position);
        if (noteEntity.getTag() == 1) {
            vh.picId.setImageResource(R.mipmap.note_type_1);
        } else if (noteEntity.getTag() == 2) {
            vh.picId.setImageResource(R.mipmap.note_type_2);
        } else if (noteEntity.getTag() == 3) {
            vh.picId.setImageResource(R.mipmap.note_type_3);
        }
        vh.tvTitle.setText(noteEntity.getTitle());
        vh.tvContent.setText(noteEntity.getContent());
        vh.tvTime.setText(noteEntity.getTime());
        vh.linearLayout.setTag(position);

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvTime;
        private ImageView picId;

        public ViewHolder(@NonNull View view) {
            super(view);
            linearLayout = view.findViewById(R.id.item_note_layout);
            tvTitle = view.findViewById(R.id.item_note_tile);
            tvContent = view.findViewById(R.id.item_note_content);
            tvTime = view.findViewById(R.id.item_note_time);
            picId = view.findViewById(R.id.item_note_iv);

        }
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new MyFilter();
        }
        return mFilter;
    }

    class MyFilter extends Filter {
        //我们在performFiltering(CharSequence charSequence)这个方法中定义过滤规则
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            List<NoteEntity> list;
            if (TextUtils.isEmpty(charSequence)) {//当过滤的关键字为空的时候，我们则显示所有的数据
                list = backList;
            } else {//否则把符合条件的数据对象添加到集合中
                list = new ArrayList<>();
                for (NoteEntity note : backList) {
                    if (note.getContent().contains(charSequence)) {
                        list.add(note);
                    }
                }
            }
            result.values = list; //将得到的集合保存到FilterResults的value变量中
            result.count = list.size();//将集合的大小保存到FilterResults的count变量中

            return result;
        }

        //在publishResults方法中告诉适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            noteList = (List<NoteEntity>) filterResults.values;
            //if (filterResults.count > 0){
                Log.d(TAG, "publishResults: 更新成功");
                notifyDataSetChanged();//通知数据发生了改变
            //}
        }
    }

}
