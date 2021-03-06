package com.quest.etna.service;

import java.util.List;

public interface IModelService<T> {
	
	public List<T> getList(Integer object, Integer limit);
	public T getOneById(Integer id);
	public T create (T entity);
	public T update(Integer id, T entity);
	public Boolean delete(Integer id);
}
