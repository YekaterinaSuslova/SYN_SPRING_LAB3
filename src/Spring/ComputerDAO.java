package Spring;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.TransactionStatus;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

import java.util.List;

/**
 * Реализация интерфейса работы с таблицей Computer
 *
 */
public class ComputerDAO implements IComputerDAO {

    private DataSource dataSource;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Computer customer) { // Реализация вставки новой записи

        JdbcTemplate insert = new JdbcTemplate(dataSource);
        insert.update("INSERT INTO COMPUTERDESCRIPTION (PRODUCER, PROCESSOR, CORES) VALUES(?,?,?)",
                new Object[]{customer.getFirstName(), customer.getProcessor(), customer.getCores()});
    }

    @Override
    public void append(String producer, String processor, int cores) {  // Реализация добавления новой записи
        JdbcTemplate insert = new JdbcTemplate(dataSource);
        insert.update("INSERT INTO COMPUTERDESCRIPTION (PRODUCER, PROCESSOR, CORES) VALUES(?,?,?)", new Object[]{producer, processor, cores});
    }

    @Override
    public void deleteByProcessor(String processor) {  // Реализация удаления записей по фамилии
        JdbcTemplate insert = new JdbcTemplate(dataSource);
        insert.update("DELETE FROM COMPUTERDESCRIPTION WHERE PROCESSOR LIKE ?", new Object[]{'%' + processor + '%'});
    }

    @Override
    public void delete(final String producer, final String processor) {  // Реализация удаления записей с заданными параметрами
        TransactionTemplate transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));

        transactionTemplate.execute(new TransactionCallback() {
            @Override
            public Object doInTransaction(TransactionStatus status) {

                try {
                    JdbcTemplate delete = new JdbcTemplate(dataSource);
                    delete.update("DELETE from COMPUTERDESCRIPTION where PRODUCER= ? AND PROCESSOR = ?", new Object[]{producer, processor});
                } catch (RuntimeException e) {
                    status.setRollbackOnly();
                    throw e;
                } catch (Exception e) {
                    status.setRollbackOnly();
                    throw new RuntimeException(e);
                }
                return null;
            }
        });
    }

    @Override

    public void deleteAll() {  // Реализация удаления всех запией
        JdbcTemplate delete = new JdbcTemplate(dataSource);
        delete.update("DELETE from COMPUTERDESCRIPTION");
    }

    @Override
    public void update(String oldProcessor, String newProcessor) {  // Изменение записей в таблице
        JdbcTemplate update = new JdbcTemplate(dataSource);
        update.update("UPDATE COMPUTERDESCRIPTION SET PROCESSOR = ? WHERE PROCESSOR = ?", new Object[]{newProcessor, oldProcessor});
    }

    @Override
    public Computer findByCores(int cores) { // Реализация роиска записи с заданным возрастом
        JdbcTemplate select = new JdbcTemplate(dataSource);
        List<Computer> computerdescription = select.query("SELECT * FROM COMPUTERDESCRIPTION WHERE CORES = ?",
                new Object[]{cores}, new ComputerRowMapper());
        return computerdescription.size() > 0 ? computerdescription.get(0) : null;
    }

    @Override
    public List<Computer> findByProducer(String processor) {  // Реализация поиска записей по заданному параметру
        JdbcTemplate select = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM COMPUTERDESCRIPTION WHERE PRODUCER LIKE ?";
        List<Computer> computers = select.query(sql, new Object[]{'%' + processor + '%'}, new ComputerRowMapper());
        return computers;
    }

    @Override
    public List<Computer> select(String producer, String processor) {  // Реализация получения записей по заданным параметрам
        JdbcTemplate select = new JdbcTemplate(dataSource);
        return select.query("select  * from COMPUTERDESCRIPTION where PRODUCER = ? AND PROCESSOR= ?",
                new Object[]{producer, processor}, new ComputerRowMapper());
    }

    @Override
    public List<Computer> selectAll() {  // Реализация получения всех записей
        JdbcTemplate select = new JdbcTemplate(dataSource);
        return select.query("select * from COMPUTERDESCRIPTION", new ComputerRowMapper());
    }

    
//    public void deleteByProcessor(String processor) {
//       
//    }
//
//    @Override
//    public Computer findByCores(int cores) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public List<Computer> findByProducer(String producer) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

}
