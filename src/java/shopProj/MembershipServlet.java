/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shopProj;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import murach.data.UserTable;
/**
 *
 * @author Woe80
 */
public class MembershipServlet extends HttpServlet {
    
    ArrayList<User> users = new ArrayList<User>();
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String url = "index.jsp";
           session.removeAttribute("error");
        if(action.equals("login")){
            url="/login.jsp";
        } else if(action.equals("signup")){
               url="/signup.jsp";
        } else if(action.equals("logout")){
             url=logout(session);
        }
        
         getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        session.removeAttribute("username");     
        
        String url = "/index.jsp";
        
        String action = request.getParameter("action");
        
        Boolean authed;
        if (action.equals("login")){
           url = "/login.jsp";
           authed = false;
           if(request.getParameter("inputEmail") != null && request.getParameter("inputPassword") != null){
           
               User checkPass = UserTable.getUser(request.getParameter("inputEmail")); //gets the user object from the email entered
               String test1 = request.getParameter("inputPassword");
               String test2 = checkPass.getPassword();
               if(checkPass != null && request.getParameter("inputPassword").equals(checkPass.getPassword())){ //checks if email is in DB and checks the entered password agaist the one in the user object.
                   authed = true;
                   session.setAttribute("username",checkPass.getEmail());
                   url="/productManagement?action=displayProducts";
               }
           if(!authed){ //if not authed get error to display on the page
               ArrayList<String> errors = loginErrorCheck(request,users);                                                            
               session.setAttribute("error",errors);               
           }
           session.setAttribute("auth",authed);
           
        }} else if (action.equals("signup")){
            url = "/signup.jsp";
            if(request.getParameter("firstName") != null && request.getParameter("lastName") != null && request.getParameter("inputEmail") != null && request.getParameter("inputPassword") != null && request.getParameter("inputPassword").length() >= 8 && UserTable.getUser(request.getParameter("inputEmail")).getEmail() == ""){
            User signup = new User(request.getParameter("firstName"), request.getParameter("lastName"), request.getParameter("inputEmail"),request.getParameter("inputPassword"));
            users.add(signup);
            UserTable.addRecord(signup);
            url = "/login.jsp"; //pushed to login due to successful signup.
            } else { //get error list and displays to user
                 
              ArrayList<String> errors = signupErrorCheck(request);      
              
              session.setAttribute("error",errors);      
               
             }
        } else if (action.equals("logout")) { //for logout's 
            url=logout(session);
        }
       
        session.setAttribute("userlist",users);
        
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    public String logout(HttpSession session){
        String url = "/index.jsp";
        session.removeAttribute("auth");
        session.removeAttribute("username");
        return url;
    }
    // Method to get the error from the login to display on the login page. 
    public ArrayList<String> loginErrorCheck(HttpServletRequest request,ArrayList<User> users) {
          ArrayList<String> errors = new ArrayList<String>();
         
        if(!(request.getParameter("inputEmail") != null && request.getParameter("inputPassword") != null)){
            errors.add("Empty Email or Password");
        }
         //for ( int i = 0; i < users.size(); i++ ){
         //if(!(request.getParameter("inputEmail").equals(users.get(i).getEmail()) && request.getParameter("inputPassword").equals(users.get(i).getPassword()))){ 
              errors.add("Invalid Email or password");
        // }}
   
        return errors;
    }
        //method to get error form the sign up page and display them on that page. 
        public ArrayList<String> signupErrorCheck(HttpServletRequest request) {
          ArrayList<String> errors = new ArrayList<String>();
         
        if(request.getParameter("inputEmail") == null){
            errors.add("Empty Email");
        }
        if(request.getParameter("firstName") == null){
            errors.add("Empty First Name");
        }
         if(request.getParameter("lastName") == null){
            errors.add("Empty Last Name");
        }
          if(request.getParameter("inputPassword") == null){
            errors.add("Empty Password");
        }
           if(request.getParameter("inputPassword") != null && request.getParameter("inputPassword").length() < 8){ //for password length and has not null so it doesn't nul pointer exception
            errors.add("Password is to short. Must be at least 8 character long.");
        }
           try{           
          if(UserTable.getUser(request.getParameter("inputEmail")).getEmail().equals(request.getParameter("inputEmail"))){
              errors.add("Email already in use, please use a different email or recover password.");
          }
} catch(IOException e){
                    errors.add(e.getMessage() +"System Error please Contact and Admin Or retry.");
                  }
 
        return errors;
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
