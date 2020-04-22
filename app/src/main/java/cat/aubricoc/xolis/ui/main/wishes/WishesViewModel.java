package cat.aubricoc.xolis.ui.main.wishes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cat.aubricoc.xolis.server.model.Wish;

import java.util.ArrayList;
import java.util.List;

public class WishesViewModel extends ViewModel {

    private final MutableLiveData<List<Wish>> wishes;
    private final MutableLiveData<Boolean> loading;

    public WishesViewModel() {
        wishes = new MutableLiveData<>(new ArrayList<>());
        loading = new MutableLiveData<>(false);
    }

    public LiveData<List<Wish>> getWishes() {
        return wishes;
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes.setValue(wishes);
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading.setValue(loading);
    }
}
