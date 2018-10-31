package io.pivotal.pal.tracker;

import com.mysql.cj.jdbc.MysqlDataSource;
import io.pivotal.pal.tracker.TimeEntryRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO time_entries (project_id, user_id, date, hours) VALUES (?,?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, String.valueOf(timeEntry.getProjectId()));
                ps.setString(2, String.valueOf(timeEntry.getUserId()));
                ps.setString(3, String.valueOf(timeEntry.getDate()));
                ps.setLong(4, timeEntry.getHours());
                return ps;
            }
        }, keyHolder);

        // timeEntryId,  projectId,  userId,  date,  hours
        TimeEntry insertedTimeEntry = new TimeEntry(keyHolder.getKey().longValue(),timeEntry.getProjectId(),timeEntry.getUserId(), timeEntry.getDate(),    timeEntry.getHours());

        return insertedTimeEntry;
    }

    @Override
    public TimeEntry find(long id) {


        return jdbcTemplate.query(
                "SELECT id, project_id, user_id, date, hours FROM time_entries WHERE id = ?",
                new Object[]{id},
                extractor);
    }

    @Override
    public List<TimeEntry> list() {

        return jdbcTemplate.query("SELECT id, project_id, user_id, date, hours FROM time_entries",mapper);
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {

        jdbcTemplate.update("UPDATE time_entries SET project_id = ?, user_id = ?, date = ?,  hours = ? WHERE id = ?",
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                Date.valueOf(timeEntry.getDate()),
                timeEntry.getHours(),
                id);

        return find(id);
    }



    @Override
    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM time_entries WHERE id = ?",
                id);
    }

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}
