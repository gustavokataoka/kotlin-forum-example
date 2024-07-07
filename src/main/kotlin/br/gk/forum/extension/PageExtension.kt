package br.gk.forum.extension

import br.gk.forum.dto.PageDto
import org.springframework.data.domain.Page

fun <T> Page<T>.toPageDTO(): PageDto<T> {
    return PageDto(
        content = this.content,
        totalElements = this.totalElements,
        totalPages = this.totalPages,
        size = this.size,
        number = this.number,
        numberOfElements = this.numberOfElements,
        first = this.isFirst,
        last = this.isLast,
        sort = mapOf("sorted" to this.sort.isSorted, "unsorted" to this.sort.isUnsorted)
    )
}