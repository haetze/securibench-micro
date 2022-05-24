// SPDX-FileCopyrightText: 2021 Falk Howar falk.howar@tu-dortmund.de
// SPDX-License-Identifier: Apache-2.0

// This file is part of the SV-Benchmarks collection of verification tasks:
// https://gitlab.com/sosy-lab/benchmarking/sv-benchmarks

package mock.sql;

import org.sosy_lab.sv_benchmarks.Tainting;

public class Connection {

    public void prepareStatement(String s) {
        Tainting.check(s, Tainting.sql_injection, false);
    }

    public void close() throws SQLException{
    }

    public Statement createStatement() {
        return new Statement();
    }
}
