package cat.aubricoc.xolis.ui.wishes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cat.aubricoc.xolis.R;

import java.util.ArrayList;
import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    private List<Wish> wishes = new ArrayList<>();

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_wish, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wish wish = wishes.get(position);
        holder.title.setText(wish.getTitle());
    }

    @Override
    public int getItemCount() {
        return wishes.size();
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
        this.notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;

        private ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.wish_title);
        }
    }
}
