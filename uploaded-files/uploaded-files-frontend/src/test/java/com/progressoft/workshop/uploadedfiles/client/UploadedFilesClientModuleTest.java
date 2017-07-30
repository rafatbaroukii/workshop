package com.progressoft.workshop.uploadedfiles.client;

import com.google.gwtmockito.GwtMockitoTestRunner;
import com.progressoft.brix.domino.api.client.annotations.ClientModule;
import com.progressoft.brix.domino.test.api.client.DominoTestClient;
import com.progressoft.workshop.uploadedfiles.client.presenters.UploadedFilesPresenter;
import com.progressoft.workshop.uploadedfiles.client.presenters.UploadedFilesPresenterSpy;
import com.progressoft.workshop.uploadedfiles.client.views.FakeUploadedFilesView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;


@ClientModule(name = "TestUploadedFiles")
@RunWith(GwtMockitoTestRunner.class)
public class UploadedFilesClientModuleTest {

    private UploadedFilesPresenterSpy presenterSpy;
    private FakeUploadedFilesView fakeView;

    @Before
    public void setUp() {
        DominoTestClient.useModules(new UploadedFilesModuleConfiguration(), new TestUploadedFilesModuleConfiguration())
                .replacePresenter(UploadedFilesPresenter.class, new UploadedFilesPresenterSpy(), presentable -> presenterSpy =
                        (UploadedFilesPresenterSpy) presentable)
                .viewOf(UploadedFilesPresenter.class, view -> fakeView = (FakeUploadedFilesView) view)
                .start();
    }

    @Test
    public void givenUploadedFilesModule_whenContributingToMainExtensionPoint_thenShouldReceiveMainContext() throws Exception {
        assertNotNull(presenterSpy.getMainContext());
    }
}
