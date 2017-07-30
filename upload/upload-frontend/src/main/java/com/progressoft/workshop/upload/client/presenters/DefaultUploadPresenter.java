package com.progressoft.workshop.upload.client.presenters;

import com.progressoft.brix.domino.api.client.annotations.Presenter;
import com.progressoft.brix.domino.api.client.mvp.presenter.BaseClientPresenter;
import com.progressoft.brix.domino.api.shared.extension.MainContext;
import com.progressoft.brix.domino.api.shared.history.TokenFilter;
import com.progressoft.brix.domino.vertxbus.shared.extension.VertxBusContext;
import com.progressoft.workshop.layout.shared.extension.LayoutContext;
import com.progressoft.workshop.fileparser.shared.FileStatistics;
import com.progressoft.workshop.upload.client.UploadClientContext;
import com.progressoft.workshop.upload.client.views.UploadView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.subjects.PublishSubject;

@Presenter
public class DefaultUploadPresenter extends BaseClientPresenter<UploadView> implements UploadPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUploadPresenter.class);

    private PublishSubject<MainContext> mainContextSubject = PublishSubject.create();
    private PublishSubject<LayoutContext> layoutContextSubject = PublishSubject.create();

    @Override
    public void initView(UploadView view) {
        Observable.zip(mainContextSubject, layoutContextSubject, CombinedContext::new).subscribe(combinedContext -> {

            view.menuItem().selectionHandler(() -> {
                combinedContext.layoutContext.setContent(view.content());
                combinedContext.layoutContext.setHeaderText("Upload");
                combinedContext.mainContext.history().pushState("upload", "Workshop - upload", "");
            });

            combinedContext.mainContext.history()
                    .listen(TokenFilter.startsWith("upload"), state -> selectUpload())
                    .onDirectUrl(state -> selectUpload());
        });
    }

    @Override
    public void contributeToMainModule(MainContext context) {
        LOGGER.info("Main main received at presenter " + DefaultUploadPresenter.class.getSimpleName());
        mainContextSubject.onNext(context);
    }

    @Override
    public void contributeToLayoutModule(LayoutContext layoutContext) {
        layoutContext.addMenuItem(view.menuItem());
        layoutContextSubject.onNext(layoutContext);
    }

    private void selectUpload() {
        view.menuItem().select();
    }

    @Override
    public void contributeToEventBusModule(VertxBusContext context) {
        context.registerMessageHandler("file-stats", this::onStatsReceived);
    }

    private void onStatsReceived(Object message) {
        FileStatistics fileStats = UploadClientContext.getFileStatsObjectMapper().fromJson(message.toString());
        view.content().update(fileStats);
    }

    private class CombinedContext {
        private final MainContext mainContext;
        private final LayoutContext layoutContext;

        private CombinedContext(MainContext mainContext,
                                LayoutContext layoutContext) {
            this.mainContext = mainContext;
            this.layoutContext = layoutContext;
        }
    }
}