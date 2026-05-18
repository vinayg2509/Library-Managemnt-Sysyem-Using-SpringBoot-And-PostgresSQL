package com.vinay.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,unique = true)
    @Size(max = 20,message = "ISBN can't be empty")
    private String isbn;

    @Size(max = 255,message = "Book title can't be empty")
    private String bookTitle;

    @Size(max = 255,message = "Book author name can't be empty")
    private String bookAuthor;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Genre bookGenre;

    @Size(max = 255,message = "Book publisher can't be empty")
    private String  publisher;


    private LocalDate publicationDate;

    @Size(max = 20,message = "Book language can't be empty")
    private String  language;

    private Integer noOfPages;

    @Size(max = 255,message = "Book description can't be empty")
    private String  description;

    @Min(value = 0)
    private Integer totalCopies;

    @Min(value = 0)
    @Column(nullable = false)
    private Integer availableCopies;

    private BigDecimal priceOfBook;

    @Size(max = 500)
    private String coverImageUrl;

    @Column(nullable = false)
    private Boolean isActive=true;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @AssertTrue(message = "Available copies can't exceed total copies")
    public boolean isAvailableIsValid()
    {
        if(totalCopies==null||availableCopies==null)return true;

        return availableCopies<=totalCopies;
    }

}
