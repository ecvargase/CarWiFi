package com.espiot.cav.carwifi.network;

import com.espiot.cav.carwifi.common.models.InstructionsSet;
import com.espiot.cav.carwifi.common.models.ItemList;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by camilovargas on 3/10/17.
 */

public class Providers {


    private Network network = new Network();
    private ApiService service = network.apiServices();

    public void setupPWM(int progress) {

        Observable<String> pwm = service.getPWM(String.valueOf(progress));
        observableDefaultString(pwm);
    }

    public void setupReverse(String state) {

        Observable<String> reverse = service.getReverse(String.valueOf(state));
        observableDefaultString(reverse);
    }

    public void setupServo(int progress) {

        Observable<String> servo = service.getServo(String.valueOf(progress));
        observableDefaultString(servo);
    }

    public void setInstuctions(InstructionsSet instructionsSet) {

        Observable<String> servo = service.getInstructions(instructionsSet);
        observableDefaultString(servo);
    }


    private void observableDefaultString(Observable<String> observable) {

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull String s) {
                        Timber.d(String.format("Next %s", s));
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Timber.d(String.format("Error %s", e));
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete");
                    }
                });
    }
}
