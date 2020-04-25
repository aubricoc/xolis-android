package cat.aubricoc.xolis.server.repository;

import androidx.annotation.NonNull;
import cat.aubricoc.xolis.core.utils.Callback;
import cat.aubricoc.xolis.server.model.Wish;
import cat.aubricoc.xolis.server.utils.HttpErrorHandler;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.List;

public class WishesRepository extends Repository<Wish> {

    private static final WishesRepository INSTANCE = new WishesRepository();
    private static final String RESOURCE = "/wishes";

    private WishesRepository() {
        super(Wish.class, RESOURCE);
    }

    public static WishesRepository getInstance() {
        return INSTANCE;
    }

    public void find(@NonNull Date createdTo, Callback<List<Wish>> callback, HttpErrorHandler... errorHandlers) {
        prepareFind(callback, errorHandlers)
                .param("created", "lt:" + DateFormatUtils.format(createdTo, "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"))
                .execute();
    }
}
