/**
 * The chess application is divided into three major components.
 * <ol>
 * <li><b>Client</b> - Console based client. There can be many active chess clients at any given point in time.
 * Clients are associated with each other by an active game. Clients are associated with a game as either a
 * player or an observer.</li>
 * <li><b>Server</b> - HTTP and Websocket server. The server handles all of the business logic of the application,
 * enforces the rules, and serialization of model objects.</li>
 * <li><b>database</b> - Persistent data store. Currently MySQL and Memory are the only supported data stores.</li>
 * </ol>
 * <img alt="Top Level Design" src="doc-files/top-level-design.png" />
 * <p>The server is further decomposed into three distinct components.</p>
 * <ol>
 * <li><b>{@link server Server}</b> - Handles incoming HTTP requests and routes to the services.</li>
 * <li><b>{@link service Services}</b> - Handles service endpoint requests by making database requests.</li>
 * <li><b>{@link dataAccess DataAccess}</b> - Handles database requests.</li>
 * <li><b>{@link model Model}</b> - All of the domain model objects. These are used for persisting data.</li>
 * </ol>
 * <img alt="Server class diagram" src="doc-files/server-class-diagram.png" />
 * <p>Refer to the {@link chess Chess} package for logic and rules for playing a game. This package is used by both the UI and Server.
 * <p><img alt="Chess piece diagram" src="doc-files/game-class-diagram.png" />
 * <p>
 *
 * @see dataAccess
 * @see model
 * @see service
 */