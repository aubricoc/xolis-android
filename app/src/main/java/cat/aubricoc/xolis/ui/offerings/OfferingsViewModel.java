package cat.aubricoc.xolis.ui.offerings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OfferingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OfferingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
