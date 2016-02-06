package com.denis.feed.category.dao;

import com.denis.feed.category.domain.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by denis on 24/09/15
 */
@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Category getByIdCategory(Long idCategory);

    List<Category> findByIdUser(Long idUser);

    Category save(Category category);

    void delete(Category category);


}
