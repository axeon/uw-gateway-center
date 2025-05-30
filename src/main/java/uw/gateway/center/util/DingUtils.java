package uw.gateway.center.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uw.httpclient.http.HttpConfig;
import uw.httpclient.http.HttpInterface;
import uw.httpclient.json.JsonInterfaceHelper;

/**
 * 发送钉钉通知的工具类。
 */
public class DingUtils {

    private static final Logger log = LoggerFactory.getLogger( DingUtils.class );

    private static final HttpInterface HTTP_INTERFACE = new JsonInterfaceHelper( HttpConfig.builder()
            .retryOnConnectionFailure( true )
            .connectTimeout( 10_000L )
            .readTimeout( 10_000L )
            .writeTimeout( 10_000L )
            .build() );


    /**
     * 发送钉钉通知。
     *
     * @param noticeUrl
     * @param title
     * @param body
     */
    public static void send(String noticeUrl, String title, String body) {
        DingMarkdownMsg ding = new DingMarkdownMsg();
        ding.getMarkdown().setTitle( title );
        ding.getMarkdown().setText( body );
        //发送通知。
        try {
            HTTP_INTERFACE.postBodyForData( noticeUrl, ding );
        } catch (Exception e) {
            log.error( "钉钉通知发送失败！{}", e.getMessage(), e );
        }

    }


    /**
     * 钉钉的markdown格式信息。
     */
    public static class DingMarkdownMsg {

        private String msgtype = "markdown";

        private Markdown markdown = new Markdown();

        private At at = new At();


        public String getMsgtype() {
            return msgtype;
        }

        public void setMsgtype(String msgtype) {
            this.msgtype = msgtype;
        }

        public Markdown getMarkdown() {
            return markdown;
        }

        public void setMarkdown(Markdown markdown) {
            this.markdown = markdown;
        }

        public At getAt() {
            return at;
        }

        public void setAt(At at) {
            this.at = at;
        }

        private static class Markdown {
            private String title;

            private String text;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }

        private static class At {
            private String[] atMobiles;

            private String[] atUserIds;

            private boolean isAtAll = true;

            public String[] getAtMobiles() {
                return atMobiles;
            }

            public void setAtMobiles(String[] atMobiles) {
                this.atMobiles = atMobiles;
            }

            public String[] getAtUserIds() {
                return atUserIds;
            }

            public void setAtUserIds(String[] atUserIds) {
                this.atUserIds = atUserIds;
            }

            public boolean isAtAll() {
                return isAtAll;
            }

            public void setAtAll(boolean atAll) {
                isAtAll = atAll;
            }
        }

    }
}
