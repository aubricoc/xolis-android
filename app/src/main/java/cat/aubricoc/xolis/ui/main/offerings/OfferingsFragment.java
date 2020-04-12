package cat.aubricoc.xolis.ui.main.offerings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import cat.aubricoc.xolis.R;

public class OfferingsFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        OfferingsViewModel offeringsViewModel = new ViewModelProvider(this).get(OfferingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_offerings, container, false);
        final TextView textView = root.findViewById(R.id.text_offerings);
        offeringsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}
