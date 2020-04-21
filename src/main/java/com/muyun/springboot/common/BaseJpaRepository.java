package com.muyun.springboot.common;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.util.ProxyUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;

/**
 * @author muyun
 * @date 2020/4/21
 */
public class BaseJpaRepository<T extends BaseEntity, ID extends Long> extends SimpleJpaRepository<T, ID> implements JpaRepositoryImplementation<T, ID> {

    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;

    public BaseJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    public BaseJpaRepository(Class<T> domainClass, EntityManager em) {
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
    }

    /**
     * Soft Delete.
     * <p><code>deleteById</code>和<code>deleteAll</code>都调用该delete方法,所以重写该方法即可</p>
     *
     * @param entity
     */
    @Override
    @Transactional
    public void delete(T entity) {
        Assert.notNull(entity, "Entity must not be null!");

        if (entityInformation.isNew(entity)) {
            return;
        }

        Class<?> type = ProxyUtils.getUserClass(entity);

        T existing = (T) em.find(type, entityInformation.getId(entity));

        // if the entity to be deleted doesn't exist, delete is a NOOP
        if (existing == null) {
            return;
        }

        entity.setDeleted(true);
        em.merge(entity);
    }

    /**
     * 调用该方法不会更新<code>updatedDate</code>和<code>updateBy</code>的值,所以暂时启用该方法
     *
     * @param entities
     */
    @Override
    @Deprecated
    public void deleteInBatch(Iterable<T> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllInBatch() {
        throw new UnsupportedOperationException();
    }
}
