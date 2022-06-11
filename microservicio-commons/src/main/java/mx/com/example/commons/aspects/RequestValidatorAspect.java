package mx.com.example.commons.aspects;

import mx.com.example.commons.exceptions.ForbiddenException;
import mx.com.example.commons.exceptions.NotFoundException;
import mx.com.example.commons.exceptions.ParametersException;
import mx.com.example.commons.exceptions.UnauthorizedException;
import mx.com.example.commons.to.ErrorTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Configuration
public class RequestValidatorAspect {

    static final Logger LOG = LogManager.getLogger(RequestValidatorAspect.class);

    @Around(value = "execution(* mx.com.example.web.rest.*.*(..))  && args(..)")
    public ResponseEntity execute(ProceedingJoinPoint joinPoint) {

        ResponseEntity result;
        try {
            Instant start = Instant.now();
            LOG.info("**** [SE INVOCA METODO] ****");
            LOG.info("Method: {}", joinPoint.getSignature().getName());
            String parameters = Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(","));
            LOG.info("Parameters: {}", parameters);
            result = (ResponseEntity) joinPoint.proceed();
            Instant finish = Instant.now();
            long millisecondsElapsed = Duration.between(start, finish).toMillis();
            long secondsElapsed = Duration.between(start, finish).getSeconds();
            LOG.info("**** [FINALIZA METODO] - Tiempo [{} segundos o {} millisegundos] ****", secondsElapsed, millisecondsElapsed);
            return result;
        }catch (ParametersException e) {
            return printException(e, joinPoint, HttpStatus.BAD_REQUEST);
        }catch (UnauthorizedException e) {
            return printException(e, joinPoint, HttpStatus.UNAUTHORIZED);
        }catch (ForbiddenException e) {
            return printException(e, joinPoint, HttpStatus.FORBIDDEN);
        }catch (NotFoundException e) {
            return printException(e, joinPoint, HttpStatus.NOT_FOUND);
        }
        catch(Throwable e){
            return printException(e, joinPoint, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity printException(Throwable e, ProceedingJoinPoint joinPoint, HttpStatus httpStatus){
        LOG.info("**** [OCURRIO UN ERROR] ****");
        LOG.info("Execution: {}", joinPoint.getSignature());
        LOG.info("Exception: {}", e.getMessage());
        LOG.info("System Exception: {}", e.getStackTrace());
        ErrorTO error = new ErrorTO();
        error.setErrorCode("");
        String causeErrorMessage = "";
        if(e.getCause() != null){
            causeErrorMessage = e.getCause().getMessage();
        }
        error.setErrorMessage(causeErrorMessage);
        error.setUserError(e.getMessage());
        error.setInfo("");
        return ResponseEntity.status(httpStatus).body(error);
    }
}
