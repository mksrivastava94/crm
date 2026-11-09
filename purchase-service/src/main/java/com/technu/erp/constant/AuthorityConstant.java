package com.technu.erp.constant;

public class AuthorityConstant {
        public static final String[] MANAGER_AUTHORITIES = {"user:read", "user:update"};
        public static final String[] ADMIN_AUTHORITIES = {"user:read", "user:update", "user:create"};
        public static final String[] SUPER_ADMIN_AUTHORITIES = {"user:read", "user:update", "user:create", "user:delete"};

        private AuthorityConstant(){}
}
