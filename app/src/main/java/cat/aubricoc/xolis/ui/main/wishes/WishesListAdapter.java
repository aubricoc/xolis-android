package cat.aubricoc.xolis.ui.main.wishes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cat.aubricoc.xolis.R;
import cat.aubricoc.xolis.server.model.Wish;

import java.util.ArrayList;
import java.util.List;

public class WishesListAdapter extends RecyclerView.Adapter<WishesListAdapter.ViewHolder> {

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
        holder.name.setText(wish.getName());
        holder.user.setText(wish.getUser().getUsername());
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
        private final TextView name;
        private final TextView user;

        private ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.wish_name);
            user = view.findViewById(R.id.wish_user);
        }
    }
}
