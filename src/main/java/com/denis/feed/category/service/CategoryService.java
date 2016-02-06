package com.denis.feed.category.service;

import com.denis.feed.category.dao.CategoryRepository;
import com.denis.feed.category.domain.Category;
import com.denis.feed.exceptions.AccessNotAllowedException;
import com.denis.feed.feedInstance.dao.FeedInstanceRepository;
import com.denis.feed.feedInstance.domain.FeedInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by denis on 24/09/15
 */
@Service(value = "categoryService")
public class CategoryService {

    protected final Logger log = LogManager.getLogger();


    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FeedInstanceRepository feedInstanceRepository;

    @Transactional
    public Category createCategory(Category category) {
        log.info("CreateCategory [" + category.getIdCategory() + "]");
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Category category) throws AccessNotAllowedException {
        log.info("updateCategory [" + category.getIdCategory() + "]");
        final Category dbCategory = categoryRepository.getByIdCategory(category.getIdCategory());
        //TODO check with annotations
        if ((category.getIdUser() == dbCategory.getIdUser()) == false) {
            throw new AccessNotAllowedException();
        }
        dbCategory.setName(category.getName());
        categoryRepository.save(dbCategory);
        return category;
    }

    @Transactional
    public boolean deleteCategory(Long idCategory, Long idUser) throws AccessNotAllowedException {
        //TODO DO IT ASYNCHRONOUS¿?¿?¿?
        log.info("deleteCategory idCategory [" + idCategory + "] idUser[" + idUser + "]");
        Category category = categoryRepository.getByIdCategory(idCategory);
        //TODO do with annotations
        if ((category.getIdUser() == idUser) == false) {
            throw new AccessNotAllowedException();
        }
        int page = 0;
        PageRequest pageRequest = new PageRequest(page, 10);
        List<FeedInstance> feedInstanceList = feedInstanceRepository.getByIdCategory(idCategory);
        while (feedInstanceList.isEmpty() == false) {
            feedInstanceList.forEach(feedInstanceRepository::delete);
            feedInstanceList = feedInstanceRepository.getByIdCategory(idCategory);
        }
        categoryRepository.delete(category);
        return true;
    }

    public List<Category> getAllByUser(Long idUser) {
        return categoryRepository.findByIdUser(idUser);
    }

    public Category getCategory(Long idCategory) {
        return categoryRepository.getByIdCategory(idCategory);
    }
}
