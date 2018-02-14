package com.niit.portalbackend.dao;

import java.util.List;

import com.niit.portalbackend.Friend;

public interface FriendDao {
	
	boolean addFriend(Friend friend);
	
	boolean deleteFriend(Friend friend);
	
	boolean updateFriend(Friend friend);
	
	Friend getFriend(int id);
	
	List<Friend> allFriends(String username);
	
	

}