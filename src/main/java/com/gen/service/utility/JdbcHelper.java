package com.gen.service.utility;

import com.gen.service.module.db.dto.DbConfigDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Objects;

/**
 * JDBC Helper
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class JdbcHelper {
    /**
     * Data Source
     */
    private DataSource dataSource;

    /**
     * Connection
     */
    private Connection connection;

    /**
     * Prepared Statement
     */
    private PreparedStatement ps;

    /**
     * Statement
     */
    private Statement statement;

    /**
     * Result Set
     */
    private ResultSet rs;

    /**
     * Auto Commit
     * The default value is true. If the value is false, the transaction needs to be committed manually.
     */
    private boolean autoCommit = true;

    /**
     * Connection Open
     */
    private boolean openConnection;

    /**
     * Jndi Name
     */
    private String jndiName;

    /**
     * Database Driver
     */
    private String driver = "com.mysql.cj.jdbc.Driver";

    /**
     * DB Connection
     */
    DbConfigDTO dbConfig;


    /***
     * Class Overloading Constructor
     * @param dataSource DataSource
     */
    public JdbcHelper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * JNDI Name Constructor
     *
     * @param jndiName Jndi Name
     */
    public JdbcHelper(String jndiName) {
        this.jndiName = jndiName;
    }

    /**
     * Class Overloading Constructor
     *
     */
    public JdbcHelper(DbConfigDTO dbConfig) {
        this.dbConfig = dbConfig;
    }

    /**
     * Open a database connection and create a database connection object.
     * Supports injecting data source, database driver, JNDI name, database access address, username, and password via ICO.
     *
     * @return boolean true:connection successful, false: connection failed
     */
    public boolean openConnection() {
        if (!Objects.isNull(dataSource)) {
            try {
                connection = dataSource.getConnection();
                openConnection = true;
            } catch (SQLException e) {
                closeAll();
                throw new RuntimeException(e);
            }
            return false;
        }
        //Get a database connection object via JNDI
        if (!StringUtils.isEmpty(jndiName)) {
            try {
                Context initContext = new InitialContext();
                dataSource = (DataSource) initContext.lookup(jndiName);
                connection = dataSource.getConnection();
                // 数据库连接已经打开
                openConnection = true;
            } catch (Exception e) {
                closeAll();
                System.out.println("JNDI connect failed");
                throw new RuntimeException(e);
            }

            return false;
        }

        //Get the database connection object using the database driver, database access address, username, and password.
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUserName(), dbConfig.getPassword());

            System.out.println(connection);
            openConnection = true;
        } catch (Exception e) {
            closeAll();
            System.out.println("Database connection failed！");
            throw new RuntimeException(e);
        }

        return openConnection;
    }

    /**
     * Update
     *
     * @param sql SQL
     * @return boolean true:success，false:failed
     */
    public boolean execUpdate(String sql, Object... args) {
        boolean result = false;
        if (openConnection) {
            try {
                ps = connection.prepareStatement(sql);
                if (args != null && args.length > 0) {
                    for (int i = 0; i < args.length; i++) {
                        ps.setObject(i + 1, args[i]);
                    }
                }
                ps.executeUpdate();

                result = true;
            } catch (SQLException e) {
                try {
                    if (autoCommit) {
                        connection.rollback();
                    }
                } catch (SQLException e1) {
                    throw new RuntimeException(e1);
                }
                System.out.println("SQL:" + sql);
                throw new RuntimeException(e);
            } finally {
                if (autoCommit) {
                    closeAll();
                }
            }
        } else {
            System.out.println("The database connection object is not open.！");
        }

        return result;
    }

    /**
     * Update
     *
     * @param sql SQL
     * @return boolean true:success，false:failed
     */
    public boolean execUpdate(String sql, List<?> args) {
        return execUpdate(sql, args.toArray());
    }

    /**
     * Batch update
     *
     * @param sql SQLs
     * @return boolean true:success，false:failed
     */
    public boolean execUpdate(Object[] sql) {
        boolean result = false;
        if (openConnection) {
            try {
                statement = connection.createStatement();
                for (Object o : sql) {
                    statement.addBatch((String) o);
                }
                statement.executeBatch();

                result = true;
            } catch (SQLException e) {
                try {
                    if (autoCommit) {
                        connection.rollback();
                    }
                } catch (SQLException e1) {
                    throw new RuntimeException(e1);
                }
                for (int i = 0; i < sql.length; i++) {
                    System.out.println("SQL " + (i + 1) + ":" + sql[i]);
                }
                throw new RuntimeException(e);
            } finally {
                if (autoCommit) {
                    closeAll();
                }
            }
        } else {
            System.out.println("The database connection object is not open.！");
        }

        return result;
    }

    /**
     * 执行Batch update
     *
     * @param sql SQLs
     * @return boolean true:success，false:failed
     */
    public boolean execUpdate(List<?> sql) {
        return execUpdate(sql.toArray());
    }

    /**
     * Execute sql query
     *
     * @param sql  SQL
     * @param args Arguments
     * @return ResultSet Result Set
     */
    public ResultSet execQuery(String sql, Object... args) {
        rs = null;
        if (openConnection) {
            try {
                ps = connection.prepareStatement(sql);
                if (args != null && args.length > 0) {
                    for (int i = 0; i < args.length; i++) {
                        ps.setObject(i + 1, args[i]);
                    }
                }
                rs = ps.executeQuery();
            } catch (SQLException e) {
                if (autoCommit) {
                    closeAll();
                }
                e.printStackTrace();
            }
        } else {
            System.out.println(" server connection failed ！");
        }
        return rs;
    }

    /**
     * Execute sql query
     *
     * @param sql  SQL
     * @param args Arguments
     * @return ResultSet Result Set
     */
    public ResultSet execQuery(String sql, List<?> args) {
        return execQuery(sql, args.toArray());
    }

    /**
     * Query and return an Int value
     *
     * @param sql  SQL
     * @param args Args
     * @return An integer value; returns -1 if an error occurs.
     */
    public int findForInt(String sql, Object... args) {
        ResultSet rs = execQuery(sql, args);
        int count = -1;
        try {
            if (rs != null && rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (autoCommit) {
                closeAll();
            }
        }

        return count;
    }

    /**
     * Query and return an Int value
     *
     * @param sql  SQL
     * @param args Args
     * @return An integer value; returns -1 if an error occurs.
     */
    public int findForInt(String sql, List<?> args) {
        return findForInt(sql, args.toArray());
    }

    /**
     * Close all connections.
     */
    public void closeAll() {
        if (rs != null || ps != null || statement != null || connection != null) {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                rs = null;
                if (ps != null || statement != null || connection != null) {
                    try {
                        if (ps != null && !ps.isClosed()) {
                            ps.close();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } finally {
                        ps = null;
                        if (statement != null || connection != null) {
                            try {
                                if (statement != null && !statement.isClosed()) {
                                    statement.close();
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            } finally {
                                statement = null;
                                try {
                                    if (connection != null
                                            && !connection.isClosed()) {
                                        connection.close();
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                } finally {
                                    connection = null;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Commit the transaction and close the database connection.
     */
    public void commit() {
        try {
            if (!autoCommit) {
                connection.commit();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            autoCommit = true;
            closeAll();
        }
    }

    /**
     * Roll back the transaction and close the database connection.
     */
    public void rollback() {
        try {
            if (!autoCommit) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            autoCommit = true;
            closeAll();
        }
    }


    /**
     *
     * @param autoCommit
     */
    public void setAutoCommit(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            closeAll();
            throw new RuntimeException(e);
        }
        this.autoCommit = autoCommit;
    }

    /**
     * test
     *
     */
    public static void main(String[] args) throws SQLException {
        // String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        DbConfigDTO dbConfig = new DbConfigDTO();
        dbConfig.setUserName("root")
                .setPort("3306")
                .setPassword("3@g9viMz")
                .setHost("127.0.0.1");
        //jdbc:mysql://127.0.0.1:3306/?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true
        //jdbc:mysql://127.0.0.1:3306/?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true
        System.out.println(dbConfig.getUrl());

        JdbcHelper commonSql = new JdbcHelper(dbConfig);
        if (commonSql.openConnection()) {
            System.out.println("数据库连接成功！");
            DatabaseMetaData dbMetaData = commonSql.getConnection()
                    .getMetaData();
            System.out
                    .print("当前连接的数据库是:" + dbMetaData.getDatabaseProductName());
            System.out.println(" " + dbMetaData.getDatabaseProductVersion());
        } else {
            System.out.println("连接失败！");
        }

        commonSql.closeAll();
    }
}