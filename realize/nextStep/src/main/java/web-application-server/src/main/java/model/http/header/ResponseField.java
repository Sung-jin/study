package model.http.header;

public enum ResponseField {
    ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin", "Specifying which web sites can participate in cross-origin resource sharing."),
    ACCESS_CONTROL_ALLOW_CREDENTIALS("Access-Control-Allow-Credentials", "Specifying which web sites can participate in cross-origin resource sharing."),
    ACCESS_CONTROL_EXPOSE_HEADERS("Access-Control-Expose-Headers", "Specifying which web sites can participate in cross-origin resource sharing."),
    ACCESS_CONTROL_MAX_AGE("Access-Control-Max-Age", "Specifying which web sites can participate in cross-origin resource sharing."),
    ACCESS_CONTROL_ALLOW_METHODS("Access-Control-Allow-Methods", "Specifying which web sites can participate in cross-origin resource sharing."),
    ACCESS_CONTROL_ALLOW_HEADERS("Access-Control-Allow-Headers", "Specifying which web sites can participate in cross-origin resource sharing."),
    ACCEPT_PATCH("AcceptPatch", "Specifies which patch document formats this server supports"),
    ACCEPT_RANGES("Accept-Ranges", "What partial content range types this server supports via byte serving"),
    AGE("Age", "The age the object has been in a proxy cache in seconds"),
    ALLOW("Allow", "Valid methods for a specified resource. To be used for a 405 Method not allowed"),
    ALT_SVC("ALT_SVC", "A server uses \"Alt-Svc\" header (meaning Alternative Services) to indicate that its resources can also be accessed at a different network location (host or port) or using a different protocol When using HTTP/2, servers should instead send an ALTSVC frame."),
    CACHE_CONTORL("Cache-Control", "Tells all caching mechanisms from server to client whether they may cache this object. It is measured in seconds"),
    CONNECTION("Connection", "Control options for the current connection and list of hop-by-hop response fields. Must not be used with HTTP/2."),
    CONTENT_DISPOSITION("Content-Disposition", "An opportunity to raise a \"File Download\" dialogue box for a known MIME type with binary format or suggest a filename for dynamic content. Quotes are necessary with special characters."),
    CONTENT_ENCODING("Content-Encoding", "The type of encoding used on the data. See HTTP compression."),
    CONTENT_LANGUAGE("Content-Language", "The natural language or languages of the intended audience for the enclosed content"),
    CONTENT_LENGTH("Content-Length", "The length of the response body in octets (8-bit bytes)"),
    CONTENT_LOCATION("Content-Location", "An alternate location for the returned data"),
    CONTENT_MD5("Content-MD5", "A Base64-encoded binary MD5 sum of the content of the response"),
    CONTENT_RANGE("Content-Range", "Where in a full body message this partial message belongs"),
    CONTENT_TYPE("Content-Type", "The MIME type of this content"),
    DATE("Date", "The date and time that the message was sent (in \"HTTP-date\" format as defined by RFC 7231)"),
    DELTA_BASE("Delta-Base", "Specifies the delta-encoding entity tag of the response."),
    ETAG("ETag", "An identifier for a specific version of a resource, often a message digest"),
    EXPIRES("Expires", "Gives the date/time after which the response is considered stale (in \"HTTP-date\" format as defined by RFC 7231)"),
    IM("IM", "Instance-manipulations applied to the response."),
    LAST_MODIFIED("Last-Modified", "The last modified date for the requested object (in \"HTTP-date\" format as defined by RFC 7231)"),
    LINK("Link", "Used to express a typed relationship with another resource, where the relation type is defined by RFC 5988"),
    LOCATION("Location", "Used in redirection, or when a new resource has been created.\nExample 1: Location: http://www.w3.org/pub/WWW/People.html\nExample 2: Location: /pub/WWW/People.html"),
    P3P("P3P", "This field is supposed to set P3P policy, in the form of P3P:CP=\"your_compact_policy\". However, P3P did not take off, most browsers have never fully implemented it, a lot of websites set this field with fake policy text, that was enough to fool browsers the existence of P3P policy and grant permissions for third party cookies."),
    PRAGMA("Pragma", "Implementation-specific fields that may have various effects anywhere along the request-response chain."),
    PROXY_AUTHENTICATE("Proxy-Authenticate", "Request authentication to access the proxy."),
    PUBLIC_KEY_PINS("Public-Key-Pins", "HTTP Public Key Pinning, announces hash of website's authentic TLS certificate"),
    RETRY_AFTER("Retry-After", "If an entity is temporarily unavailable, this instructs the client to try again later. Value could be a specified period of time (in seconds) or a HTTP-date.\nExample 1: Retry-After: 120\nExample 2: Retry-After: Fri, 07 Nov 2014 23:59:59 GMT"),
    SERVER("Server", "A name for the server"),
    SET_COOKIE("Set-Cookie", "An HTTP cookie"),
    STRICT_TRANSPORT_SECURITY("Strict-Transport-Security", "A HSTS Policy informing the HTTP client how long to cache the HTTPS only policy and whether this applies to subdomains."),
    TRAILER("Trailer", "The Trailer general field value indicates that the given set of header fields is present in the trailer of a message encoded with chunked transfer coding."),
    TRANSFER_ENCODING("Transfer-Encoding", "The form of encoding used to safely transfer the entity to the user. Currently defined methods are: chunked, compress, deflate, gzip, identity. Must not be used with HTTP/2."),
    TK("Tk", "Tracking Status header, value suggested to be sent in response to a DNT(do-not-track), possible values:\n" +
            "            \"!\" — under construction\n" +
            "\"?\" — dynamic\n" +
            "\"G\" — gateway to multiple parties\n" +
            "\"N\" — not tracking\n" +
            "\"T\" — tracking\n" +
            "\"C\" — tracking with consent\n" +
            "\"P\" — tracking only if consented\n" +
            "\"D\" — disregarding DNT\n" +
            "\"U\" — updated"),
    UPGRADE("Upgrade", "Ask the client to upgrade to another protocol. Must not be used in HTTP/2"),
    VARY("Vary", "Tells downstream proxies how to match future request headers to decide whether the cached response can be used rather than requesting a fresh one from the origin server."),
    VIA("Via", "Informs the client of proxies through which the response was sent."),
    WARNING("Warning", "A general warning about possible problems with the entity body."),
    WWW_AUTHENTICATE("WWW-Authenticate", "Indicates the authentication scheme that should be used to access the requested entity."),
    X_FRAME_OPTIONS("X-Frame-Options", "Clickjacking protection: deny - no rendering within a frame, sameorigin - no rendering if origin mismatch, allow-from - allow from specified location, allowall - non-standard, allow from any location"),
    // standard

