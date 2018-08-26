package cc.databus.springboot.demo.exception;

import cc.databus.springboot.demo.pojo.JsonResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class DemoExceptionHandler {

    public static final String ERROR_VIEW = "thymeleaf/error";

    @ExceptionHandler(value = Exception.class)
    public Object errorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        System.out.println("Get exception");
        e.printStackTrace();

        if (isAjaxRequest(request)) {
            return new JsonResponse(555, "Exception got: " + e.getMessage(), null);
        }
        else {
            ModelAndView mav = new ModelAndView();
            mav.addObject("exception", e);
            mav.addObject("url", request.getRequestURL());
            mav.setViewName(ERROR_VIEW);
            return mav;
        }
    }

    private static boolean isAjaxRequest(final HttpServletRequest request) {
//        return (request.getHeader("X-Requested-With") != null
//                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
        return true;
    }
}
