package com.badmintonshop.entity.enums;

/**
 * User Status - Values must match MySQL ENUM exactly (lowercase)
 */
public enum UserStatus {
    active,      // Hoạt động bình thường
    banned,      // Cấm vĩnh viễn (vi phạm TOS)
    locked       // Tạm khóa (nhập sai pass nhiều lần)
}

