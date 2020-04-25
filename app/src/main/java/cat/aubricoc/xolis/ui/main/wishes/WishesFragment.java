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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cat.aubricoc.xolis.R;
import cat.aubricoc.xolis.Xolis;
import cat.aubricoc.xolis.core.service.WishService;
import cat.aubricoc.xolis.core.utils.Preferences;
import cat.aubricoc.xolis.ui.main.wishes.create.CreateWishActivity;

import java.util.Date;

public class WishesFragment extends Fragment {

    private WishesViewModel model;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wishes, container, false);

        model = new ViewModelProvider(this).get(WishesViewModel.class);

        SwipeRefreshLayout refreshLayout = root.findViewById(R.id.refresh_wish_list_container);
        RecyclerView recyclerView = root.findViewById(R.id.wish_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        WishesListAdapter adapter = new WishesListAdapter();
        recyclerView.setAdapter(adapter);

        model.getWishes().observe(getViewLifecycleOwner(), adapter::setWishes);
        model.getLoading().observe(getViewLifecycleOwner(), refreshLayout::setRefreshing);

        refreshLayout.setOnRefreshListener(this::loadWishes);
        root.findViewById(R.id.add_wish).setOnClickListener(view -> createNewWish());
        initScrollListener(recyclerView, refreshLayout, adapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Xolis.getPreferences().store(Preferences.LAST_MAIN_DESTINATION, R.id.navigation_wishes);
        loadWishes();
    }

    private void loadWishes() {
        model.setLoading(true);
        WishService.getInstance().getWishes(wishes -> {
            model.setWishes(wishes);
            model.setLoading(false);
        });
    }

    private void loadMoreWishes() {
        Date nextWishesLoadCreatedTo = model.getNextWishesLoadCreatedTo();
        if (nextWishesLoadCreatedTo != null) {
            model.setLoading(true);
            WishService.getInstance().getMoreWishes(nextWishesLoadCreatedTo, wishes -> {
                model.addWishes(wishes);
                model.setLoading(false);
            });
        }
    }

    private void createNewWish() {
        startActivity(new Intent(getContext(), CreateWishActivity.class));
    }

    private void initScrollListener(RecyclerView recyclerView, SwipeRefreshLayout refreshLayout, WishesListAdapter adapter) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!refreshLayout.isRefreshing() &&
                        linearLayoutManager != null &&
                        linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.getItemCount() - 1) {
                    loadMoreWishes();
                }
            }
        });
    }
}
