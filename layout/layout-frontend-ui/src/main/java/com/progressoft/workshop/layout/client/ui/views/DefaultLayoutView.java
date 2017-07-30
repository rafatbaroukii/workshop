package com.progressoft.workshop.layout.client.ui.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.progressoft.brix.domino.api.client.annotations.UiView;
import com.progressoft.workshop.layout.client.presenters.LayoutPresenter;
import com.progressoft.workshop.layout.client.views.LayoutView;
import com.progressoft.workshop.layout.shared.extension.LayoutContext;
import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialSideNavPush;

@UiView(presentable = LayoutPresenter.class)
public class DefaultLayoutView extends Composite implements LayoutView {

    interface DefaultLayoutViewUiBinder extends UiBinder<HTMLPanel, DefaultLayoutView> {
    }

    private static DefaultLayoutViewUiBinder ourUiBinder = GWT.create(DefaultLayoutViewUiBinder.class);

    @UiField
    MaterialSideNavPush menuPanel;

    @UiField
    MaterialContainer contentPanel;

    @UiField
    MaterialNavBrand navBrand;

    public DefaultLayoutView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public IsWidget get() {
        return this;
    }

    @Override
    public void show() {
        RootPanel.get().add(get());
    }

    @Override
    public void addMenuItem(LayoutContext.MenuItem menuItem) {
        menuPanel.add(menuItem.get());
    }

    @Override
    public void setContent(LayoutContext.Content content) {
        contentPanel.clear();
        contentPanel.add(content.get());
    }

    @Override
    public void setHeader(String headerText) {
        navBrand.setText(new SafeHtmlBuilder().appendEscaped(headerText).toSafeHtml().asString());
    }
}