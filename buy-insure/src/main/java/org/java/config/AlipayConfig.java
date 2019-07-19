package org.java.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {
    //alipay_public_key、return_url、notify_url。
    //bizContent
    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016091600526356";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCknZieHVsGzCwHDo079jbAc/j4CJAvWrckVp/h13Lotdhukqx/FhY21K3/pdVHehv8E3JID1nJMhD1y8SrPcNrJT5FTbB7KMRhUHxUbo5Udimj1e+XgkZqz1E9RcmaIJQ79vpxjjitL4/WBibbCaAY2boQXYlyTFT/6K2iOKtyXoaf3R5hB4tsRbs6lf1cUF0pDf6Tv1OwbxXFkyGsmUE7zNOqIyxPUkaPVFh1Oc5MQXkPEefK0dNtf3fXq9gArWGWw8rtUxxrDuLgryIZv1MTP8hto/x+f766fAoU0RnFtpIBBktGfcSnOkB3D6Z3D/ZZn08h4IBtmxHe5LWnIg+FAgMBAAECggEBAKQnR868hU41Ad6To0FdGGI4A9kLaQTf2GuTOmJSn5Lx637wIrWkh8hrs4uQiit52Dg6CCdVr3pTw9foOBlvQNUy0XL4svlNwO8t/J2RlbsiDgoDx6V1UtUmIeQRGKXqwQc/FaVxZpFFMZECl3KrmYvOap3xOKPS/JwpIiT3j/g+MxaQkkGwvyj7GiPALFrZDBPhgtuQt6U0dpJqbWzsfDv/BQZ0XsJTaC2isNYaSMZjf4aq1LCu8J95V+yQx5CFlXq6I3nXwqtkcxAaYhTT5rf8yxAlaszLA6HZiZxUmY0/1MNWHTY3SGa4HTr/PsSohj4RtIPXTOBDvOdTGQ3Oi40CgYEA1UwqfKMDXPgP1B3Nut0MHxBDDqcRRoMnQNagQZvka0VkqKKZy2DuIlxZpbog+zxdc8eN1Nu5L3Po2+XDwnP5+aqlQn0WY0G4sn7nJ0dWjseoJ1UPnQQqWPRKcqNjOdybXFWu2LWP2ftXHfYHmQZCSiOniTW57BMV9U1R2UIWkSMCgYEAxZJnzG1EdIyXEXMxt8cVLYcFJMtlrzw0ZEKGnta6etbJT3MY30sGMYl4qEzY5HuryyvP6cornTtap7BkjGRaak6DQDuj42Vf/O5zFvRPBFd8nrz0ZSwxudluLEn2c6jf6USPGMxt9JT4PKOaSjZXluYjwshcLfPBCTyxLrD4KzcCgYBOQ39Hckz2nPK54yGs0hTzhPUF+FlHZTJPATGWQUxD7BduJROHY46dPwOJ4szdQp4LsIojM2g0uDQWE62LuSBc8rdmuR0w2Fmb7SKF8BAbnbjejcCH3ZJoR2LVw4vSHFdJPwtD6wLYeffSP4c0J+7E42ZC21rX+3zQIEnLDQvYvwKBgQCCcDpfWPSNDhTezHLSpnlw6WeX90FXBCSC3PkL/TB40T1vWsndAidApUYntzSrBItTQ8Ws0HEjCYC8nVSE6+xVqyg0TCvTgaZx0uvhB8LzVt9L3exsr1TVIpv+WYaArcnGtCDVeoJNq9yIDGtc+6uwUJjjjGxvWjLj6lIARY5S2QKBgQC2JloVSkFFPRRys08ZZY51j/Vo9QMpLd3cnDc+Gt+vCnui+sZbnnUJVfLr5oN1UQSFqERazhbd6VS5XmgQEbO+swUkwkVeu5pGpIHgXYV1RRucx3dl/LENiCwwq/9bq68r/3icBIPfAMB98UOdblYqu+B1fVy7QHq31P+cPEXikg==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApJ2Ynh1bBswsBw6NO/Y2wHP4+AiQL1q3JFaf4ddy6LXYbpKsfxYWNtSt/6XVR3ob/BNySA9ZyTIQ9cvEqz3DayU+RU2weyjEYVB8VG6OVHYpo9Xvl4JGas9RPUXJmiCUO/b6cY44rS+P1gYm2wmgGNm6EF2JckxU/+itojircl6Gn90eYQeLbEW7OpX9XFBdKQ3+k79TsG8VxZMhrJlBO8zTqiMsT1JGj1RYdTnOTEF5DxHnytHTbX9316vYAK1hlsPK7VMcaw7i4K8iGb9TEz/IbaP8fn++unwKFNEZxbaSAQZLRn3EpzpAdw+mdw/2WZ9PIeCAbZsR3uS1pyIPhQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8100";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8100";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
