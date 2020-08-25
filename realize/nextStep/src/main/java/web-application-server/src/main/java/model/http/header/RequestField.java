package model.http.header;

public enum RequestField {
    AIM("A-IM", "Acceptable instance-manipulations for the request."),
    ACCEPT("Accept", "Media type(s) that is/are acceptable for the response."),
    ACCEPT_CHARSET("Accept-Datetime", "Character sets that are acceptable."),
    ACCEPT_DATETIME("Accept-Datetime", "Acceptable version in time."),
    ACCEPT_ENCODING("Accept-Encoding", "List of acceptable encodings."),
    ACCEPT_LANGUAGE("Accept-Language", "List of acceptable human languages for response."),
    ACCESS_CONTROL_REQUEST_METHOD("Access-Control-Request-Method", "Initiates a request for cross-origin resource sharing with Origin."),
    ACCESS_CONTORL_REQUEST_HEADERS("Access-Control-Request-Headers", "Initiates a request for cross-origin resource sharing with Origin ."),
    AUTHORIZATION("Authorization", "Authentication credentials for HTTP authentication."),
    CACHE_CONTROL("Cache-Control", "Used to specify directives that must be obeyed by all caching mechanisms along the request-response chain."),
    CONNECTION("Connection", "Control options for the current connection and list of hop-by-hop request fields."),
    CONTENT_ENCODING("Content-Encoding", "The type of encoding used on the data."),
    CONTENT_LENGTH("Content-Length", "The length of the request body in octets (8-bit bytes)."),
    CONTENT_MD5("Content-MD5", "A Base64-encoded binary MD5 sum of the content of the request body."),
    CONTENT_TYPE("Content-Type", "The Media type of the body of the request (used with POST and PUT requests)."),
    COOKIE("Cookie", "An HTTP cookie previously sent by the server with Set-Cookie"),
    DATE("Date", "The date and time at which the message was originated"),
    EXPECT("Expect", "Indicates that particular server behaviors are required by the client."),
    FORWARDED("Forwarded", "Disclose original information of a client connecting to a web server through an HTTP proxy."),
    FROM("From", "The email address of the user making the request."),
    HOST("Host", "The domain name of the server (for virtual hosting), and the TCP port number on which the server is listening. The port number may be omitted if the port is the standard port for the service requested.\nMandatory since HTTP/1.1.[16] If the request is generated directly in HTTP/2, it should not be used."),
    HTTP2_SETTINGS("HTTP2-Settings", "A request that upgrades from HTTP/1.1 to HTTP/2 MUST include exactly one HTTP2-Setting header field. The HTTP2-Settings header field is a connection-specific header field that includes parameters that govern the HTTP/2 connection, provided in anticipation of the server accepting the request to upgrade."),
    IF_MATCH("If-Match", "Only perform the action if the client supplied entity matches the same entity on the server. This is mainly for methods like PUT to only update a resource if it has not been modified since the user last updated it."),
    IF_MODIFIED_SINCE("If-Modified-Since", "Allows a 304 Not Modified to be returned if content is unchanged."),
    IF_NONE_MATCH("If-None-Match", "Allows a 304 Not Modified to be returned if content is unchanged, see HTTP ETag."),
    IF_RANGE("If-Range", "If the entity is unchanged, send me the part(s) that I am missing; otherwise, send me the entire new entity."),
    IF_UNMODIFIED_SINCE("If-Unmodified-Since", "Only send the response if the entity has not been modified since a specific time."),
    MAX_FORWARDS("Max-Forwards", "Limit the number of times the message can be forwarded through proxies or gateways."),
    ORIGIN("Origin", "Initiates a request for cross-origin resource sharing (asks server for Access-Control-* response fields)."),
    PRAGMA("Pragma", "Implementation-specific fields that may have various effects anywhere along the request-response chain."),
    PROXY_AUTHORIZATION("Proxy-Authorization", "Authorization credentials for connecting to a proxy."),
    RANGE("Range", "Request only part of an entity. Bytes are numbered from 0."),
    REFERER("Referer", "This is the address of the previous web page from which a link to the currently requested page was followed. (The word \"referrer\" has been misspelled in the RFC as well as in most implementations to the point that it has become standard usage and is considered correct terminology)"),
    TE("TE", "The transfer encodings the user agent is willing to accept: the same values as for the response header field Transfer-Encoding can be used, plus the \"trailers\" value (related to the \"chunked\" transfer method) to notify the server it expects to receive additional fields in the trailer after the last, zero-sized, chunk.\nOnly trailers is supported in HTTP/2.[13]"),
    TRAILER("Trailer", "The Trailer general field value indicates that the given set of header fields is present in the trailer of a message encoded with chunked transfer coding."),
    TRANSFER_ENCODING("Transfer-Encoding", "The form of encoding used to safely transfer the entity to the user. Currently defined methods are: chunked, compress, deflate, gzip, identity.\nMust not be used with HTTP/2."),
    USER_AGENT("User-Agent", "The user agent string of the user agent."),
    UPGRADE("Upgrade", "Ask the server to upgrade to another protocol.\nMust not be used in HTTP/2.[13]"),
    VIA("Via", "Informs the server of proxies through which the request was sent."),
    WARNING("Warning", "A general warning about possible problems with the entity body."),
    // standard

