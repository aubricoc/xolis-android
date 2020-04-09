package cat.aubricoc.xolis.ui.wishes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class WishListViewModel extends ViewModel {

    private MutableLiveData<List<Wish>> wishes;

    public WishListViewModel() {
        wishes = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Wish>> getWishes() {
        return wishes;
    }

    public void setWishes(List<Wish> list) {
        wishes.setValue(list);
    }
}
