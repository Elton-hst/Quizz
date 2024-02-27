package br.com.mjv.quizz.domain.config.pagination;

import java.util.List;
import java.util.function.Function;

public record PagedResponse<T>(
        int currentPage,
        int perPage,
        long total,
        List<T> items) {

    public <R> PagedResponse<R> map(final Function<T, R> mapper) {
        final List<R> aNewList = this.items.stream()
                .map(mapper)
                .toList();

        return new PagedResponse<>(currentPage(), perPage(), total(), aNewList);
    }

}
