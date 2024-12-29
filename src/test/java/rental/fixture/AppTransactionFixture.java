package rental.fixture;

import org.mockito.stubbing.Stubber;
import rental.application.AppTransaction;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;

public class AppTransactionFixture {

    private AppTransactionFixture() {

    }

    public static AppTransaction mockedTransaction() {
        AppTransaction transaction = mock(AppTransaction.class);
        doAnswer(invocationOnMock -> {
            when(transaction.inTransaction()).thenReturn(true);
            Runnable runnable = invocationOnMock.getArgument(0);
            runnable.run();
            when(transaction.inTransaction()).thenReturn(false);
            return null;
        }).when(transaction).execute(any());
        return transaction;
    }

    public static Stubber assertThatInTransaction(AppTransaction transaction) {
        return doAnswer(invocationOnMock -> {
            assertThat(transaction.inTransaction(), is(true));
            return null;
        });
    }
}
