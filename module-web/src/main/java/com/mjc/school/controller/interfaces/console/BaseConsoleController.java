package com.mjc.school.controller.interfaces.console;

import java.util.List;

public interface BaseConsoleController<T, R, K> {

    List<R> readAll();

    R readById(K id);

    R create(T createRequest);

    R update(T updateRequest);

    boolean deleteById(K id);
}
