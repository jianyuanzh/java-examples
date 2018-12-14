package cc.databus.maria;

import java.sql.*;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {

        if (args.length < 4) {
            System.out.println("check <hostname> <username> <password> <dbName>");
            return;
        }

        // reportsuper
        // report59super
        String hostname = args[0];
        String username = args[1];
        String pass = args[2];
        String dbName = args[3];
        String query = "SHOW /*!50002 GLOBAL */ STATUS;";

        loadDriver();
        String url = String.format("jdbc:mysql://%s:3306/%s", hostname, dbName);

        CountDownLatch latch = new CountDownLatch(1);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    queryStatus(url, username, pass, query);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    latch.countDown();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        if (!latch.await(1, TimeUnit.MINUTES)) {
            System.out.println("Not finished in 2 minutes - dump threads");
            System.out.println(DiagnosticCommand.getInstance().diagnose(DiagnosticCommand.Operation.dumpThread.name(), new HashMap<>()));
        }
    }

    private static void queryStatus(String url, String username, String password, String query) throws SQLException, InterruptedException {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
            stmt.setQueryTimeout(120);
            rs = stmt.executeQuery(query);


            if (rs.next()) {
                int columns = rs.getMetaData().getColumnCount();

                StringBuilder s = new StringBuilder();
                for (int i = 1; i <= columns; i++) {
                    String value = rs.getString(i);

                    String colName = rs.getMetaData().getColumnName(i).toLowerCase();
                    s.append(colName).append('=').append(value).append('\n');

                    /* get alias name set with AS clause in SQL statement
                     * Note: we do not remove colName for back-compatibility so that
                     * the existing datasources work well as before */
                    String colAsName = rs.getMetaData().getColumnLabel(i).toLowerCase();
                    if (!colName.equals(colAsName)) {
                        s.append(colAsName).append('=').append(value).append('\n');
                    }

                }

                StringBuilder s2 = new StringBuilder();
                do {
                    for (int i = 1; i <= columns; i++) {
                        if (i != 1) {
                            s2.append(',');
                        }

                        String value = rs.getString(i);

                        String colName = rs.getMetaData().getColumnName(i).toLowerCase();
                        s2.append(colName).append('=').append(value);

                        String colAsName = rs.getMetaData().getColumnLabel(i).toLowerCase();
                        if (!colName.equals(colAsName)) {
                            s2.append(",").append(colAsName).append('=').append(value);
                        }

                    }
                    s2.append('\n');
                } while (rs.next());

                System.out.println("Done");
                System.out.println(s2.toString());

            }
        }
        finally {
            closeSiliently(rs);
            closeSiliently(stmt);
            closeSiliently(conn);
        }
    }

    private static void loadDriver() throws ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
    }

    private static void closeSiliently(AutoCloseable closeable) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        }
        catch (Exception ignore) {
        }
    }

}
