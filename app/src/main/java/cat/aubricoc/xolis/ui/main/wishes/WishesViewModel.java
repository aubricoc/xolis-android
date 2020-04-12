package cat.aubricoc.xolis.ui.main.wishes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cat.aubricoc.xolis.server.model.Wish;

import java.util.ArrayList;
import java.util.List;

public class WishesViewModel extends ViewModel {

    private final MutableLiveData<List<Wish>> wishes;

    public WishesViewModel() {
        wishes = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Wish>> getWishes() {
        return wishes;
    }

    public void setWishes(List<Wish> list) {
        wishes.setValue(list);
    }
}
