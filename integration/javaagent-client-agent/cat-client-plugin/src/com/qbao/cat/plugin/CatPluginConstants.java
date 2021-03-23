/**
 *
 */
package com.qbao.cat.plugin;


/**
 * @author andersen
 *
 */
public interface CatPluginConstants {
    /**
     * �����ߵ�ַ������
     */
    public static final String D_CLIENT_ADDR = "X-CAT-CLIENT-ADDR";
    /**
     * ������domain������
     */
    public static final String D_CLIENT_DOMAIN = "X-CAT-CLIENT-DOMAIN";
    /**
     * �����ߵ�ַ������
     */
    public static final String D_CALL_SERVER_ADDR = "X-CAT-SERVER-ADDR";
    /**
     * ������domain������
     */
    public static final String D_CALL_SERVER_DOMAIN = "X-CAT-SERVER-DOMAIN";

    /**
     *  Զ�̷�����Domain
     */
    public static final String E_CALLEE_APP = "Callee.app";
    /**
     * ���������Domain
     */
    public static final String E_CALL_APP = "Call.app";
    /**
     * Զ�̷���������Addr
     */
    public static final String E_CALLEE_ADDR = "Callee.Addr";

    /**
     * Զ�̵��ñ�ǲ�����
     */
    public static final String D_CALL_TRACE_MODE = "X-CAT-TRACE-MODE";

    /**
     * dubbo�����transaction-type
     */
    public static final String TYPE_DUBBO_SERVER = "Call.Dubbo.Server";

    /**
     * dubbo�ͻ���transaction-type
     */
    public static final String TYPE_DUBBO_CLIENT = "Call.Dubbo.Client";

    /**
     * http�����transaction-type
     */
    public static final String TYPE_HTTP_SERVER = "Call.Http.Server";

    /**
     * htt�ͻ���transaction-type
     */
    public static final String TYPE_HTTP_CLIENT = "Call.Http.Client";

    /**
     * http����˷�����
     */
    public static final String TYPE_URL_SERVER_RESOPONSE_CODE = "URL.Server.Response.Code";

    /**
     * http�ͻ��˴�����Ϣ��ȡ��x-forwarded-for
     */
    public static final String TYPE_URL_SERVER = "URL.Server";

    /**
     * http�ͻ���refer��Ϣ
     */
    public static final String TYPE_URL_SERVER_REFERER = "URL.Server.Referer";

    /**
     * http�ͻ���user-agent��Ϣ
     */
    public static final String TYPE_URL_SERVER_AGENT = "URL.Server.Agent";

    /**
     * http�ͻ�������ʽ
     */
    public static final String TYPE_URL_METHOD = "URL.Method";

    /**
     * ���ݿ����Ӵ�
     */
    public static final String TYPE_SQL_DATABASE = "SQL.Database";

    /**
     * ���÷���ַ
     */
    public static final String E_CLIENT_ADDR = "Client.addr";
}
