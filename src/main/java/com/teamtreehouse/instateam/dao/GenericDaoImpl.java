package com.teamtreehouse.instateam.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
// This abstract class implements the GenericDao interface.
// This greatly gets rid of dry code by not needing to create a DAO implementation class for each existing entity.
// It uses the generics feature to get the passed class type to operate on.
// It overrides all the methods from the GenericDao interface.
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

    // This method retrieves a list with all the objects from the database.
    // The class type of the objects is specified using generics.
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll(){
        Session session = sessionFactory.openSession();
        List<T> objects = session.createCriteria(daoType).list();
        session.close();
        return objects;
    }

    // This method retrieves a specific object from the database.
    // The class type of the object is specified using generics.
    @Override
    public T findById(Long objectId){
        Session session = sessionFactory.openSession();
        T object = session.get(daoType, objectId);
        session.close();
        return object;
    }

    // This method saves a new object or updates an existing an existing one in the database.
    // The class type of the object is specified using generics.
    @Override
    public void save(T object){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(object);
        session.getTransaction().commit();
        session.close();
    }

    // This method deletes an existing object from the database.
    // The class type of the object is specified using generics.
    @Override
    public void delete(T object){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(object);
        session.getTransaction().commit();
        session.close();
    }
}
