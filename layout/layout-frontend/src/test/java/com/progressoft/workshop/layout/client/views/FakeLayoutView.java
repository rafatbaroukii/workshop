package com.progressoft.workshop.layout.client.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.progressoft.brix.domino.api.client.annotations.UiView;
import com.progressoft.workshop.layout.client.presenters.LayoutPresenter;
import com.progressoft.workshop.layout.shared.extension.LayoutContext;

@UiView(presentable = LayoutPresenter.class)
public class FakeLayoutView implements LayoutView {

    private boolean shown;
    private LayoutContext.MenuItem menuItem;
    private LayoutContext.Content content;

    @Override
    public IsWidget get() {
        return null;
    }

    public boolean isShown() {
        return shown;
    }

    @Override
    public void show() {
        this.shown = true;
    }

    @Override
    public void setHeader(String headerText) {

    }

    @Override
    public void addMenuItem(LayoutContext.MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    @Override
    public void setContent(LayoutContext.Content content) {
        this.content = content;
    }

    public LayoutContext.MenuItem getMenuItem() {
        return menuItem;
    }

    public LayoutContext.Content getContent() {
        return content;
    }
}
