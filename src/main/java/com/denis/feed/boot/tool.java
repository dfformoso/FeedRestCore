package com.denis.feed.boot;

/**
 * Created by denis on 15/09/15

@Component
@SpringBootApplication
public class tool implements CommandLineRunner {

 */

//    @Autowired
//    FeedRepository feedRepository;
//
//    @Autowired
//    EntryRepository entryRepository;
//
//    @Autowired
//    FeedService feedService;
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    CategoryService categoryService;
//
//    @Autowired
//    FeedInstanceService feedInstanceService;
//
//    @Autowired
//    AuthTokenService authTokenService;


  /*  public static void main(String[] args) {
        System.setProperty("server.port", "8088");
        System.setProperty("spring.jpa.hibernate.naming_strategy", "org.hibernate.cfg.EJB3NamingStrategy");
        SpringApplication.run(tool.class);
    }
*/
   // public void run(String... strings) throws Exception {
        //authTokenService.create();


//        //1. CreateUser
//        User com.denis.feed.user = createUser("testDenis");
//        //2. CreateCategory
//        Category com.denis.com.denis.feed.feed.category = createCategory("categoryName", com.denis.feed.user.getIdUser());
//        //3. Search Feeds
//        List<Feed> feedList = searchFeed("http://www.eldiario.es/");
//        //3. CreateFeedInstance
//        for (Feed f : feedList) {
//            FeedInstance fi = createFeedInstance(f.getUrl(), com.denis.feed.user.getIdUser(), com.denis.com.denis.feed.feed.category.getIdCategory());
//        }

    //}

//    private List<Feed> searchFeed(String input) {
//        List<Feed> resultList = feedService.searchFeed(input);
//        return resultList;
//    }
//
//    private void getFeedUpdates() {
//        Feed com.denis.feed.feed = feedRepository.getByUrl("http://www.eldiario.es/rss/");
//        for (Entry e : feedService.getFeedUpdates(com.denis.feed.feed)) {
//            System.out.println(e.getUrl());
//        }
//    }
//
//    private User createUser(String username) throws DuplicateException {
//        User com.denis.feed.user = new User();
//        com.denis.feed.user.setUsername(username);
//        com.denis.feed.user.setPassword(username);
//        com.denis.feed.user.setEmail(username);
//        return userService.createUser(com.denis.feed.user);
//    }
//
//    private Category createCategory(String name, long idUser) {
//        Category com.denis.com.denis.feed.feed.category = new Category();
//        com.denis.com.denis.feed.feed.category.setName(name);
//        com.denis.com.denis.feed.feed.category.setIdUser(idUser);
//        return categoryService.createCategory(com.denis.com.denis.feed.feed.category);
//    }
//
//    private FeedInstance createFeedInstance(String feedURL, Long idUser, long idCategory) {
//        return feedInstanceService.createFeedInstance(feedURL, null, idUser, idCategory);
//    }

//}
