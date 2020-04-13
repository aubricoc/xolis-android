package cat.aubricoc.xolis.ui.main.wishes;

import android.content.Intent;
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
import cat.aubricoc.xolis.Xolis;
import cat.aubricoc.xolis.core.service.WishService;
import cat.aubricoc.xolis.core.utils.Preferences;
import cat.aubricoc.xolis.ui.main.wishes.create.CreateWishActivity;

public class WishesFragment extends Fragment {

    private WishesViewModel model;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wishes, container, false);

        model = new ViewModelProvider(this).get(WishesViewModel.class);

        RecyclerView recyclerView = root.findViewById(R.id.wish_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        WishesListAdapter adapter = new WishesListAdapter();
        recyclerView.setAdapter(adapter);

        model.getWishes().observe(getViewLifecycleOwner(), adapter::setWishes);

        root.findViewById(R.id.add_wish).setOnClickListener(view -> createNewWish());

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Xolis.getPreferences().store(Preferences.LAST_MAIN_DESTINATION, R.id.navigation_wishes);
        WishService.getInstance().getWishes(model::setWishes);
    }

    private void createNewWish() {
        startActivity(new Intent(getContext(), CreateWishActivity.class));
    }
}
