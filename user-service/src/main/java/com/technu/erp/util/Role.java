package com.technu.erp.util;

import static com.technu.erp.constant.AuthorityConstant.*;

public enum Role {
        ROLE_MANAGER(MANAGER_AUTHORITIES),
        ROLE_ADMIN(ADMIN_AUTHORITIES),
        ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

        private String[] authorities;

        Role(String... authorities) {
            this.authorities = authorities;
        }

        public String[] getAuthorities() {
            return authorities;
        }
}