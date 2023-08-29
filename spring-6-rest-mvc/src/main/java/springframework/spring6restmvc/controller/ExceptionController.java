//package springframework.spring6restmvc.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//// global exception handler method
//// @ControllerAdvice   // by annotating this, will say this is accessible for every other controller
//public class ExceptionController {
//    @ExceptionHandler(NotFoundException.class)  // we set what exception will handle, if any method throws that exception
//    public ResponseEntity handleNotFoundException() {
//        return ResponseEntity.notFound().build();
//    }
//}
