package cat.aubricoc.xolis.ui.main.offerings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OfferingsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public OfferingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("People will offer delicious local products :)");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
