package com.progressoft.workshop.uploadedfiles.client;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.gwtmockito.WithClassesToStub;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;

import static org.junit.Assert.*;

import com.progressoft.workshop.uploadedfiles.client.presenters.UploadedFilesPresenter;
import com.progressoft.workshop.uploadedfiles.client.presenters.UploadedFilesPresenterSpy;
import com.progressoft.workshop.uploadedfiles.client.views.UploadedFilesViewSpy;

import com.progressoft.brix.domino.test.api.client.DominoTestClient;

@RunWith(GwtMockitoTestRunner.class)
@WithClassesToStub(RootPanel.class)
public class UploadedFilesClientModuleTest{

    private UploadedFilesPresenterSpy presenterSpy;
    private UploadedFilesViewSpy viewSpy;

    @Before
    public void setUp() {
        DominoTestClient.useModules(new UploadedFilesModuleConfiguration(), new UploadedFilesUIModuleConfiguration())
                .replacePresenter(UploadedFilesPresenter.class, new UploadedFilesPresenterSpy(), presentable -> presenterSpy=
                    (UploadedFilesPresenterSpy) presentable)
                .replaceView(UploadedFilesPresenter.class, new UploadedFilesViewSpy(), view -> viewSpy= (UploadedFilesViewSpy) view)
                .start();
    }

    @Test
    public void nothing() throws Exception {

    }

}
