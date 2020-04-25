package cat.aubricoc.xolis.ui.main.wishes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cat.aubricoc.xolis.server.model.Wish;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class WishesViewModel extends ViewModel {

    private final MutableLiveData<List<Wish>> wishes;
    private final MutableLiveData<Boolean> loading;
    private Date nextWishesLoadCreatedTo;

    public WishesViewModel() {
        wishes = new MutableLiveData<>(new ArrayList<>());
        loading = new MutableLiveData<>(false);
    }

    public LiveData<List<Wish>> getWishes() {
        return wishes;
    }

    public void setWishes(List<Wish> wishes) {
        storeNextWishesLoad(wishes);
        this.wishes.setValue(wishes);
    }

    public void addWishes(List<Wish> wishes) {
        storeNextWishesLoad(wishes);
        List<Wish> old = Objects.requireNonNull(this.wishes.getValue());
        old.addAll(wishes);
        this.wishes.setValue(old);
    }

    private void storeNextWishesLoad(List<Wish> wishes) {
        if (wishes.isEmpty()) {
            this.nextWishesLoadCreatedTo = null;
        } else {
            this.nextWishesLoadCreatedTo = wishes.get(wishes.size() - 1).getCreated();
        }
    }

    public Date getNextWishesLoadCreatedTo() {
        return nextWishesLoadCreatedTo;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading.setValue(loading);
    }
}
