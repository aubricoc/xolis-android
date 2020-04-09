package cat.aubricoc.xolis.ui.wishes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cat.aubricoc.xolis.R;

public class WishListFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wish_list, container, false);

        WishListViewModel model = new ViewModelProvider(this).get(WishListViewModel.class);

        RecyclerView recyclerView = root.findViewById(R.id.wish_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        WishListAdapter adapter = new WishListAdapter();
        recyclerView.setAdapter(adapter);

        model.getWishes().observe(getViewLifecycleOwner(), adapter::setWishes);
        WishClient.getInstance().getWishes(model::setWishes);

        root.findViewById(R.id.add_wish).setOnClickListener(view -> createNewWish());

        return root;
    }

    private void createNewWish() {
        throw new UnsupportedOperationException();
    }
}
