package com.photolocator.cassandra;

public abstract class CassandraCallback {
	public void passwdRetrived(String passwd){
		
	}
	
	public void registered(boolean ret){
		
	}
	
	public void dataInserted(boolean ret){
		
	}
	
	public  void dataReaded(CassandraDataUnit cdu){
		
	}
}
