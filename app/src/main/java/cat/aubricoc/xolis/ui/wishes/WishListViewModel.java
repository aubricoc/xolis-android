package cat.aubricoc.xolis.ui.wishes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WishListViewModel extends ViewModel {

    private MutableLiveData<List<Wish>> wishes;

    public WishListViewModel() {
        wishes = new MutableLiveData<>(buildList());
    }

    public LiveData<List<Wish>> getWishes() {
        return wishes;
    }

    public void refresh() {
        wishes.setValue(buildList());
    }

    private List<Wish> buildList() {
        List<Wish> list = new ArrayList<>();
        for (int i = 0; i < new Random().nextInt(50); i++) {
            list.add(buildWish());
        }
        return list;
    }

    private Wish buildWish() {
        Wish wish = new Wish();
        wish.setTitle("Botifarra " + new Random().nextInt(100));
        return wish;
    }
}
