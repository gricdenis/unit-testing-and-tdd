package com.acme.banking.dbo.reporting;

import com.acme.banking.dbo.domain.Branch;
import com.acme.banking.dbo.domain.Client;
import com.acme.banking.dbo.service.Reporting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ReportingTest {


    Reporting sut;
    Branch branch;

    @BeforeEach
    void setUp(){
        sut = new Reporting();
        branch = mock(Branch.class);
    }

    @Test
    public void shouldGetReport() {
        assertThat(sut.getReport(branch)).isEqualTo(
                "# BRANCH 1 (10000.0)" +
                        "  ## (1) Z K" +
                        "       ### acc 0: 100.0" +
                        "  # BRANCH 1-1 (20000.0)" +
                        "    ## (2) Vas Pupking" +
                        "       ### acc 1: 100.0" +
                        "       ### acc 2: 200.0"
        );
    }

    @Test
    public void shouldGetReportOnBranchWithEmptySumAccount() {

        assertThat(sut.getReport(branch)).isEqualTo(
                "# BRANCH 1 (0.0)"
        );
    }
    }
}
