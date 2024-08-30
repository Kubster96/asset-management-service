package io.kontakt.assetmanagmentservice.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceType, String id) {
        super(String.format("Resource of type [%s] and id [%s] not found", resourceType, id));
    }
}



