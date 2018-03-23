package PresentationLayer;

import FunctionLayer.LogicFacade;
import FunctionLayer.LegoHouseException;
import FunctionLayer.Order;
import FunctionLayer.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The purpose of Login is to...
 *
 * @author kasper
 */
public class LoginLogout extends Command {

    @Override
    String execute(HttpServletRequest request, HttpServletResponse response) throws LegoHouseException {
        if (request.getParameter("command").equals("login")) {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            User user = LogicFacade.login(email, password);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());
            if (user.getRole().equals("employee")) {
                List<Order> allOrders = LogicFacade.allOrders();
                request.setAttribute("allOrders", allOrders);
            }
            return user.getRole() + "page";
        }else{
            request.getSession().removeAttribute("user");
            return "index";
        }
    }

}