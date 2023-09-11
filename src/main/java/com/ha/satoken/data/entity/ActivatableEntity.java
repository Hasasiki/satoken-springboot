package com.ha.satoken.data.entity;


import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@NoRepositoryBean
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class ActivatableEntity<ID extends Serializable> {
    @Basic
    @Column(updatable = false)
    private boolean active = true;

    @CreatedDate
    @Column(updatable = false)
    private Long createDate;

    @CreatedBy
    @Column(updatable = false)
    private Long createBy;

    @Id
    public abstract ID getId();

    public boolean isActive() {
        return this.active;
    }

    public Long getCreateDate() {
        return this.createDate;
    }

    public Long getCreateBy() {
        return this.createBy;
    }
}
