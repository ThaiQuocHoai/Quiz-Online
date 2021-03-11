/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.filter;

import hoaitq.user.UserDTO;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author QH
 */
public class DispatchFilter implements Filter {

    private final String INDEX = "login.jsp";
    private final String INVALID = "invalid.jsp";
    private final String ERROR = "error.jsp";
    private static final boolean debug = true;
    private final String[] LIST_ADMIN = {"admin.jsp", "add.jsp", "update.jsp", "LoginServlet", "LogoutServlet", "AdminServlet", "SearchSubServlet", "SearchStatusServlet", "SearchNameServlet", "AddNewServlet", "UpdateServlet", "SaveServlet"};
    private final String[] LIST_USER = {"home.jsp", "quiz.jsp", "history.jsp", "LoginServlet", "LogoutServlet", "HomeServlet", "QuizServlet", "TakeQuizServlet", "SubmitServlet", "SearchHistoryServlet"};
    private final String[] LIST_NONE = {"login.jsp", "signup.jsp", "LoginServlet", "LogoutServlet", "SignUpServlet"};

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public DispatchFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("DispatchFilter:DoBeforeProcessing");
        }
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("DispatchFilter:DoAfterProcessing");
        }
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        String url = INDEX;

        String uri = req.getRequestURI();
        try {
            int index = uri.lastIndexOf("/");
            String resource = uri.substring(index + 1);
            if (resource.length() > 0) {
                url = resource.substring(0, 1).toUpperCase()
                        + resource.substring(1)
                        + "Servlet";
                if (resource.lastIndexOf(".jsp") > 0) {
                    url = resource;
                }
            }
            int flag = 0;
            UserDTO dto = (UserDTO) session.getAttribute("USERINFO");
            if (dto == null) {
                for (int i = 0; i < LIST_NONE.length; i++) {
                    if (url.equals(LIST_NONE[i])) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    url = INVALID;
                }
            } else {
                int role = dto.getRole();
                if (role == 1) {
                    for (int i = 0; i < LIST_ADMIN.length; i++) {
                        if (url.equals(LIST_ADMIN[i])) {
                            flag = 1;
                        }
                    }
                    if (flag == 0) {
                        url = ERROR;
                    }
                } else {
                    for (int i = 0; i < LIST_USER.length; i++) {
                        if (url.equals(LIST_USER[i])) {
                            flag = 1;
                        }
                    }
                    if (flag == 0) {
                        url = ERROR;
                    }
                }
            }

            if (url != null) {
                RequestDispatcher rd = req.getRequestDispatcher(url);
                rd.forward(request, response);
            } else {
                chain.doFilter(request, response);
            }

        } catch (Exception e) {
            log("FilterDispatcher_Exception: " + e.getMessage());
        }

    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("DispatchFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("DispatchFilter()");
        }
        StringBuffer sb = new StringBuffer("DispatchFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
