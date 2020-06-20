package nl.birdsongit.repositories;

import nl.birdsongit.model.TodoItem;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TodoMapper implements RowMapper<TodoItem> {

    @Override
    public TodoItem map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new TodoItem((UUID) rs.getObject("id"))
                .setTitle(rs.getString("title"))
                .setCompleted(rs.getBoolean("completed"))
                .setOrder(rs.getInt("index"))
                .setUrl(rs.getString("url"));
    }
}
