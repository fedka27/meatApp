package app.meat.view.splash;

import java.util.concurrent.TimeUnit;

import app.meat.model.data.repository.CategoryRepository;
import app.meat.util.rx.RxSchedulersAbs;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class SplashPresenter implements SplashContract.Presenter {
    private final int settingRequestCode = 432;
    private SplashContract.View view;
    private CategoryRepository categoryRepository;
    private RxSchedulersAbs rxSchedulersAbs;
    private CompositeDisposable compositeDisposable;

    public SplashPresenter(CategoryRepository categoryRepository,
                           RxSchedulersAbs rxSchedulersAbs,
                           CompositeDisposable compositeDisposable) {
        this.categoryRepository = categoryRepository;
        this.rxSchedulersAbs = rxSchedulersAbs;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void bindView(SplashContract.View view) {
        this.view = view;
    }

    @Override
    public void onStart() {
        compositeDisposable.add(Observable.timer(2, TimeUnit.SECONDS)
                .compose(rxSchedulersAbs.getIOToMainTransformer())
                .subscribe(aLong -> {
                    view.openMainActivity();
                }));
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
    }

}
