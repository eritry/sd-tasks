package com.shefer.visit.command;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import com.shefer.common.command.CommandDao;

public class CommandDaoImpl implements CommandDao {
    private final Connection conn;

    public CommandDaoImpl(Connection conn) {
        this.conn = conn;
    }

    Date getExpiryDate(Long uid) throws Exception {
        PreparedStatement st = conn.prepareStatement(
                "SELECT max(expiry_date) FROM membership_events WHERE user_id = ?");
        st.setLong(1, uid);
        ResultSet rs = st.executeQuery();

        if (!rs.next() || rs.getDate(1) == null) {

            throw new IllegalArgumentException();
        }
        return rs.getDate(1);

    }

    public String enter(Long uid, Long timestamp) throws Exception {
        if (!hasEntered(uid)) {
            Date expiryDate;
            try {
                expiryDate = getExpiryDate(uid);
            } catch (IllegalArgumentException e) {
                return "No membership found for user " + uid;
            }

            if (expiryDate.getTime() > timestamp) {
                Timestamp ts = new Timestamp(timestamp);

                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO visit_events(user_id, type, event_time) VALUES(?, 'enter', ?)",
                        Statement.RETURN_GENERATED_KEYS);

                pstmt.setLong(1, uid);
                pstmt.setTimestamp(2, ts);

                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    return "Successful enter";
                }

                conn.rollback();
                return "Error entering";
            } else {
                return "Membership " + uid + " has expired";
            }
        } else {
            return "The user " + uid + " has already entered";
        }
    }

    Boolean hasEntered(Long uid) throws Exception {
        PreparedStatement st = conn.prepareStatement(
                "SELECT type FROM visit_events WHERE user_id = ? ORDER BY event_time DESC LIMIT 1");
        st.setLong(1, uid);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            return rs.getString(1).equals("enter");
        } else {
            return false;
        }
    }

    public String exit(Long uid, Long timestamp) throws Exception {
        if (hasEntered(uid)) {
            Timestamp ts = new Timestamp(timestamp);

            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO visit_events(user_id, type, event_time) VALUES(?, 'exit', ?)",
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setLong(1, uid);
            pstmt.setTimestamp(2, ts);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return "Successful exit";
            }

            conn.rollback();
            return "Error exiting";
        } else {
            return "The user " + uid + " has not entered";
        }
    }
}
