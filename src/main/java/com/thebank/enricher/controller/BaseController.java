package com.thebank.enricher.controller;

import com.thebank.enricher.commons.RequestInfo;
import com.thebank.enricher.commons.RequestInfoRegistry;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.TimeoutException;


public abstract class BaseController {

    public static final String

            API_VERSION = "/v1",
            API_PREFIX = "/api" + API_VERSION,
            TRADE_PREFIX = API_PREFIX + "/trades";

    protected <T> DeferredResult<T> deferredObservable(Observable<T> observable) {
        return new DeferredResultObservable<>(observable);
    }

    private static class DeferredResultObservable<T> extends DeferredResult<T> {
        private Disposable subscription;
        private RequestInfo requestInfo;
        private T             result;
        private boolean       resultIsSet;

        public DeferredResultObservable(Observable<T> observable) {
            requestInfo = RequestInfoRegistry.getRequestInfo();
            onTimeout(this::handleTimeout);
            ConnectableObservable<T> publication = observable.publish();
            publication.subscribe(this::onNext, this::onError, this::onCompleted);
            publication.connect(subscription -> this.subscription = subscription);
        }

        private void handleTimeout() {
            setErrorResult(new TimeoutException(requestInfo.toString()));
            subscription.dispose();
        }

        public void onCompleted() {
            if (resultIsSet) {
                setResult(result);
            } else {
                setResult(null);
            }
        }

        public void onError(Throwable e) {
            setErrorResult(e);
        }

        public void onNext(T t) {
            if (!resultIsSet) {
                result = t;
                resultIsSet = true;
            } else {
                setErrorResult(new IllegalStateException("Produced multiple results"));
                subscription.dispose();
            }
        }
    }
}
