package me.chaoe.sdutnew.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import me.chaoe.sdutnew.R;
import me.chaoe.sdutnew.pojo.NewBean;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<NewBean> newlist;
    public interface OnClickListener{
        void onItemClick(View view,int posiotion,NewBean nnew);
    }
    private OnClickListener onClickListener;

    public MyAdapter(List<NewBean> newlist) {
        this.newlist = newlist;
    }
    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_item_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.title.setText(newlist.get(position).getTitle());
        holder.date.setText(newlist.get(position).getDate());
        holder.text.setText(newlist.get(position).getText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                onClickListener.onItemClick(holder.itemView,pos,newlist.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return newlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView text;
        public TextView date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.new_title);
            this.date = (TextView) itemView.findViewById(R.id.new_date);
            this.text = (TextView) itemView.findViewById(R.id.new_text);
        }
    }
    public void addlist(List<NewBean> thislist){
        int size = newlist.size();
        this.newlist.addAll(thislist);
        notifyItemInserted(size);
        notifyItemRangeChanged(size,newlist.size()-size);
    }
    public void removeall(){
        int size = this.newlist.size();
        newlist.clear();
        notifyItemRangeRemoved(0,size);
    }
}
