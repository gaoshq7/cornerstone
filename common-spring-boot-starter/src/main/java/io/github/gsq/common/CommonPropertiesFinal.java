package io.github.gsq.common;

/**
 * Project : cornerstone
 * Class : io.github.gsq.common.CommonPropertiesFinal
 *
 * @author : gsq
 * @date : 2024-10-29 17:17
 * @note : It's not technology, it's art !
 **/
public final class CommonPropertiesFinal {

    public static final String APPLICATION_ID = "spring.application.name";

    private static final String REQUEST = "request";

    public static final String REQUEST_TIME_OUT = REQUEST + ".timeout";

    public static final String REQUEST_LOG = REQUEST + ".log";

    public static final String REQUEST_COPY_INPUT_STREAM = REQUEST + ".copyInputStream";

    public static final String REQUEST_PARAMETER_XSS = REQUEST + ".parameterXss";

    public static final String REQUEST_PARAMETER_TRIM_ALL = REQUEST + ".trimAll";

    public static final String IP_DEFAULT_HEADER_NAME = "ip.defaultHeaderName";

    private static final String INTERCEPTOR = "interceptor";

    public static final String INTERCEPTOR_INIT_PACKAGE_NAME = INTERCEPTOR + ".initPackageName";

    public static final String INTERCEPTOR_RESOURCE_HANDLER = INTERCEPTOR + ".resourceHandler";

    public static final String INTERCEPTOR_RESOURCE_LOCATION = INTERCEPTOR + ".resourceLocation";

    private static final String PRELOAD = "preload";

    public static final String PRELOAD_PACKAGE_NAME = PRELOAD + ".packageName";

    public static final String[] IMAGE_EXTENSION = new String[] {"gif", "jpg", "png"};

    public static final String SCAN_ROOT_PACKAGE = "cn.galaxy.loader";

}
