package org.hibernate.bugs.mysql;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DateTimeRoundtripTest extends AbstractTest {

    private TimeZone previousTimeZone;

    @Before
    public void setUp() {
        setDefaultTimeZone(TimeZone.getTimeZone("GMT-8"));
    }

    @After
    public void tearDown() {
        restorePreviousDefaultTimeZone();
    }

    @Test
    public void timestamp() throws Exception {
        try (Connection connection = createConnection()) {
            Timestamp sentValue = Timestamp.valueOf("2018-11-06 06:52:11");
            PreparedStatement st = connection.prepareStatement("SELECT ?");
            st.setTimestamp(1, sentValue);

            ResultSet rs = st.executeQuery();
            assertTrue(rs.next());
            Timestamp retrievedValue = rs.getTimestamp(1);
            assertEquals(sentValue, retrievedValue);
        }
    }

    @Test
    public void date() throws Exception {
        try (Connection connection = createConnection()) {
            Date sentValue = Date.valueOf("2018-11-06");
            PreparedStatement st = connection.prepareStatement("SELECT ?");
            st.setDate(1, sentValue);

            ResultSet rs = st.executeQuery();
            assertTrue(rs.next());
            Date retrievedValue = rs.getDate(1);
            assertEquals(sentValue, retrievedValue);
        }
    }

    @Test
    public void time() throws Exception {
        try (Connection connection = createConnection()) {
            Time sentValue = Time.valueOf("06:52:11");
            PreparedStatement st = connection.prepareStatement("SELECT ?");
            st.setTime(1, sentValue);

            ResultSet rs = st.executeQuery();
            assertTrue(rs.next());
            Time retrievedValue = rs.getTime(1);
            assertEquals(sentValue, retrievedValue);
        }
    }

    private void setDefaultTimeZone(TimeZone timeZone) {
        previousTimeZone = TimeZone.getDefault();
        TimeZone.setDefault(timeZone);
    }

    private void restorePreviousDefaultTimeZone() {
        TimeZone.setDefault(previousTimeZone);
    }
}
