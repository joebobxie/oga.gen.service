package com.gen.service.common.exception;

import com.gen.service.model.vo.BaseVO;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * Global Exception Handler
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Apply to all methods annotated with @RequestMapping to initialize the data binder before their execution.
     */
    @InitBinder
    public void initWebBinder(WebDataBinder binder){

    }

    /**
     * Bind the value to the Model so that the global @RequestMapping can access that value.
     */
    @ModelAttribute
    public void addAttribute(Model model) {

    }

    /**
     * Service Exception Handler
     */

    @ExceptionHandler({GlobalException.class})
    @ResponseStatus(HttpStatus.OK)
    public ApiRest<BaseVO> serviceExceptionHandler(GlobalException e) {
        return new ApiRest<BaseVO>(e);
    }

}