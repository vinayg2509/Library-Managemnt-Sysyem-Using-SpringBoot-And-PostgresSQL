package com.vinay.payload.response;

import lombok.*;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<B>
{

    private List<B> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private boolean first;
    private boolean empty;
}
