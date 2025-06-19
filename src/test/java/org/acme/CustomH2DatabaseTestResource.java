package org.acme;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.h2.tools.Server;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CustomH2DatabaseTestResource implements QuarkusTestResourceLifecycleManager {
    private Server tcpServer;

    @Override
    public Map<String, String> start() {
        try {
            tcpServer = Server.createTcpServer("-ifNotExists").start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Map<String, String> props = new HashMap<>();
        props.put("quarkus.datasource.jdbc.url", "jdbc:h2:tcp://localhost/mem:test");
        props.put("quarkus.datasource.db-kind", "h2");
        props.put("quarkus.hibernate-orm.database.generation", "drop-and-create");
        return props;
    }

    @Override
    public synchronized void stop() {
        if (tcpServer != null) {
            tcpServer.stop();
            tcpServer = null;
        }
    }
}
