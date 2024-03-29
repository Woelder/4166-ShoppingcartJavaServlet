<%-- 
    Document   : index
    Created on : Sep 19, 2019, 1:19:45 AM
    Author     : Woe80
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel ="stylesheet" type="text/css" href="CSS/confirmDelete.css.css"/>
        <link rel="stylesheet" type="text/css" href="CSS/login.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
              rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </head>
    <body>

 <nav class="navbar navbar-dark navbar-expand-md bg-dark ">
    <a href="/" class="navbar-brand mr-0">
        <i id="logo" class="material-icons" aria-hidden="true">spa</i>
    </a>
    <button class="navbar-toggler ml-1" type="button" data-toggle="collapse" data-target="#collapsingNavbar2">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="navbar-collapse collapse justify-content-between align-items-center w-100" id="collapsingNavbar2">
        <ul class="navbar-nav mx-auto text-center">
            <li class="nav-item active">
                <a class="nav-link" href="index.jsp">Home<span class="sr-only">Home</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="productManagement?action=displayProducts">View Products</a>
            </li>

        </ul>
        <ul class="nav navbar-nav flex-row justify-content-center flex-nowrap">
            
                <c:choose>
                    <c:when test="${auth == true}">
                          <li class="nav-item">
                              <a class="nav-link">${username}</a>
                               </li>
                      <li class="nav-item">
                    <a class="nav-link" href="membership?action=logout">logout</a>
                     </li>
                    </c:when>
                <c:otherwise>
                <a class="nav-link" href="login.jsp">Login</a>
                </c:otherwise>
                </c:choose>
            
        </ul>
    </div>
</nav>
        <div class="container">
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card card-signin my-5">
                        <div class="card-body">
                            <h5 class="card-title text-center">Confirm Deletion of this product:</h5>
                            <form class="form-signin" action ="productManagement" method="doPost">
                                <div class="form-label-group">

                                    <input type="text" name="code"  id="code" class="form-control" placeholder="code" required autofocus value=" <c:out value="${product.code}"/>">
                                   
                                    <label for="code">code:</label>
                                </div>

                                <div class="form-label-group">
                                    <textarea type="text" name="description" id="description" placeholder="Description:" class="form-control" rows="3"> <c:out value ="${product.description}"/>
                                    </textarea>


                                </div>

                                <div class="form-label-group">
                                    <input type="text" name="price"  id="price" class="form-control" placeholder="Price:" required autofocus value=" <c:out value="${product.price}"/>">
                                  
                                    <label for="price">Price:</label>
                                </div>

                                    <a class="btn btn-lg btn-green btn-block text-uppercase" href="productManagement?action=confirmDelete&yes=${product.id}">Yes</a>
                                    <a class="btn btn-lg btn-grey btn-block text-uppercase" href="products.jsp">No</a>

                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
