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
 * <p>[DELETE] /db - Clear application data
 * <p>
 * <b>UserService</b>
 * <p>[POST] /user - Register user
 * <p>
 * <b>AuthService</b>
 * <p>[POST] /session - Create session
 * <p>[DELETE] /session - Delete session
 * <p>
 * <b>GameService</b>
 * <p>[GET] /game - Lists games
 * <p>[POST] / game - Create game
 * <p>[PUT] / - Join game
 */
package service;