package com.publicis.sapient.user_search_api.service;

import com.publicis.sapient.user_search_api.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.search.engine.search.query.SearchQuery;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SearchService {

    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> searchUsers(String keyword) {
        try {
        SearchSession searchSession = Search.session(entityManager);
        SearchQuery<User> query = searchSession.search(User.class)
                .where(f -> f.bool()
                        .should(f.wildcard()
                                .fields("firstName", "lastName", "email")
                                .matching(keyword + "*") // Wildcard search
                        )
                        .should(f.match()
                                .field("ssn")
                                .matching(keyword)
                        )
                )
                .toQuery();

        List<User> results = query.fetchHits(20);
        logger.info("Search completed for keyword: {} with {} results found", keyword, results.size());
        // Execute the query and fetch the results
        return results; // Fetch up to 20 results
    }catch (HibernateException e) {
            // Log the exception and rethrow a custom exception or return an empty list
            System.err.println("Hibernate error during search: " + e.getMessage());
            // Returning an empty list here, but can be changed to a custom response if needed
            return List.of(); // Empty list returned on error
        } catch (Exception e) {
            // Catch any other exceptions and log them
            System.err.println("Error during search: " + e.getMessage());
            return List.of(); // Empty list returned on general error
        }
    }
}



