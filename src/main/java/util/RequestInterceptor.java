package util;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestInterceptor implements HandlerInterceptor
{
	@Override
	public boolean preHandle(HttpServletRequest req,HttpServletResponse res,Object handler)
	{
		System.out.println("reqpre headers -> "+req.getHeader("authorization"));
		System.out.println("reqpre headers ->"+req.getContextPath());
		System.out.println("URIpre headers ->"+req.getRequestURI());
		System.out.println("respre headers ->"+res.getHeader(null));
		System.out.println("currentpre thread: "+Thread.currentThread());
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest req,HttpServletResponse res,Object handler,ModelAndView mv)
	{
		System.out.println("reqpost headers ->"+req.getHeader("authorization"));
		System.out.println("mvpost headers ->"+ mv.getViewName()+" "+mv.getModel());
		System.out.println("reqpost headers ->"+req.getContextPath());
		System.out.println("URIpost headers ->"+req.getRequestURI());
		System.out.println("respost headers ->"+res.getHeader(null));
		System.out.println("currentpost thread: "+Thread.currentThread());
		
		//return true;
	}
}
