package com.Karchat.entity.blogcore;

import com.Karchat.dao.operation.DatabaseOperation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class BlogList implements Iterable<String>{

	private List<String> list = new LinkedList<>();
	private DatabaseOperation dbOperation = new DatabaseOperation();

	public void moveUp(int i) {
		//i是要移动的行的下标
		if (i > 0) {
			String swap = this.list.get(i-1);
			this.list.set(i-1, this.list.get(i));
			this.list.set(i, swap);
			dbOperation.moveUp(i);
		}
	}
	
	public void moveDown(int i) {
		if (i < this.list.size() - 1) {
			String swap = this.list.get(i+1);
			this.list.set(i+1, this.list.get(i));
			this.list.set(i, swap);
			dbOperation.moveDown(i);
		}
	}
	
	public void add(String item) {
		this.list.add(item);
		dbOperation.add(item, this.list.size() - 1);
	}
	public void init(String item){
		this.list.add(item);
	}
	
	public void editAt(int i, String newValue){
		if (i >= 0 && i < this.list.size()) {
			this.list.set(i, newValue);
			dbOperation.editAt(newValue, i);
		}
	}
	
	public void removeAt(int i) {
		if (i >= 0 && i < this.list.size()) {
			this.list.remove(i);
			dbOperation.removeAt(i);
		}
	}
	
	public int size() {
		return list.size();
	}
	
	public String elementAt(int i) {
		return list.get(i);
	}
	
	@Override
	public Iterator<String> iterator() {
		return list.iterator();
	}
}
