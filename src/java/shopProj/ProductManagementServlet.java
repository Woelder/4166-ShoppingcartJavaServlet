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
import murach.data.ProductTable;

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
        ArrayList<Product> itemListMutate = (ArrayList<Product>) session.getAttribute("items"); //gets item list getParameterValues() 
        //get the current action
        String action = request.getParameter("action");

        //determine action and set url to appropriate page
        if (action != null && session.getAttribute("auth") != null && session.getAttribute("auth").equals(true)) {

            switch (action) {
                case "displayProducts":
                    url = "/products.jsp";

                    break;
                case "addProduct":
                    url = "/product.jsp";

                    ArrayList<String> errors = new ArrayList<String>();
                    String Id = request.getParameter("Id");
                    String productCode = request.getParameter("code");
                    String itemDescription = request.getParameter("description");
                    String itemPrice = request.getParameter("price");

                    if (request.getParameter("editProduct") != null && Id != null && productCode != null && itemDescription != null && itemPrice != null) {
                        //update goes here
                        Product test1 = new Product(request.getParameter("Id"), request.getParameter("code"), ProductTable.selectProduct(request.getParameter("editProduct")).getDescription(), Double.toString(ProductTable.selectProduct(request.getParameter("editProduct")).getPrice()));
                        session.setAttribute("item", test1);

                        if (itemPrice.matches("[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?") && !(itemDescription.equals(""))) {
                            url = "/products.jsp";
                            Product toUpdate = new Product(Id, productCode, itemDescription, itemPrice);
                            ProductTable.updateProduct(toUpdate);
                        } else {
                            if(!(itemPrice.matches("[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?"))){
                                errors.add("Please enter a valid Price");
                            }

                            if (productCode != null) {

                            } else {
                                errors.add("Please enter a product code");
                            }
                            if (itemDescription != null) {

                            } else {
                                errors.add("Please enter a product description");
                            }
                            if (itemDescription != null) {

                            } else {
                                errors.add("Please enter a product description");
                            }
                            if (itemDescription.equals("")) {
                                errors.add("Please enter a product description");
                            }

                            url = "/product.jsp";
                        }
                    } else {
                        //create product object
                        boolean doub = false;
                        Product item = new Product();
                        item.setCode(productCode);
                        item.setDescription(itemDescription);
                        if (itemPrice.matches("[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?")) {
                            item.setPrice(new Double(itemPrice));
                            doub = true;
                        } else {
                            errors.add("Please only enter number or a decmial in the price");
                        }

                        if (doub) {
                            // itemList.add(item);
                            ProductTable.insertProduct(item);
                        } else {
                            if (productCode != null) {

                            } else {
                                errors.add("Please enter a product code");
                            }
                            if (itemDescription != null) {

                            } else {
                                errors.add("Please enter a product description");
                            }
                            if (itemDescription != null) {

                            } else {
                                errors.add("Please enter a product description");
                            }

                        }

                    }

                    session.setAttribute("error", errors);
                    break;
                case "editProduct":

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
        session.setAttribute("items", ProductTable.selectProducts());

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
        session.removeAttribute("item");
        if (session.getAttribute("auth") != null && session.getAttribute("auth").equals(true)) {
            switch (action) {
                case "displayProducts":
                    url = "/products.jsp";
                    break;
                case "addProduct":

                    if (request.getParameter("editProduct") != null) {

                        Product test1 = ProductTable.selectProduct(request.getParameter("editProduct"));
                        session.setAttribute("item", test1);
                    }
                    url = "/product.jsp";
                    break;
                case "displayProduct":
                    url = "/product.jsp";
                    break;
                case "deleteProduct":
                    String pCode = request.getParameter("delete");
                    if (ProductTable.exists(pCode)) {
                        Product test1 = ProductTable.selectProduct(pCode);
                        session.setAttribute("product", test1);
                        url = "/confirmDelete.jsp";
                         
                    }
                    break;
                case "confirmDelete":
                    pCode = request.getParameter("yes");
                    ProductTable.deleteProduct(pCode);
                    url = "/products.jsp";

                default:
                    break;
            }
        } else {
            url = "/login.jsp";
        }
        session.setAttribute("items", ProductTable.selectProducts());
        getServletContext().getRequestDispatcher(url).forward(request, response);

    }

}
