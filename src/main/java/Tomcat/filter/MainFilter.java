package Tomcat.filter;

//@WebFilter("/*")
//public class MainFilter extends HttpFilter {
//    @Override
//    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
//        String url = req.getRequestURI().toString();
//        if (!url.contains("/static/") && !url.endsWith("login")) {
//            HttpSession session=req.getSession();
//            User user=(User)session.getAttribute("user");
//            if (user == null) {
//                res.sendRedirect("login");
//                return;
//            }
//        }
//        chain.doFilter(req,res);
//    }
//}
