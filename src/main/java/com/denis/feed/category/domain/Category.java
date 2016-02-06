package com.denis.feed.category.domain;

import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;

/**
 * Created by denis on 24/09/15
 */
@Entity
@Table(name = "com/denis/feed/category")
public class Category extends ResourceSupport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idCategory")
    private Long idCategory;

    private String name;

    private Long idUser;

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

}
