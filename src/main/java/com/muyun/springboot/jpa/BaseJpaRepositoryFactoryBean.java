package com.muyun.springboot.jpa;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author muyun
 * @date 2020/5/25
 */
public class BaseJpaRepositoryFactoryBean<T extends Repository<S, ID>, S extends BaseEntity, ID> extends JpaRepositoryFactoryBean<T, S, ID> {

    public BaseJpaRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(new BaseEntityManager(entityManager));
    }

}
