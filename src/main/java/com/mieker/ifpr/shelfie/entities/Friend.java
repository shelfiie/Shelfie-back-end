package com.mieker.ifpr.shelfie.entities;


import com.mieker.ifpr.shelfie.entities.enumeration.FriendshipRequestStatus;

import javax.xml.crypto.Data;
import java.util.Date;

public class Friend {
    private Long id;
    private User userA;
    private User userB;
    private FriendshipRequestStatus friendshipRequestStatus;
    private Date friendshipRequestDate;
    private Date friendshipRequestAccepted;
}
