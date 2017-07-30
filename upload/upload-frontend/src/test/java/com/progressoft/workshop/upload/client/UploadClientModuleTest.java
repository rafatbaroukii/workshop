package com.progressoft.workshop.upload.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.progressoft.brix.domino.api.client.annotations.ClientModule;
import com.progressoft.brix.domino.api.client.extension.Contributions;
import com.progressoft.brix.domino.test.api.client.ClientContext;
import com.progressoft.brix.domino.test.api.client.DominoTestClient;
import com.progressoft.brix.domino.vertxbus.shared.extension.VertxBusExtensionPoint;
import com.progressoft.workshop.fileparser.shared.Transaction;
import com.progressoft.workshop.layout.shared.extension.LayoutContext;
import com.progressoft.workshop.layout.shared.extension.LayoutExtensionPoint;
import com.progressoft.workshop.upload.client.presenters.UploadPresenter;
import com.progressoft.workshop.upload.client.presenters.UploadPresenterSpy;
import com.progressoft.workshop.upload.client.views.FakeUploadView;
import com.progressoft.workshop.upload.shared.BulkValidTransactions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

@ClientModule(name = "TestUpload")
@RunWith(GwtMockitoTestRunner.class)
public class UploadClientModuleTest {

    private String VALID_TRANSACTIONS_UI = "valid-transactions-ui";
    private String INVALID_TRANSACTIONS_UI = "invalid-transactions-ui";

    private UploadPresenterSpy presenterSpy;
    private FakeUploadView fakeView;
    private FakeLayoutContext fakeLayoutContext;
    private ClientContext dominoContext;
    private FakeVertxBusContext fakeVertxBusContext;

    @Before
    public void setUp() {
        fakeVertxBusContext = new FakeVertxBusContext();
        UploadClientContext.withBulkValidTransactionsMapper(new TestJsonMapper<>(new ObjectMapper(), BulkValidTransactions.class));
        DominoTestClient.useModules(new UploadModuleConfiguration(), new TestUploadModuleConfiguration())
                .replacePresenter(UploadPresenter.class, new UploadPresenterSpy(),
                        presentable -> this.presenterSpy = (UploadPresenterSpy) presentable)
                .viewOf(UploadPresenter.class, view -> this.fakeView = (FakeUploadView) view)
                .onBeforeStart(context -> {
                    dominoContext = context;
                    fakeLayoutContext = new FakeLayoutContext();
                })
                .onStartCompleted(context ->
                        Contributions.apply(VertxBusExtensionPoint.class, (VertxBusExtensionPoint) () -> fakeVertxBusContext))
                .start();
    }

    private void applyLayoutContributions() {
        Contributions.apply(LayoutExtensionPoint.class, (LayoutExtensionPoint) () -> fakeLayoutContext);
    }

    @Test
    public void givenUploadModule_whenContributingToMainExtensionPoint_thenShouldReceiveMainContext() throws Exception {
        assertNotNull(presenterSpy.getMainContext());
    }

    @Test
    public void givenUploadModule_whenContributingToLayoutExtensionPoint_thenShouldReceiveLayoutContext()
            throws Exception {
        applyLayoutContributions();
        assertNotNull(presenterSpy.getLayoutContext());
    }

    @Test
    public void givenLayoutContext_whenContributionApplied_thenShouldAddMenuItemToTheLayout() throws Exception {
        applyLayoutContributions();
        assertThat(fakeLayoutContext.getMenuItem()).isEqualTo(fakeView.menuItem());
    }

    @Test
    public void givenLayoutContext_whenMenuItemIsSelected_thenUploadContentShouldBeShownInLayoutContent()
            throws Exception {
        applyLayoutContributions();
        fakeView.selectMenuItem();

        assertThat(fakeLayoutContext.getContent()).isEqualTo(fakeView.content());
        assertThat(dominoContext.history().currentToken().value()).isEqualTo("upload");
    }

    @Test
    public void givenUploadModule_whenCurrentUrlTokenStartsWithUpload_thenUploadMenuShouldBeSelectedAndUploadContentShouldBeShown()
            throws Exception {
        dominoContext.history().initialState("upload", "");
        applyLayoutContributions();

        assertThat(fakeView.isMenuSelected()).isTrue();
        assertThat(fakeLayoutContext.getContent()).isEqualTo(fakeView.content());
    }

    @Test
    public void givenUploadModule_whenFireStateWithUrlTokenStartsWithUpload_thenUploadMenuShouldBeSelectedAndUploadContentShouldBeShown()
            throws Exception {
        applyLayoutContributions();
        dominoContext.history().pushState("upload", "", "");
        dominoContext.history().fireCurrentStateHistory();

        assertThat(fakeView.isMenuSelected()).isTrue();
        assertThat(fakeLayoutContext.getContent()).isEqualTo(fakeView.content());
    }

    @Test
    public void givenBusContext_whenContributionApplied_thenShouldRecieveBusContext() throws Exception {
        assertThat(presenterSpy.getBusContext()).isNotNull();
    }

    private class FakeLayoutContext implements LayoutContext {

        private MenuItem menuItem;
        private Content content;

        @Override
        public void addMenuItem(MenuItem menuItem) {
            this.menuItem = menuItem;
        }

        @Override
        public void setContent(Content content) {
            this.content = content;
        }

        public MenuItem getMenuItem() {
            return menuItem;
        }

        public Content getContent() {
            return content;
        }

        @Override
        public void setHeaderText(String headerText) {

        }
    }
}
