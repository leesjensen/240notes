/**
 * The chess application is divided into three major components.
 * <ol>
 * <li><b>Client</b> - Console based client</li>
 * <li><b>Server</b> - HTTP and Websocket server</li>
 * <li><b>database</b> - MySQL persistent store</li>
 * </ol>
 * <p>The server is further decomposed into three distinct components.</p>
 * <ol>
 * <li><b>HTTPServer</b> - Handles incoming HTTP requests and routes to the services.</li>
 * <li><b>{@link service Services}</b> - Handles service endpoint requests by making database requests.</li>
 * <li><b>{@link dataAccess DataAccess}</b> - Handles database requests.</li>
 * </ol>
 * <img alt="Server class diagram" src="doc-files/server.png" />
 * <p>
 * Refer to the {@link chess Chess} package for logic and rules for playing a game and is referenced from both the UI and Server.
 * <p>
 *
 * @see dataAccess
 * @see model
 * @see service
 */
module chess2023f {
}