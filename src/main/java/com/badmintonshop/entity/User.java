package com.badmintonshop.entity;

import com.badmintonshop.entity.enums.Gender;
import com.badmintonshop.entity.enums.PlayingStyle;
import com.badmintonshop.entity.enums.SkillLevel;
import com.badmintonshop.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User Entity - Khách hàng
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_users_email", columnList = "email"),
    @Index(name = "idx_users_phone", columnList = "phone"),
    @Index(name = "idx_users_status", columnList = "status"),
    @Index(name = "idx_users_deleted_at", columnList = "deleted_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    // ==================== OAuth ====================
    @Column(name = "google_oauth_id", unique = true, length = 255)
    private String googleOauthId;

    // ==================== Email Verification ====================
    @Column(name = "is_email_verified")
    @Builder.Default
    private Boolean isEmailVerified = false;

    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;

    // ==================== Playing Profile ====================
    @Enumerated(EnumType.STRING)
    @Column(name = "playing_style")
    private PlayingStyle playingStyle;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_level")
    private SkillLevel skillLevel;

    @Column(name = "preferred_racket_weight", length = 10)
    private String preferredRacketWeight;

    @Column(name = "preferred_tension_min", precision = 4, scale = 1)
    private BigDecimal preferredTensionMin;

    @Column(name = "preferred_tension_max", precision = 4, scale = 1)
    private BigDecimal preferredTensionMax;

    @Column(name = "preferred_string_type", length = 100)
    private String preferredStringType;

    // ==================== Status ====================
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    // ==================== Relationships ====================
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<UserAddress> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Wishlist> wishlists = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    // ==================== Helper Methods ====================
    public void addAddress(UserAddress address) {
        addresses.add(address);
        address.setUser(this);
    }

    public void removeAddress(UserAddress address) {
        addresses.remove(address);
        address.setUser(null);
    }

    public UserAddress getDefaultAddress() {
        return addresses.stream()
                .filter(UserAddress::getIsDefault)
                .findFirst()
                .orElse(null);
    }

    public boolean isActive() {
        return status == UserStatus.ACTIVE && getDeletedAt() == null;
    }

    public void recordLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }
}
