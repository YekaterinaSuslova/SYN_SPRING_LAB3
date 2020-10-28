package Spring;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * Класс загрузки данных в объект Computer из считанной записи таблицы БД
 *
 */
public class ComputerRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int line) throws SQLException {
        ComputerResultSetExtractor extractor = new ComputerResultSetExtractor();
        return extractor.extractData(rs);
    }

    /**
     * Класс загрузки данных в объект данных из считанной записи таблицы
     *
     */
    class ComputerResultSetExtractor implements ResultSetExtractor {

        @Override
        public Object extractData(ResultSet rs) throws SQLException {
            Computer computer = new Computer();
            computer.setId(rs.getInt(1));
            computer.setProducer(rs.getString(2));
            computer.setProcessor(rs.getString(3));
            computer.setCores(rs.getInt(4));
            return computer;
        }
    }
}
