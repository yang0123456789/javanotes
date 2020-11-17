package codes.JAVA并发编程的艺术.线程池.实例;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * # @author  chilcyWind
 * # @Time   2020/7/21 15:33
 * # @version 1.0
 * # @File : fds.java
 * # @Software: IntelliJ IDEA
 */
public class ConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<Connection>();

    public ConnectionPool(int initialSize) {

        if (initialSize > 0) {

            for (int i = 0; i < initialSize; i++) {

                pool.addLast(ConnectionDriver.createConnection());

            }

        }

    }

    public void releaseConnection(Connection connection) {

        if (connection != null) {

            synchronized (pool) {

// 连接释放后需要进行通知，这样其他消费者能够感知到连接池中已经归还了一个连接

                pool.addLast(connection);

                pool.notifyAll();

            }

        }

    }

// 在mills内无法获取到连接，将会返回null

    public Connection fetchConnection(long mills) throws InterruptedException {

        synchronized (pool) {

// 完全超时

            if (mills <= 0) {

                while (pool.isEmpty()) {

                    pool.wait();

                }

                return pool.removeFirst();

            } else {
                long future = System.currentTimeMillis() + mills;

                long remaining = mills;

                while (pool.isEmpty() && remaining > 0) {

                    pool.wait(remaining);

                    remaining = future - System.currentTimeMillis();

                }

                Connection result = null;

                if (!pool.isEmpty()) {

                    result = pool.removeFirst();

                }

                return result;

            }

        }

    }

}
