package Spring;

import javax.sql.DataSource;
import java.util.List;

public interface IComputerDAO {

    void setDataSource(DataSource ds); // Установка связи с данныими

    void insert(Computer customer); // Вставка новой записи

    void append(String producer, String processor, int cores); // Добавление новой записи

    void deleteByProcessor(String processor); // Удаление записи по параметру

    void delete(String producer, String processor); // Удаление записи с указанными параметрами

    void deleteAll(); // Удаление всех запией

    void update(String oldProcessor, String newProcessor); // Изменение записей в таблице

    Computer findByCores(int cores); // Получение записи с заданным параметром

    List<Computer> findByProducer(String producer); // Получение записей с заданным параметром

    List<Computer> select(String producer, String processor); // Получение записей с заданным параметром

    List<Computer> selectAll(); // Получение всех записей
}
