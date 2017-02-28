package org.mi.core.common;

public final class Constant {

    /**
     * 全局定义
     */
    public static final class Global {

        /** 状态*/
        public static enum State {
            Enable(1, "启用"),
            Disable(0, "禁用");

            private int value;
            private String title;

            private State(int value, String title) {
                this.value = value;
                this.title = title;
            }

            public int getValue() {
                return value;
            }

            public String getTitle() {
                return title;
            }
        }
    }

	public static class Application{

		/**
		 * 提示信息页面样式控制
		 */
		public static final String COMMON_SUCCESS_ALERTINFO = "success";
		public static final String COMMON_INFO_ALERTINFO = "info";
		public static final String COMMON_WARNING_ALERTINFO = "warning";
		public static final String COMMON_DANGER_ALERTINFO = "danger";
	}

    public static class I18nMessage {

        public static final String RECORD_NOT_FOUND = "record.not.found";

        public static final String SAVE_SUCCESS = "save.success.msg";
        public static final String SAVE_FAILURE = "save.failure.msg";

        public static final String DEL_SUCCESS = "del.success.msg";
        public static final String DEL_FAILURE = "del.failure.msg";
        
        public static final String CHECK_RECORD_EXISTS = "check.record.exists";
        public static final String CHECK_RECORD_NOT_EXISTS = "check.record.notexists";
        
        public static final String NEED_ENTER_PASSWORD = "need.enter.password";

        public static final String LOGIN_ERROR = "login.error";

        public static final String USER_PASS_FORMAT_ERR = "user.pass.format.err";

        public static final String USER_PASS_ORIGINAL_CHECK_ERR= "user.pass.original.check.err" ;

        public static final String USER_PASS_UPDATE_SUCCESS = "user.pass.update.success";
        
        public static final String REPLY_SUCCESS = "reply.success.msg";
        public static final String REPLY_FAILURE = "reply.failure.msg";
        
        public static final String SAVE_LIMIT = "save.limit.msg";
        
        public static final String ALREADY_EXITS = "already.exits.msg";
        
    }

}
