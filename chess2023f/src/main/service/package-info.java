/**
 * The service package contains the Chess service endpoint implementations. This
 * includes providing methods for the HTTP handlers to call, and pushing application
 * data into the persistence layer.
 * <p>
 * The following services and endpoints are provided for the Chess application. For
 * a full description of the endpoint refer to the
 * <a href="https://github.com/softwareconstruction240/softwareconstruction/blob/main/chess/3-web-api/web-api.md"></a>Chess Requirements</a>.
 * <p>
 * <b>AdminService</b>
 * <p><code>[DELETE] /db - Clear application data</code>
 * <p>
 * <b>UserService</b>
 * <p><code>[POST] /user - Register user</code>
 * <pre>{ "username":"", "password":"", "email":"" }</pre>
 * <p>
 * <b>AuthService</b>
 * <p><code>[POST] /session - Create session</code>
 * <pre>{ "username":"", "password":"" }</pre>
 * <p><code>[DELETE] /session - Delete session</code>
 * <p>
 * <b>GameService</b>
 * <p><code>[GET] /game - Lists games</code>
 * <p><code>[POST] / game - Create game</code>
 * <pre>{ "gameName":"" }</pre>
 * <p><code>[PUT] / - Join game</code>
 * <pre>{ "playerColor":"WHITE/BLACK", "gameID": 1234 }</pre>
 */
package service;