/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.bugs.mysql;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public abstract class AbstractTest {
    private Driver driver;

    public AbstractTest() {
        try {
            this.driver = new Driver();
        } catch (SQLException var2) {
            throw new RuntimeException(var2.toString());
        }
    }

    protected String getUrl() {
//        return "jdbc:mysql://root:root@localhost/test";
        return "jdbc:mysql://root@localhost/test";
    }

    protected final synchronized Connection createConnection() throws SQLException {
        return this.createConnection(this.getUrl());
    }

    protected final synchronized Connection createConnection(String url) throws SQLException {
        return this.createConnection(url, null);
    }

    protected final synchronized Connection createConnection(String url, Properties props) throws SQLException {
        return this.driver.connect(url, props);
    }
}
