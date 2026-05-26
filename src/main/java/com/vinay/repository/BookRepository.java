package com.vinay.repository;

import com.vinay.model.Book;
import com.vinay.payload.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {


    Optional<Book> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);


@Query("""
    SELECT b FROM Book b WHERE
    (   :searchTerm IS NULL
        OR lower(b.bookTitle)  LIKE lower(concat('%', cast(:searchTerm as string), '%'))
        OR lower(b.bookAuthor) LIKE lower(concat('%', cast(:searchTerm as string), '%'))
        OR lower(b.isbn)       LIKE lower(concat('%', cast(:searchTerm as string), '%'))
    )
    AND (:genreId IS NULL OR b.bookGenre.id = :genreId)
    AND (:availableOnly = false OR b.availableCopies > 0)
    AND b.isActive = true
    ORDER BY b.createdAt DESC
""")
Page<Book> searchWithFilters(
        @Param("searchTerm") String searchTerm,
        @Param("genreId") Long genreId,
        @Param("availableOnly") boolean availableOnly,
        Pageable pageable
);
    long countByIsActiveTrue();

    @Query("select count(b) from Book b where b.availableCopies > 0 and b.isActive = true")
    Long availableCopies();
}