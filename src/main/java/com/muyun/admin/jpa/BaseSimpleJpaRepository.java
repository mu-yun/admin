package com.muyun.admin.jpa;

import com.muyun.admin.util.UserUtil;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.util.ProxyUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.Iterator;


/**
 * @author muyun
 * @date 2020/4/21
 */
public class BaseSimpleJpaRepository<T extends BaseEntity, ID extends Long> extends SimpleJpaRepository<T, ID> implements JpaRepositoryImplementation<T, ID> {

    private static final String DELETE_ALL_QUERY_STRING = "update %s x set deleted=true,updated_date=?0,updated_by=%d";

    public static final String ALIAS = "x";


    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;

    public BaseSimpleJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    public BaseSimpleJpaRepository(Class<T> domainClass, EntityManager em) {
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
    }


    /**
     * set deleted property to false when persist. because deleted is object type
     *
     * @param entity
     * @param <S>
     * @return
     */
    @Transactional
    @Override
    public <S extends T> S save(S entity) {

        if (entityInformation.isNew(entity)) {
            entity.setDeleted(Boolean.FALSE);
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }

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

    @Override
    @Deprecated
    public void deleteInBatch(Iterable<T> entities) {
        Assert.notNull(entities, "Entities must not be null!");

        if (!entities.iterator().hasNext()) {
            return;
        }

        applyAndBind(getDeleteInBatchString(entityInformation.getEntityName()), entities, em)
                .setParameter(0, new Date())
                .executeUpdate();

    }

    @Override
    public void deleteAllInBatch() {
        em.createQuery(getDeleteInBatchString(entityInformation.getEntityName()))
                .setParameter(0, new Date())
                .executeUpdate();
    }

    private static String getDeleteInBatchString(String entityName) {

        Assert.hasText(entityName, "Entity name must not be null or empty!");

        return String.format(DELETE_ALL_QUERY_STRING, entityName, UserUtil.getCurrentUserDetail().getId());
    }

    private static <T> Query applyAndBind(String queryString, Iterable<T> entities, EntityManager entityManager) {

        Assert.notNull(queryString, "Querystring must not be null!");
        Assert.notNull(entityManager, "EntityManager must not be null!");

        Iterator<T> iterator = entities.iterator();

        if (!iterator.hasNext()) {
            return entityManager.createQuery(queryString);
        }

        StringBuilder builder = new StringBuilder(queryString);
        builder.append(" where");

        int i = 0;

        while (iterator.hasNext()) {

            iterator.next();

            builder.append(String.format(" %s = ?%d", ALIAS, ++i));

            if (iterator.hasNext()) {
                builder.append(" or");
            }
        }

        Query query = entityManager.createQuery(builder.toString());

        iterator = entities.iterator();

        i = 0;

        while (iterator.hasNext()) {
            query.setParameter(++i, iterator.next());
        }

        return query;
    }


}
