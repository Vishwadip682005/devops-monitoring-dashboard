package com.devops;

import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class MainServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(9090), 0);

        // Homepage Dashboard
        // Homepage Dashboard
server.createContext("/", exchange -> {

    String html = """
            <html>

            <head>

                <title>DevOps Monitoring Dashboard</title>

                <style>

                    body {
                        font-family: Arial;
                        background-color: #f4f4f4;
                        padding: 30px;
                    }

                    h1 {
                        text-align: center;
                        color: #333;
                    }

                    .dashboard {
                        display: grid;
                        grid-template-columns: repeat(2, 1fr);
                        gap: 20px;
                        margin-top: 30px;
                    }

                    .card {
                        background: white;
                        padding: 20px;
                        border-radius: 10px;
                        box-shadow: 0px 2px 8px rgba(0,0,0,0.2);
                    }

                    .card h2 {
                        margin-bottom: 10px;
                        color: #444;
                    }

                    .value {
                        font-size: 24px;
                        color: #007bff;
                        font-weight: bold;
                    }

                </style>

                <script>

                    async function loadData() {

                        // Health API
                        let healthResponse =
                            await fetch('/api/health');

                        let healthData =
                            await healthResponse.json();

                        document.getElementById("health")
                            .innerText = healthData.status;

                        // CPU API
                        let cpuResponse =
                            await fetch('/api/cpu');

                        let cpuData =
                            await cpuResponse.json();

                        document.getElementById("cpu")
                            .innerText = cpuData.cpuLoad;

                        // Memory API
                        let memoryResponse =
                            await fetch('/api/memory');

                        let memoryData =
                            await memoryResponse.json();

                        document.getElementById("memory")
                            .innerText = memoryData.usedMemory;

                        // System API
                        let systemResponse =
                            await fetch('/api/system');

                        let systemData =
                            await systemResponse.json();

                        document.getElementById("os")
                            .innerText = systemData.osName;
                    }

                    setInterval(loadData, 3000);

                    window.onload = loadData;

                </script>

            </head>

            <body>

                <h1>DevOps Monitoring Dashboard</h1>

                <div class="dashboard">

                    <div class="card">
                        <h2>Health Status</h2>
                        <div class="value" id="health">
                            Loading...
                        </div>
                    </div>

                    <div class="card">
                        <h2>CPU Load</h2>
                        <div class="value" id="cpu">
                            Loading...
                        </div>
                    </div>

                    <div class="card">
                        <h2>Used Memory</h2>
                        <div class="value" id="memory">
                            Loading...
                        </div>
                    </div>

                    <div class="card">
                        <h2>Operating System</h2>
                        <div class="value" id="os">
                            Loading...
                        </div>
                    </div>

                </div>

            </body>

            </html>
            """;

    exchange.getResponseHeaders().set("Content-Type", "text/html");

    exchange.sendResponseHeaders(200, html.length());

    OutputStream os = exchange.getResponseBody();

    os.write(html.getBytes());

    os.close();
});

        // Health API
        server.createContext("/api/health", exchange -> {

            String response = "{\"status\":\"UP\"}";

            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();

            os.write(response.getBytes());

            os.close();
        });
        //CPU API
        server.createContext("/api/cpu", exchange -> {

    OperatingSystemMXBean osBean =
            ManagementFactory.getOperatingSystemMXBean();

    double cpuLoad = osBean.getSystemLoadAverage();

    String response = "{\"cpuLoad\":\"" + cpuLoad + "\"}";

    exchange.sendResponseHeaders(200, response.length());

    OutputStream os = exchange.getResponseBody();

    os.write(response.getBytes());

    os.close();
});
server.createContext("/api/memory", exchange -> {

    Runtime runtime = Runtime.getRuntime();

    long totalMemory = runtime.totalMemory();

    long freeMemory = runtime.freeMemory();

    long usedMemory = totalMemory - freeMemory;

    String response =
            "{"
            + "\"totalMemory\":\"" + totalMemory + "\","
            + "\"freeMemory\":\"" + freeMemory + "\","
            + "\"usedMemory\":\"" + usedMemory + "\""
            + "}";

    exchange.sendResponseHeaders(200, response.length());

    OutputStream os = exchange.getResponseBody();

    os.write(response.getBytes());

    os.close();
});
server.createContext("/api/system", exchange -> {

    String osName = System.getProperty("os.name");

    String javaVersion = System.getProperty("java.version");

    String architecture = System.getProperty("os.arch");

    int processors = Runtime.getRuntime().availableProcessors();

    String response =
            "{"
            + "\"osName\":\"" + osName + "\","
            + "\"javaVersion\":\"" + javaVersion + "\","
            + "\"architecture\":\"" + architecture + "\","
            + "\"processors\":\"" + processors + "\""
            + "}";

    exchange.sendResponseHeaders(200, response.length());

    OutputStream os = exchange.getResponseBody();

    os.write(response.getBytes());

    os.close();
});

        System.out.println("Server started on port 9090");

        server.start();
    }
}