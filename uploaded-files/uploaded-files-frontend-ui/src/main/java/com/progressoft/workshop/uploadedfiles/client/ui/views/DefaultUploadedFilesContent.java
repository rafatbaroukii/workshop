package com.progressoft.workshop.uploadedfiles.client.ui.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.progressoft.workshop.fileparser.shared.FileStatistics;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

import static com.progressoft.workshop.uploadedfiles.client.views.UploadedFilesView.UploadedFilesContent;

public class DefaultUploadedFilesContent extends Composite implements UploadedFilesContent {

    private final MaterialPanel andBindUi;


    interface DefaultUploadedFilesContentUiBinder extends UiBinder<MaterialPanel, DefaultUploadedFilesContent> {
    }

    private static DefaultUploadedFilesContentUiBinder ourUiBinder = GWT.create(DefaultUploadedFilesContentUiBinder.class);

    @UiField
    MaterialCollection itemsList;

    @UiField
    MaterialColumn detailsContainer;

    public DefaultUploadedFilesContent() {
        andBindUi = ourUiBinder.createAndBindUi(this);
        initWidget(andBindUi);
        itemsList.getElement().getStyle().setProperty("height", "calc(100vh - 65px)");
        detailsContainer.getElement().getStyle().setProperty("height", "calc(100vh - 65px)");
//        MaterialToast.fireToast(detailsContainer.getElement().getScrollTop() + " " + detailsContainer.getElement().getScrollHeight());
        Event.sinkEvents(detailsContainer.getElement(), Event.ONSCROLL);
        detailsContainer.addHandler(event -> {
//            if ((detailsContainer.getElement().getScrollHeight() - detailsContainer.getElement().getScrollTop())<=1000)
//                MaterialToast.fireToast(detailsContainer.getElement().getScrollTop() - detailsContainer.getElement().getScrollHeight() + "");

        }, ScrollEvent.getType());
    }

    @Override
    public IsWidget get() {
        return this;
    }

    @Override
    public void add(FileStatistics fileStatistics) {
        MaterialCollectionItem item = new MaterialCollectionItem();

        item.addStyleName(Bundle.INSTANCE.style().entry());
        item.setWaves(WavesType.DEFAULT);

        MaterialLabel nameLabel = new MaterialLabel(fileStatistics.fileName);
        nameLabel.addStyleName(Bundle.INSTANCE.style().collectionFileName());

        MaterialLabel dateLabel = new MaterialLabel(DateTimeFormat.getFormat(
                com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat.DATE_TIME_FULL).format(fileStatistics.uploadDate));
        dateLabel.addStyleName(Bundle.INSTANCE.style().collectionFileDate());

        item.add(nameLabel);
        item.add(dateLabel);

        FileDetails fileDetails = new FileDetails(fileStatistics);
        detailsContainer.add(fileDetails);

        item.addClickHandler(event -> {
            fileDetails.getElement().scrollIntoView();
            MaterialAnimation animation = new MaterialAnimation();
            animation.setTransition(Transition.SHAKE);
            animation.setDelay(0);
            animation.setDuration(1000);
            animation.setInfinite(false);
            animation.animate(fileDetails);
        });


        itemsList.add(item);
    }

    @Override
    public void clear() {
        itemsList.clear();
    }

}