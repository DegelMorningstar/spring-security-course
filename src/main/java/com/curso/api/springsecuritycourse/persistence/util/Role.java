package com.curso.api.springsecuritycourse.persistence.util;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMINISTRATOR(Arrays.asList(
            RolePermission.READ_ALL_PRODUCTS,
            RolePermission.READ_ONE_PRODUCTS,
            RolePermission.CREATE_ONE_PRODUCTS,
            RolePermission.UPDATE_ONE_PRODUCTS,
            RolePermission.DISABLE_ONE_PRODUCTS,
            RolePermission.READ_ALL_CATEGORY,
            RolePermission.READ_ONE_CATEGORY,
            RolePermission.CREATE_ONE_CATEGORY,
            RolePermission.UPDATE_ONE_CATEGORY,
            RolePermission.DISABLE_ONE_CATEGORY,
            RolePermission.READ_PROFILE
    )),
    ASSISTANT_ADMINISTRATOR(Arrays.asList(
            RolePermission.READ_ALL_PRODUCTS,
            RolePermission.READ_ONE_PRODUCTS,
            RolePermission.UPDATE_ONE_PRODUCTS,

            RolePermission.READ_ALL_CATEGORY,
            RolePermission.READ_ONE_CATEGORY,
            RolePermission.UPDATE_ONE_CATEGORY,
            RolePermission.READ_PROFILE
    )),
    CUSTOMER(Arrays.asList(
            RolePermission.READ_PROFILE
    ));

    private List<RolePermission> permissions;

    Role(List<RolePermission> permissions) {
        this.permissions = permissions;
    }

    public List<RolePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolePermission> permissions) {
        this.permissions = permissions;
    }
}
