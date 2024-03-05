package com.acme.banking.dbo.reporting;

import com.acme.banking.dbo.domain.Branch;
import com.acme.banking.dbo.domain.Client;
import com.acme.banking.dbo.exception.AccountNotFoundException;
import com.acme.banking.dbo.service.Reporting;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReportingTest {

    Reporting sut;
    Branch branch;

    @BeforeEach
    void setUp() {
        sut = new Reporting();
        branch = mock(Branch.class);
    }

    @Test
    public void shouldGetReport() {
        assertThat(sut.getReport(branch)).isEqualTo(
                "# Leninskiy 1 (10000.0)" +
                        "  ## (1) Z K" +
                        "       ### acc 0: 100.0" +
                        "  # BRANCH 1-1 (20000.0)" +
                        "    ## (2) Vas Pupking" +
                        "       ### acc 1: 100.0" +
                        "       ### acc 2: 200.0"
        );
    }

    @Test
    public void shouldGetReportOnBranchWithEmptySumAccountAndWithoutClients() {

        when(branch.toReport())
                .thenReturn("# Leninskiy");

        assertThat(sut.getReport(branch)).isEqualTo(
                "# Leninskiy"
        );
    }


    @Test
    public void shouldGetReportOnBranchWithoutClients() {

        when(branch.toReport())
                .thenReturn("# Leninskiy 100000.0");

        assertThat(sut.getReport(branch)).isEqualTo(
                "# Leninskiy 100000.0"
        );
    }

    @Test
    public void shouldFailOnBranchWithoutName() {

        when(branch.getName())
                .thenReturn("# Leninskiy");

        assertThrows(IllegalStateException.class,
                () -> sut.getReport(branch));
    }

}