    UPGRADE_INSECURE_REQUESTS("Upgrade-Insecure-Requests", "Tells a server which (presumably in the middle of a HTTP -> HTTPS migration) hosts mixed content that the client would prefer redirection to HTTPS and can handle Content-Security-Policy: upgrade-insecure-requests\nMust not be used with HTTP/2"),
    X_REQUESTED_WITH("X-Requested-With", "Mainly used to identify Ajax requests (most JavaScript frameworks send this field with value of XMLHttpRequest); also identifies Android apps using WebView"),
    DNT("DNT","Requests a web application to disable their tracking of a user. This is Mozilla's version of the X-Do-Not-Track header field (since Firefox 4.0 Beta 11). Safari and IE9 also have support for this field. On March 7, 2011, a draft proposal was submitted to IETF. The W3C Tracking Protection Working Group is producing a specification."),
    X_FORWARED_FOR("X-Forwarded-For", "A de facto standard for identifying the originating IP address of a client connecting to a web server through an HTTP proxy or load balancer. Superseded by Forwarded header."),
    X_FORWARDED_HOST("X-Forwarded-Host", "A de facto standard for identifying the original host requested by the client in the Host HTTP request header, since the host name and/or port of the reverse proxy (load balancer) may differ from the origin server handling the request. Superseded by Forwarded header."),
    X_FORWARDED_PROTO("X-Forwarded-Proto", "A de facto standard for identifying the originating protocol of an HTTP request, since a reverse proxy (or a load balancer) may communicate with a web server using HTTP even if the request to the reverse proxy is HTTPS. An alternative form of the header (X-ProxyUser-Ip) is used by Google clients talking to Google servers. Superseded by Forwarded header."),
    FRONT_END_HTTPS("Front-End-Https", "Non-standard header field used by Microsoft applications and load-balancers"),
    X_HTTP_METHOD_OVERRIDE("X-Http-Method-Override", "Requests a web application to override the method specified in the request (typically POST) with the method given in the header field (typically PUT or DELETE). This can be used when a user agent or firewall prevents PUT or DELETE methods from being sent directly (note that this is either a bug in the software component, which ought to be fixed, or an intentional configuration, in which case bypassing it may be the wrong thing to do)."),
    X_ATT_DEVICEID("X-ATT-DeviceId", "Allows easier parsing of the MakeModel/Firmware that is usually found in the User-Agent String of AT&T Devices"),
    X_WAP_PROFILE("X-Wap-Profile", "Links to an XML file on the Internet with a full description and details about the device currently connecting. In the example to the right is an XML file for an AT&T Samsung Galaxy S2."),
    PROXY_CONNECTION("Proxy-Connection", "Implemented as a misunderstanding of the HTTP specifications. Common because of mistakes in implementations of early HTTP versions. Has exactly the same functionality as standard Connection field.\nMust not be used with HTTP/2."),
    X_UIDH("X-UIDH", "Server-side deep packet insertion of a unique ID identifying customers of Verizon Wireless; also known as \"perma-cookie\" or \"supercookie\""),
    X_CSRF_TOKEN("X-Csrf-Token", "Used to prevent cross-site request forgery. Alternative header names are: X-CSRFToken and X-XSRF-TOKEN"),
    X_REQUEST_ID("X-Request-ID", "Correlates HTTP requests between a client and server."),
    X_CORRELATION_ID("X-Correlation-ID", "Correlates HTTP requests between a client and server."),
    SAVE_DATA("Save-Data", "The Save-Data client hint request header available in Chrome, Opera, and Yandex browsers lets developers deliver lighter, faster applications to users who opt-in to data saving mode in their browser.");;
    // non-standard

    private String key;
    private String desc;

    RequestField(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
