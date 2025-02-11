package com.dnd.sbooky.core.point;

import com.dnd.sbooky.core.member.MemberEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Entity
@Table(name = "point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PointEntity {

    private static final String ENTITY_PREFIX = "point";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ENTITY_PREFIX + "_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column(name = ENTITY_PREFIX + "_current")
    private int current;

    @Column(name = ENTITY_PREFIX + "_plus")
    private int plus;

    @Column(name = ENTITY_PREFIX + "_minus")
    private int minus;

    @Column(name = ENTITY_PREFIX + "_policy_id")
    @Convert(converter = PointPolicyConverter.class)
    private PointPolicy pointPolicy;

    @CreationTimestamp
    @Column(name = ENTITY_PREFIX + "_created_at")
    private LocalDateTime createdAt;

    @Builder(access = AccessLevel.PRIVATE)
    private PointEntity(
            MemberEntity member, int current, int plus, int minus, PointPolicy pointPolicy) {
        this.member = member;
        this.current = current;
        this.plus = plus;
        this.minus = minus;
        this.pointPolicy = pointPolicy;
    }

    public static PointEntity newInstance(MemberEntity member, int current, PointPolicy pointPolicy) {
        return PointEntity.builder()
                .member(member)
                .current(current)
                .plus(pointPolicy.getPlus())
                .minus(pointPolicy.getMinus())
                .pointPolicy(pointPolicy)
                .build();
    }
}
