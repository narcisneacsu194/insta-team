package com.teamtreehouse.instateam.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class GenericDaoImpl<T> implements GenericDao<T>{
    @Autowired
    protected SessionFactory sessionFactory;

    protected Class<? extends T> daoType;

    @SuppressWarnings("unchecked")
    public GenericDaoImpl(){
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        daoType = (Class) parameterizedType.getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll(){
        Session session = sessionFactory.openSession();
        List<T> objects = session.createCriteria(daoType).list();
        session.close();
        return objects;
    }

    @Override
    public T findById(Long objectId){
        Session session = sessionFactory.openSession();
        T object = session.get(daoType, objectId);
        session.close();
        return object;
    }

    @Override
    public void save(T object){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(object);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(T object){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
        session.close();
    }
}
