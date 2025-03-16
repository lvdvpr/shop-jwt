package com.apple.shop.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findPageBy(Pageable page);
    List<Item> findAllByTitleContains(String title);

    @Query(value="SELECT * FROM item WHERE(CHAR_LENGTH(?1)=1 AND title LIKE CONCAT('%', ?1, '%')) OR (CHAR_LENGTH(?1)>1 AND MATCH(title) AGAINST(?1))", nativeQuery=true)
    List<Item> fullTextSearch(String title);
}
