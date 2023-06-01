package ru.kpfu.itis.lifeTrack.controller.rest.helper;

import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface StandardRestApi <T, ID> {

    ResponseEntity<Collection<T>> list();

    ResponseEntity<T> get(ID id);

    ResponseEntity<T> create(T resource);

    ResponseEntity<T> patch(ID id, JsonPatch jsonPatch);

    ResponseEntity<T> update(ID id, T resource);

    ResponseEntity<ID> delete(ID id);

}
