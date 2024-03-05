package com.mieker.ifpr.shelfie.entity;


import com.mieker.ifpr.shelfie.entity.enumeration.FriendshipRequestStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "tb_friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "friend_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id_a", nullable=false)
    private User userA;
    @ManyToOne
    @JoinColumn(name="user_id_b", nullable=false)
    private User userB;
    @Enumerated(EnumType.STRING)
    @Column(name = "friendship_request_status", nullable = false)
    private FriendshipRequestStatus friendshipRequestStatus;
    @Column(name = "friendship_request_date", nullable = false)
    private Date friendshipRequestDate;
    @Column(name = "friendship_request_accepted")
    private Date friendshipRequestAccepted;
}
