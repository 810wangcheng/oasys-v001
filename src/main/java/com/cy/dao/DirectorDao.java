package com.cy.dao;

import com.cy.entity.note.Director;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Administrator
 */
public interface DirectorDao extends PagingAndSortingRepository<Director,Long> {
}
