package com.technu.erp.enumeration;

public enum BucketName {

        USER("user"),
        PRODUCT("product"),
        PURCHASEBILL("purchase-bill"),
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
