package com.mieker.ifpr.shelfie.entity;


import com.mieker.ifpr.shelfie.entity.enumeration.FriendshipRequestStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "friend_id", nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name="user_id_a", nullable=false, updatable = false)
    private User userA;
    @ManyToOne
    @JoinColumn(name="user_id_b", nullable=false, updatable = false)
    private User userB;
    @Enumerated(EnumType.STRING)
    @Column(name = "friendship_request_status", nullable = false)
    private FriendshipRequestStatus friendshipRequestStatus;
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "friendship_request_date", nullable = false, updatable = false)
    private Date friendshipRequestDate;
    @Column(name = "friendship_request_accepted")
    private Date friendshipRequestAccepted;
}
