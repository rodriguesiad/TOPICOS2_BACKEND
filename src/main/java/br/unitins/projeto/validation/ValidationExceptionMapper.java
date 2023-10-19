package br.unitins.projeto.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.ExceptionMapper;


@Provider
@ApplicationScoped
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception) {

        ValidationError validationError = new ValidationError("400", "Erro de Validação");
        validationError.addFieldError(exception.getFieldName(), exception.getMessage());


        return Response.status(Response.Status.BAD_REQUEST).entity(validationError).build();

    }

}