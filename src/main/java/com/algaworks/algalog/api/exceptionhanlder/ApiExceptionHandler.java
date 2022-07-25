package com.algaworks.algalog.api.exceptionhanlder;

import com.algaworks.algalog.domain.exception.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus pStatus, WebRequest request) {

        List<Campo> campos = new ArrayList<>();

        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            String nomeCampo = ((FieldError) error).getField();
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            campos.add(new Campo(nomeCampo, mensagem));
        }

        var vTitulo = "Um ou mais campos estão inválidos, faça o preenchimento e tente novamente!";
        var problemaLocal = criaAmbienteProblema(pStatus, vTitulo, campos);

        return handleExceptionInternal(ex, problemaLocal, headers, pStatus, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest webRequest) {
        var statusLocal = HttpStatus.BAD_REQUEST;
        var problemaLocal = criaAmbienteProblema(statusLocal, ex.getLocalizedMessage(),null);

        return handleExceptionInternal(ex, problemaLocal, new HttpHeaders(), statusLocal, webRequest);
    }

    private Problema criaAmbienteProblema(HttpStatus pStatus, String pTitulo, List campos) {
        var problemaRetonno = new Problema();
        problemaRetonno.setStatus(pStatus.value());
        problemaRetonno.setDataHora(OffsetDateTime.now());
        problemaRetonno.setTitulo(pTitulo);
        problemaRetonno.setCampos(campos);
        return problemaRetonno;
    }
}
