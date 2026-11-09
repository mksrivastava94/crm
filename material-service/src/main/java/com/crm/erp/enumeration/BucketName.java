package com.crm.erp.enumeration;

public enum BucketName {

        USER("user"),
        PRODUCT("product"),
        Bill("bill"),
        CLIENT("client"),
        EMPLOYEE("employee"),
        MATERIAL("material");

        private final String bucketName;

        BucketName(String bucketName) {
            this.bucketName = bucketName;
        }

        public String getBucketName() {
            return bucketName;
        }
}
