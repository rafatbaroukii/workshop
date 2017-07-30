package com.progressoft.workshop.layout.client;

import com.google.gwtmockito.GwtMockitoTestRunner;
import com.progressoft.brix.domino.api.client.annotations.ClientModule;
import com.progressoft.brix.domino.test.api.client.DominoTestClient;
import com.progressoft.workshop.layout.client.contributions.FakeLayoutContribution;
import com.progressoft.workshop.layout.client.presenters.LayoutPresenter;
import com.progressoft.workshop.layout.client.presenters.LayoutPresenterSpy;
import com.progressoft.workshop.layout.client.views.FakeLayoutView;
import com.progressoft.workshop.layout.shared.extension.LayoutContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ClientModule(name = "TestLayout")
@RunWith(GwtMockitoTestRunner.class)
public class LayoutClientModuleTest {

    private LayoutPresenterSpy presenterSpy;
    private FakeLayoutView fakeView;
    private FakeLayoutContribution fakeLayoutContribution;

    @Before
    public void setUp() {
        DominoTestClient.useModules(new LayoutModuleConfiguration(), new TestLayoutModuleConfiguration())
                .replacePresenter(LayoutPresenter.class, new LayoutPresenterSpy(),
                        presentable -> this.presenterSpy = (LayoutPresenterSpy) presentable)
                .viewOf(LayoutPresenter.class, view -> this.fakeView = (FakeLayoutView) view)
                .contributionOf(FakeLayoutContribution.class, contribution -> this.fakeLayoutContribution = (FakeLayoutContribution) contribution)
                .start();
    }

    @Test
    public void givenLayoutModule_whenContributingToMainExtensionPoint_thenShouldReceiveMainContext() throws Exception {
        assertNotNull(presenterSpy.getMainContext());
    }

    @Test
    public void givenLayoutModule_whenContributedToMainContext_thenViewShouldBeShown() throws Exception {
        assertTrue(fakeView.isShown());
    }

    @Test
    public void givenLayoutModule_whenContributeToLayoutExtensionPoint_thenContributionShouldReceiveTheLayoutContext() throws Exception {
        assertNotNull(fakeLayoutContribution.getLayoutContext());
    }

    @Test
    public void givenLayoutContext_whenAddingMenuItem_thenTheItemShouldBeAddedToTheLayoutView() throws Exception {
        LayoutContext.MenuItem menuItem = () -> null;
        fakeLayoutContribution.getLayoutContext().addMenuItem(menuItem);

        assertThat(fakeView.getMenuItem()).isEqualTo(menuItem);
    }

    @Test
    public void givenLayoutContext_whenSettingTheContent_thenTheContentShouldBeSetToTheLayoutView() throws Exception {
        LayoutContext.Content fakeContent = () -> null;
        fakeLayoutContribution.getLayoutContext().setContent(fakeContent);

        assertThat(fakeView.getContent()).isEqualTo(fakeContent);
    }
}
