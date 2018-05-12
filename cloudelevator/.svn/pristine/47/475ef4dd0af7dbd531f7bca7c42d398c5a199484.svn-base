package com.laixusoft.cloudelevator.biz.common.security.filter;

import org.springframework.web.filter.GenericFilterBean;
import wint.core.service.bean.BeanFactoryService;
import wint.core.service.bean.spring.SpringSupportBeanFactoryService;
import wint.mvc.holder.WintContext;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by apple on 14-6-26 下午9:29.
 */
public class IFilterProxy  extends GenericFilterBean {

    private String contextAttribute;

    private String targetBeanName;

    private boolean targetFilterLifecycle = false;

    private Filter delegate;

    private final Object delegateMonitor = new Object();


    /**
     * Set the name of the ServletContext attribute which should be used to retrieve the
     * {@link org.springframework.web.context.WebApplicationContext} from which to load the delegate {@link javax.servlet.Filter} bean.
     */
    public void setContextAttribute(String contextAttribute) {
        this.contextAttribute = contextAttribute;
    }

    /**
     * Return the name of the ServletContext attribute which should be used to retrieve the
     * {@link org.springframework.web.context.WebApplicationContext} from which to load the delegate {@link javax.servlet.Filter} bean.
     */
    public String getContextAttribute() {
        return this.contextAttribute;
    }

    /**
     * Set the name of the target bean in the Spring application context.
     * The target bean must implement the standard Servlet 2.3 Filter interface.
     * <p>By default, the <code>filter-name</code> as specified for the
     * DelegatingFilterProxy in <code>web.xml</code> will be used.
     */
    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

    /**
     * Return the name of the target bean in the Spring application context.
     */
    protected String getTargetBeanName() {
        return this.targetBeanName;
    }

    /**
     * Set whether to invoke the <code>Filter.init</code> and
     * <code>Filter.destroy</code> lifecycle methods on the target bean.
     * <p>Default is "false"; target beans usually rely on the Spring application
     * context for managing their lifecycle. Setting this flag to "true" means
     * that the servlet container will control the lifecycle of the target
     * Filter, with this proxy delegating the corresponding calls.
     */
    public void setTargetFilterLifecycle(boolean targetFilterLifecycle) {
        this.targetFilterLifecycle = targetFilterLifecycle;
    }

    /**
     * Return whether to invoke the <code>Filter.init</code> and
     * <code>Filter.destroy</code> lifecycle methods on the target bean.
     */
    protected boolean isTargetFilterLifecycle() {
        return this.targetFilterLifecycle;
    }


    protected void initFilterBean() throws ServletException {
        // If no target bean name specified, use filter name.
        if (this.targetBeanName == null) {
            this.targetBeanName = getFilterName();
        }

        // Fetch Spring root application context and initialize the delegate early,
        // if possible. If the root application context will be started after this
        // filter proxy, we'll have to resort to lazy initialization.
//        synchronized (this.delegateMonitor) {
//            ApplicationContext wac = findWebApplicationContext();
//            if (wac != null) {
//                this.delegate = initDelegate(wac);
//            }
//        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Lazily initialize the delegate if necessary.
        Filter delegateToUse = null;
        synchronized (this.delegateMonitor) {
            if (this.delegate == null) {
                BeanFactoryService wac = findWebApplicationContext();
                if (wac == null) {
                    throw new IllegalStateException("No WebApplicationContext found: no ContextLoaderListener registered?");
                }
                this.delegate = initDelegate(wac);
            }
            delegateToUse = this.delegate;
        }

        // Let the delegate perform the actual doFilter operation.
        invokeDelegate(delegateToUse, request, response, filterChain);
    }

    public void destroy() {
        Filter delegateToUse = null;
        synchronized (this.delegateMonitor) {
            delegateToUse = this.delegate;
        }
        if (delegateToUse != null) {
            destroyDelegate(delegateToUse);
        }
    }


    /**
     * Retrieve a <code>WebApplicationContext</code> from the <code>ServletContext</code>
     * attribute with the {@link #setContextAttribute configured name}. The
     * <code>WebApplicationContext</code> must have already been loaded and stored in the
     * <code>ServletContext</code> before this filter gets initialized (or invoked).
     * <p>Subclasses may override this method to provide a different
     * <code>WebApplicationContext</code> retrieval strategy.
     * @return the WebApplicationContext for this proxy, or <code>null</code> if not found
     * @see #getContextAttribute()
     */
    protected BeanFactoryService findWebApplicationContext() {
        return WintContext.getServiceContext().getService(SpringSupportBeanFactoryService.class);
//        String attrName = getContextAttribute();
//        if (attrName != null) {
//            return WebApplicationContextUtils.getWebApplicationContext(getServletContext(), attrName);
//        }
//        else {
//            return WebApplicationContextUtils.getWebApplicationContext(getServletContext());
//        }
    }

    /**
     * Initialize the Filter delegate, defined as bean the given Spring
     * application context.
     * <p>Default implementation fetches the bean from the application context
     * and calls the standard <code>Filter.init</code> method on it, passing
     * in the FilterConfig of this Filter proxy.
     * @param wac the root application context
     * @return the initialized delegate Filter
     * @throws javax.servlet.ServletException if thrown by the Filter
     * @see #getTargetBeanName()
     * @see #isTargetFilterLifecycle()
     * @see #getFilterConfig()
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    protected Filter initDelegate(BeanFactoryService wac) throws ServletException {
        Filter delegate=(Filter)wac.getBeanFactory().getObject(getTargetBeanName());
//        Filter delegate = (Filter) wac.getBean(getTargetBeanName(), Filter.class);
        if (isTargetFilterLifecycle()) {
            delegate.init(getFilterConfig());
        }
        return delegate;
    }

    /**
     * Actually invoke the delegate Filter with the given request and response.
     * @param delegate the delegate Filter
     * @param request the current HTTP request
     * @param response the current HTTP response
     * @param filterChain the current FilterChain
     * @throws javax.servlet.ServletException if thrown by the Filter
     * @throws java.io.IOException if thrown by the Filter
     */
    protected void invokeDelegate(
            Filter delegate, ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        delegate.doFilter(request, response, filterChain);
    }

    /**
     * Destroy the Filter delegate.
     * Default implementation simply calls <code>Filter.destroy</code> on it.
     * @param delegate the Filter delegate (never <code>null</code>)
     * @see #isTargetFilterLifecycle()
     * @see javax.servlet.Filter#destroy()
     */
    protected void destroyDelegate(Filter delegate) {
        if (isTargetFilterLifecycle()) {
            delegate.destroy();
        }
    }

}
