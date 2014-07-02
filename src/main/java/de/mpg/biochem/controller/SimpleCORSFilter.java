package de.mpg.biochem.controller;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * This class filters each call to the web service, and sets the required headers to allow Cross-origin resource sharing (CORS)
 * 
 * @author jvillaveces
 *
 */
@Component
public class SimpleCORSFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*"); // allow all URIs to access the resource
		response.setHeader("Access-Control-Allow-Methods", "POST, GET"); // Allow all methods
		response.setHeader("Access-Control-Max-Age", "3600"); // indicates how long the results of a preflight request can be cached
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");// enables the usage of x-requested-with header when making a request.
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}

}