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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.regex.*;

/**
 *
 * @author Woe80
 */
public class ProductManagementServlet extends HttpServlet {

    ArrayList<Product> itemList = new ArrayList<>();

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
        
        String url = "index.jsp";
        HttpSession session = request.getSession();
        session.removeAttribute("item");
        ArrayList<Product> itemListMutate = (ArrayList<Product>)session.getAttribute("items"); //gets item list getParameterValues() 
        //get the current action
        String action = request.getParameter("action");

        //determine action and set url to appropriate page
        if (action != null &&session.getAttribute("auth") != null && session.getAttribute("auth").equals(true)) {

            switch (action) {
                case "displayProducts":
                    url = "/products.jsp";

                    break;
                case "addProduct":
                    url = "/product.jsp";
                    
                    ArrayList<String> errors = new ArrayList<String>();      
                    
                    String productCode = request.getParameter("code");
                    String itemDescription = request.getParameter("description");
                    String itemPrice = request.getParameter("price");
                    
                    if(productCode != null && itemDescription != null && itemPrice != null ){
                    if(request.getParameter("oldCode") != null){
                        //find reference to old code and replce with new 
                        for(int i = 0; i < itemListMutate.size(); i++){
                            if(itemListMutate.get(i).getCode().equals(request.getParameter("oldCode"))){
                                itemListMutate.get(i).setCode(request.getParameter("code"));
                                itemListMutate.get(i).setDescription(request.getParameter("description"));
                                itemListMutate.get(i).setPrice(new Double(itemPrice));                              
                            }
                            url = "/products.jsp";
                        }
                    }else if(request.getParameter("editProduct") != null){
                        Product item = new Product();
                            for(int i = 0; i < itemListMutate.size(); i++){
                            if(itemListMutate.get(i).getCode().equals(request.getParameter("editProduct"))){
                                item.setCode(itemListMutate.get(i).getCode());
                                item.setDescription(itemListMutate.get(i).getDescription());
                                item.setPrice(itemListMutate.get(i).getPrice());
                            }
                            session.setAttribute("item", item);
                            url = "/products.jsp";
                        }
                    } else {
                    //create product object
                    boolean doub = false;
                    Product item = new Product();
                    item.setCode(productCode);
                    item.setDescription(itemDescription);
                    if(itemPrice.matches("[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?")){
                                item.setPrice(new Double(itemPrice)); 
                                doub = true;
                          }else{
                                    errors.add("Please only enter number or a decmial in the price");
                          }
                    boolean notDupe = true;
                    if(itemListMutate != null){
                     for(int i = 0; i < itemListMutate.size(); i++){
                            if(itemListMutate.get(i).getCode().equals(productCode)){           
                                    errors.add("Enter a unique productCode");
                                    notDupe = false;
                            }}
                    } 
                 
                        if(notDupe && doub){
                        itemList.add(item);
                        } else{
                            if(productCode != null){
                             
                            } else {
                                errors.add("Please enter a product code");
                            }
                            if(itemDescription != null){
                            
                            }else{
                                errors.add("Please enter a product description");
                            }
                            if(itemDescription != null){
                             
                            } else {
                             errors.add("Please enter a product description");
                            }
                            

                            
                        }
                        
                    }
                    
                     
                    //add item to show the failed add product
                    
                                         
                          
                     }

                       
                     session.setAttribute("error",errors); 
                    break;
                case "displayProduct":
                    url = "/product.jsp";

                    break;
               
                default:
                    url = "/index.jsp";
                    break;
            }

        } else {
            url = "/login.jsp";
        }
        //store product list in session
        session.setAttribute("items", itemList);

        //forward request and respoinse
        getServletContext().getRequestDispatcher(url).forward(request, response);
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

        String action = request.getParameter("action");
        String url = "/index.jsp";
        HttpSession session = request.getSession();
        if ( session.getAttribute("auth") != null && session.getAttribute("auth").equals(true)) {
            switch (action) {
                case "displayProducts":
                    url = "/products.jsp";
                    break;
                case "addProduct":
                    ArrayList<Product> itemListMutate = (ArrayList<Product>)session.getAttribute("items"); //gets item list getParameterValues() 
                    if(request.getParameter("editProduct") != null){
                        Product item = new Product();
                            for(int i = 0; i < itemListMutate.size(); i++){
                            if(itemListMutate.get(i).getCode().equals(request.getParameter("editProduct"))){
                                item.setCode(itemListMutate.get(i).getCode());
                                item.setDescription(itemListMutate.get(i).getDescription());
                                item.setPrice(itemListMutate.get(i).getPrice());
                            }
                            session.setAttribute("item", item);
                        }}
                    url = "/product.jsp";
                    break;
                case "displayProduct":
                    url = "/product.jsp";
                    break;
                case "deleteProduct":
                    String pCode = request.getParameter("delete");

                    for (Product p : itemList) {
                        if (p.getCode().equals(pCode)) {
                            session.setAttribute("product", p);
                            url = "/confirmDelete.jsp";

                        }
                    }
                    break;
                case "confirmDelete":
                        pCode = request.getParameter("yes");
                        Iterator<Product> i = itemList.iterator();
                        while(i.hasNext()) {
                            Product p = i.next();
                            if (p.getCode().equals(pCode)) {
                                i.remove();
                                session.removeAttribute("items");
                                session.setAttribute("items", itemList);
                            }
                            url = "/products.jsp";
                        }
                default:
                    break;
            }
        } else {
            url = "/login.jsp";
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);

    }

}
