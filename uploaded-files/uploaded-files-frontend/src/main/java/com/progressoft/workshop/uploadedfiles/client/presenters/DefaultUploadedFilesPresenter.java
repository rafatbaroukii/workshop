package com.progressoft.workshop.uploadedfiles.client.presenters;

import com.google.gwt.user.client.Window;
import com.progressoft.brix.domino.api.client.annotations.Presenter;
import com.progressoft.brix.domino.api.client.mvp.presenter.BaseClientPresenter;
import com.progressoft.brix.domino.api.shared.extension.MainContext;
import com.progressoft.brix.domino.api.shared.history.TokenFilter;
import com.progressoft.workshop.fileparser.shared.FileStatistics;
import com.progressoft.workshop.fileparser.shared.FilesStats;
import com.progressoft.workshop.layout.shared.extension.LayoutContext;
import com.progressoft.workshop.uploadedfiles.client.requests.UploadedFilesServerRequest;
import com.progressoft.workshop.uploadedfiles.client.views.UploadedFilesView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.subjects.PublishSubject;

@Presenter
public class DefaultUploadedFilesPresenter extends BaseClientPresenter<UploadedFilesView> implements UploadedFilesPresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUploadedFilesPresenter.class);

    private PublishSubject<MainContext> mainContextSubject = PublishSubject.create();
    private PublishSubject<LayoutContext> layoutContextSubject = PublishSubject.create();

    @Override
    public void initView(UploadedFilesView view) {
        Observable.zip(mainContextSubject, layoutContextSubject, CombinedContext::new).subscribe(combinedContext -> {
            view.menuItem().setSelectionHandler(() -> {
                combinedContext.layoutContext.setHeaderText("Uploaded files");
                combinedContext.layoutContext.setContent(view.content());
                combinedContext.mainContext.history().pushState("files", "Workshop - files", "");
                new UploadedFilesServerRequest().send();
            });
            combinedContext.mainContext.history()
                    .listen(TokenFilter.startsWith("files"), state -> selectUploadedFiles())
                    .onDirectUrl(state -> selectUploadedFiles());
        });
    }

    private void selectUploadedFiles() {
        view.menuItem().select();
    }

    @Override
    public void contributeToMainModule(MainContext context) {
        LOGGER.info("Main context received at presenter " + DefaultUploadedFilesPresenter.class.getSimpleName());
        mainContextSubject.onNext(context);
    }

    @Override
    public void contributeToLayoutModule(LayoutContext layoutContext) {
        layoutContext.addMenuItem(view.menuItem());
        layoutContextSubject.onNext(layoutContext);
    }

    @Override
    public void onFilesLoaded(FilesStats filesStats) {
        view.content().clear();
        filesStats.statistics.forEach(this::addStats);
    }

    private void addStats(FileStatistics fileStatistics) {
        view.content().add(fileStatistics);
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