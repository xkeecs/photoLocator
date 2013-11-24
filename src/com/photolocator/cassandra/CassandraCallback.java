package com.photolocator.cassandra;

import java.util.ArrayList;

public abstract class CassandraCallback {
	public void passwdRetrived(String passwd){
		
	}
	
	public void registered(boolean ret){
		
	}
	
	public void dataInserted(boolean ret){
		
	}
	
	public  void dataReaded(ArrayList<CassandraDataUnit> cdus){
		
	}
}