    CONTENT_SECURITY_POLICY("Content-Security-Policy", "Content Security Policy definition."),
    X_CONTENT_SECURITY_POLICY("X-Content-Security-Policy", "Content Security Policy definition."),
    X_WEBKIT_CSP("X-WebKit-CSP", "Content Security Policy definition."),
    REFRESH("Refresh", "Used in redirection, or when a new resource has been created. This refresh redirects after 5 seconds. Header extension introduced by Netscape and supported by most web browsers."),
    STATUS("Status", "CGI header field specifying the status of the HTTP response. Normal HTTP responses use a separate \"Status-Line\" instead, defined by RFC 7230."),
    TIMING_ALLOW_ORIGIN("Timing-Allow-Origin", "The Timing-Allow-Origin response header specifies origins that are allowed to see values of attributes retrieved via features of the Resource Timing API, which would otherwise be reported as zero due to cross-origin restrictions."),
    X_CONTENT_CURATION("X-Content-Duration", "Provide the duration of the audio or video in seconds; only supported by Gecko browsers"),
    X_CONTENT_TYPE_OPTIONS("X-Content-Type-Options", "The only defined value, \"nosniff\", prevents Internet Explorer from MIME-sniffing a response away from the declared content-type. This also applies to Google Chrome, when downloading extensions."),
    X_POWERED_BY("X-Powered-By", "Specifies the technology (e.g. ASP.NET, PHP, JBoss) supporting the web application (version details are often in X-Runtime, X-Version, or X-AspNet-Version)"),
    X_REQUEST_ID("X-Request-ID", "Correlates HTTP requests between a client and server."),
    X_CORRELATION_ID("X-Correlation-ID", "Correlates HTTP requests between a client and server."),
    X_UA_COMPATIBLE("X-UA-Compatible", "Recommends the preferred rendering engine (often a backward-compatibility mode) to use to display the content. Also used to activate Chrome Frame in Internet Explorer."),
    X_XSS_PROTECTION("X-XSS-Protection", "Cross-site scripting (XSS) filter");
    // non-standard

    private String key;
    private String desc;

    ResponseField(String key, String desc) {
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
