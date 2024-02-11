package com.renault.pizzaauthserver.domain;

public class TestMain {
    public static void main(String[] args) {
        RoleList admin = RoleList.ADMIN;
        admin.getPermissions().forEach(System.out::println);
    }
}
