package com.denis.feed.category.controller;

import com.denis.feed.Utils.Session;
import com.denis.feed.category.domain.Category;
import com.denis.feed.category.service.CategoryService;
import com.denis.feed.exceptions.AccessNotAllowedException;
import com.denis.feed.feed.domain.FeedWrapper;
import com.denis.feed.feedInstance.service.FeedInstanceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.denis.feed.validation.InjectSession;

import java.util.List;


/**
 * Created by denis on 3/10/15
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    protected final Logger log = LogManager.getLogger();


    @Autowired
    CategoryService categoryService;

    @Autowired
    FeedInstanceService feedInstanceService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    List<Category> getAll(@InjectSession Session session) {
        //TODO PROPERTIES
        List<Category> categories = categoryService.getAllByUser(session.getUser().getIdUser());
        return categories;
    }

    @RequestMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        Category category = categoryService.getCategory(id);
        //Link link = linkTo(methodOn(CategoryController.class).getFeedsByCategory(com.denis.com.denis.feed.feed.category.getIdCategory(), 0, session)).withRel("feeds");
        //com.denis.com.denis.feed.feed.category.add(link);
        return categoryService.getCategory(id);
    }

    @RequestMapping("/{idCategory}/feeds")
    public List<FeedWrapper> getFeedsByCategory(@InjectSession Session session, @PathVariable Long idCategory) {
        //TODO PROPERTIES
        List<FeedWrapper> feedList = feedInstanceService.getByIdCategory(idCategory, session.getUser().getIdUser());
        // for (FeedWrapper com.denis.feed.feed : feedList) {
        //     Link link = linkTo(methodOn(FeedController.class).getByIdFeedAndUser(com.denis.feed.feed.getIdFeed(), session)).withSelfRel();
        //     com.denis.feed.feed.add(link);
        // }
        return feedList;
    }

    @RequestMapping(method = RequestMethod.POST)
    Category create(Category category, @InjectSession Session session) {
        final Category dbCategory = new Category();
        dbCategory.setIdUser(session.getUser().getIdUser());
        dbCategory.setName(category.getName());
        return categoryService.createCategory(dbCategory);
    }

    @RequestMapping(method = RequestMethod.PUT)
    Category update(Category category, @InjectSession Session session) throws AccessNotAllowedException {
        final Category dbCategory = new Category();
        dbCategory.setIdUser(session.getUser().getIdUser());
        dbCategory.setName(category.getName());
        dbCategory.setIdCategory(category.getIdCategory());
        return categoryService.updateCategory(dbCategory);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    void delete(Category category, @InjectSession Session session) throws AccessNotAllowedException {
        categoryService.deleteCategory(category.getIdCategory(), session.getUser().getIdUser());
    }

}
